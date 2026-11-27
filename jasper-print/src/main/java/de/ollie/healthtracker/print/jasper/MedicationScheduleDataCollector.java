package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.print.jasper.po.MedicationScheduleItemPO;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class MedicationScheduleDataCollector {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter
		.ofPattern("dd.MM.yyyy")
		.withLocale(Locale.GERMANY);
	private static final DateTimeFormatter GERMAN_TIME = DateTimeFormatter.ofPattern("HH:mm");
	private static final Comparator<Row> ROW_ORDER = Comparator
		.comparing(Row::name, String.CASE_INSENSITIVE_ORDER)
		.thenComparing(Row::start, Comparator.nullsLast(Comparator.naturalOrder()))
		.thenComparing(Row::time, Comparator.nullsLast(Comparator.naturalOrder()));

	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;

	List<MedicationScheduleItemPO> collect(LocalDate from, LocalDate to) {
		List<Interval> mergeableIntervals = new ArrayList<>();
		List<Row> rows = new ArrayList<>();
		collectPlans(from, to, rows, mergeableIntervals);
		collectLogs(from, to, mergeableIntervals);
		rows.addAll(combineByPeriod(mergePerTime(mergeableIntervals)));
		return rows.stream().sorted(ROW_ORDER).map(Row::po).toList();
	}

	private void collectPlans(LocalDate from, LocalDate to, List<Row> rows, List<Interval> mergeableIntervals) {
		medicationPlanService
			.listMedicationPlans()
			.stream()
			.filter(plan -> planIntersectsInterval(plan, from, to))
			.forEach(plan -> {
				if (hasConcretePeriod(plan)) {
					mergeableIntervals.add(toInterval(plan));
				} else {
					rows.add(toOpenPlanRow(plan));
				}
			});
	}

	private void collectLogs(LocalDate from, LocalDate to, List<Interval> mergeableIntervals) {
		medicationLogService
			.listMedicationLogs()
			.stream()
			.filter(log -> logFallsIntoInterval(log, from, to))
			.map(this::toInterval)
			.forEach(mergeableIntervals::add);
	}

	private boolean planIntersectsInterval(MedicationPlan plan, LocalDate from, LocalDate to) {
		boolean startsBeforeIntervalEnds = (plan.getStartDate() == null) || !plan.getStartDate().isAfter(to);
		boolean endsAfterIntervalStarts = (plan.getEndDate() == null) || !plan.getEndDate().isBefore(from);
		return startsBeforeIntervalEnds && endsAfterIntervalStarts;
	}

	private boolean logFallsIntoInterval(MedicationLog log, LocalDate from, LocalDate to) {
		LocalDate date = log.getDateOfIntake();
		return (date != null) && !date.isBefore(from) && !date.isAfter(to);
	}

	private boolean hasConcretePeriod(MedicationPlan plan) {
		return (plan.getStartDate() != null) && (plan.getEndDate() != null);
	}

	private Interval toInterval(MedicationPlan plan) {
		return new Interval(
			mergeKey(plan.getMedication(), plan.getUnitCount(), plan.getMedicationUnit(), plan.getTimeOfIntake()),
			plan.getStartDate(),
			plan.getEndDate(),
			true
		);
	}

	private Interval toInterval(MedicationLog log) {
		return new Interval(
			mergeKey(log.getMedication(), log.getUnitCount(), log.getMedicationUnit(), log.getTimeOfIntake()),
			log.getDateOfIntake(),
			log.getDateOfIntake(),
			false
		);
	}

	private MergeKey mergeKey(Medication medication, BigDecimal count, MedicationUnit unit, LocalTime time) {
		return new MergeKey(medicationName(medication), manufacturerName(medication), formatDosage(count, unit), time);
	}

	private List<SubRegimen> mergePerTime(List<Interval> intervals) {
		Map<MergeKey, List<Interval>> intervalsByKey = new LinkedHashMap<>();
		intervals.forEach(interval -> intervalsByKey.computeIfAbsent(interval.key(), key -> new ArrayList<>()).add(interval)
		);
		List<SubRegimen> subRegimens = new ArrayList<>();
		intervalsByKey.forEach((key, group) -> subRegimens.addAll(mergeContiguousDays(key, group)));
		return subRegimens;
	}

	private List<SubRegimen> mergeContiguousDays(MergeKey key, List<Interval> group) {
		group.sort(Comparator.comparing(Interval::start));
		List<SubRegimen> subRegimens = new ArrayList<>();
		LocalDate start = null;
		LocalDate end = null;
		boolean containsPlan = false;
		for (Interval interval : group) {
			if (start == null) {
				start = interval.start();
				end = interval.end();
				containsPlan = interval.fromPlan();
			} else if (isContiguous(end, interval.start())) {
				end = laterOf(end, interval.end());
				containsPlan = containsPlan || interval.fromPlan();
			} else {
				subRegimens.add(toSubRegimen(key, start, end, containsPlan));
				start = interval.start();
				end = interval.end();
				containsPlan = interval.fromPlan();
			}
		}
		if (start != null) {
			subRegimens.add(toSubRegimen(key, start, end, containsPlan));
		}
		return subRegimens;
	}

	private boolean isContiguous(LocalDate currentEnd, LocalDate nextStart) {
		return !nextStart.isAfter(currentEnd.plusDays(1));
	}

	private LocalDate laterOf(LocalDate first, LocalDate second) {
		return second.isAfter(first) ? second : first;
	}

	private SubRegimen toSubRegimen(MergeKey key, LocalDate start, LocalDate end, boolean containsPlan) {
		return new SubRegimen(key.medication(), key.manufacturer(), key.dosage(), start, end, key.time(), containsPlan);
	}

	private List<Row> combineByPeriod(List<SubRegimen> subRegimens) {
		Map<RangeKey, List<SubRegimen>> subRegimensByRange = new LinkedHashMap<>();
		subRegimens.forEach(subRegimen ->
			subRegimensByRange.computeIfAbsent(rangeKey(subRegimen), key -> new ArrayList<>()).add(subRegimen)
		);
		List<Row> rows = new ArrayList<>();
		subRegimensByRange.forEach((range, group) -> rows.addAll(toRows(range, group)));
		return rows;
	}

	private RangeKey rangeKey(SubRegimen subRegimen) {
		return new RangeKey(
			subRegimen.medication(),
			subRegimen.manufacturer(),
			subRegimen.dosage(),
			subRegimen.start(),
			subRegimen.end()
		);
	}

	private List<Row> toRows(RangeKey range, List<SubRegimen> group) {
		List<Row> rows = new ArrayList<>();
		if (group.stream().anyMatch(SubRegimen::containsPlan)) {
			rows.add(toPlanRow(range, planTimes(group)));
		}
		group
			.stream()
			.filter(subRegimen -> !subRegimen.containsPlan())
			.forEach(subRegimen -> rows.add(toLogRow(range, subRegimen.time())));
		return rows;
	}

	private TreeSet<LocalTime> planTimes(List<SubRegimen> group) {
		return group
			.stream()
			.filter(SubRegimen::containsPlan)
			.map(SubRegimen::time)
			.filter(Objects::nonNull)
			.collect(Collectors.toCollection(TreeSet::new));
	}

	private Row toPlanRow(RangeKey range, TreeSet<LocalTime> times) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(range.medication())
			.setManufacturer(range.manufacturer())
			.setPeriod(formatRange(range.start(), range.end()))
			.setFrequency(dailyFrequency(Math.max(times.size(), 1)))
			.setDosage(range.dosage())
			.setTime(formatTimes(times));
		return new Row(range.medication(), range.start(), times.isEmpty() ? null : times.first(), po);
	}

	private Row toLogRow(RangeKey range, LocalTime time) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(range.medication())
			.setManufacturer(range.manufacturer())
			.setPeriod(formatRange(range.start(), range.end()))
			.setFrequency(isSingleDay(range.start(), range.end()) ? "einmalig" : "1x täglich")
			.setDosage(range.dosage())
			.setTime(formatTimes(timeSet(time)));
		return new Row(range.medication(), range.start(), time, po);
	}

	private Row toOpenPlanRow(MedicationPlan plan) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(medicationName(plan.getMedication()))
			.setManufacturer(manufacturerName(plan.getMedication()))
			.setPeriod(formatPeriod(plan.getStartDate(), plan.getEndDate()))
			.setFrequency(dailyFrequency(1))
			.setDosage(formatDosage(plan.getUnitCount(), plan.getMedicationUnit()))
			.setTime(formatTimes(timeSet(plan.getTimeOfIntake())));
		return new Row(po.getMedication(), plan.getStartDate(), plan.getTimeOfIntake(), po);
	}

	private String dailyFrequency(int timesPerDay) {
		return timesPerDay + "x täglich";
	}

	private boolean isSingleDay(LocalDate start, LocalDate end) {
		return start.equals(end);
	}

	private String medicationName(Medication medication) {
		return (medication != null) && (medication.getName() != null) ? medication.getName() : "?";
	}

	private String manufacturerName(Medication medication) {
		if (hasNoManufacturerName(medication)) {
			return "-";
		}
		return medication.getManufacturer().getName();
	}

	private boolean hasNoManufacturerName(Medication medication) {
		return (
			(medication == null) || (medication.getManufacturer() == null) || (medication.getManufacturer().getName() == null)
		);
	}

	private String formatRange(LocalDate start, LocalDate end) {
		return start.equals(end) ? formatDate(start) : formatDate(start) + " - " + formatDate(end);
	}

	private String formatPeriod(LocalDate start, LocalDate end) {
		if ((start != null) && (end != null)) {
			return formatDate(start) + " - " + formatDate(end);
		}
		if (start != null) {
			return "ab " + formatDate(start);
		}
		if (end != null) {
			return "bis " + formatDate(end);
		}
		return "-";
	}

	private String formatDate(LocalDate date) {
		return date == null ? "-" : date.format(GERMAN_DATE);
	}

	private TreeSet<LocalTime> timeSet(LocalTime time) {
		TreeSet<LocalTime> times = new TreeSet<>();
		if (time != null) {
			times.add(time);
		}
		return times;
	}

	private String formatTimes(TreeSet<LocalTime> times) {
		if (times.isEmpty()) {
			return "-";
		}
		return "um " + times.stream().map(GERMAN_TIME::format).collect(Collectors.joining(", ")) + " Uhr";
	}

	private String formatDosage(BigDecimal count, MedicationUnit unit) {
		String amount = count == null ? "?" : count.stripTrailingZeros().toPlainString();
		String unitLabel = unitLabel(unit);
		return unitLabel.isBlank() ? amount : amount + " " + unitLabel;
	}

	private String unitLabel(MedicationUnit unit) {
		if (unit == null) {
			return "";
		}
		if ((unit.getName() != null) && !unit.getName().isBlank()) {
			return unit.getName();
		}
		return unit.getToken() == null ? "" : unit.getToken();
	}

	private record MergeKey(String medication, String manufacturer, String dosage, LocalTime time) {}

	private record Interval(MergeKey key, LocalDate start, LocalDate end, boolean fromPlan) {}

	private record SubRegimen(
		String medication,
		String manufacturer,
		String dosage,
		LocalDate start,
		LocalDate end,
		LocalTime time,
		boolean containsPlan
	) {}

	private record RangeKey(String medication, String manufacturer, String dosage, LocalDate start, LocalDate end) {}

	private record Row(String name, LocalDate start, LocalTime time, MedicationScheduleItemPO po) {}
}
