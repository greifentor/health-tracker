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
		List<Row> rows = new ArrayList<>();
		List<Interval> mergeableIntervals = new ArrayList<>();
		collectPlans(from, to, rows, mergeableIntervals);
		collectLogs(from, to, mergeableIntervals);
		rows.addAll(consolidate(mergeableIntervals));
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
			mergeKey(plan.getMedication(), plan.getUnitCount(), plan.getMedicationUnit()),
			plan.getStartDate(),
			plan.getEndDate(),
			true,
			plan.getTimeOfIntake()
		);
	}

	private Interval toInterval(MedicationLog log) {
		return new Interval(
			mergeKey(log.getMedication(), log.getUnitCount(), log.getMedicationUnit()),
			log.getDateOfIntake(),
			log.getDateOfIntake(),
			false,
			log.getTimeOfIntake()
		);
	}

	private MergeKey mergeKey(Medication medication, BigDecimal count, MedicationUnit unit) {
		return new MergeKey(medicationName(medication), manufacturerName(medication), formatDosage(count, unit));
	}

	private List<Row> consolidate(List<Interval> intervals) {
		Map<MergeKey, List<Interval>> intervalsByKey = new LinkedHashMap<>();
		intervals.forEach(interval -> intervalsByKey.computeIfAbsent(interval.key(), key -> new ArrayList<>()).add(interval)
		);
		List<Row> rows = new ArrayList<>();
		intervalsByKey.forEach((key, group) -> rows.addAll(mergeGroup(key, group)));
		return rows;
	}

	private List<Row> mergeGroup(MergeKey key, List<Interval> group) {
		group.sort(Comparator.comparing(Interval::start));
		List<Row> rows = new ArrayList<>();
		DateRange range = null;
		for (Interval interval : group) {
			if (range == null) {
				range = new DateRange(interval);
			} else if (isContiguous(range.end, interval.start())) {
				range.extend(interval);
			} else {
				rows.add(toRow(key, range));
				range = new DateRange(interval);
			}
		}
		if (range != null) {
			rows.add(toRow(key, range));
		}
		return rows;
	}

	private boolean isContiguous(LocalDate currentEnd, LocalDate nextStart) {
		return !nextStart.isAfter(currentEnd.plusDays(1));
	}

	private Row toRow(MergeKey key, DateRange range) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(key.medication())
			.setManufacturer(key.manufacturer())
			.setPeriod(formatRange(range.start, range.end))
			.setFrequency(frequency(range))
			.setDosage(key.dosage())
			.setTime(formatTimes(range.times));
		return new Row(key.medication(), range.start, earliestTime(range.times), po);
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

	private String frequency(DateRange range) {
		if (range.isSingleIntake()) {
			return "einmalig";
		}
		return dailyFrequency(countableTimes(range.times));
	}

	private String dailyFrequency(int timesPerDay) {
		return timesPerDay + "x täglich";
	}

	private int countableTimes(TreeSet<LocalTime> times) {
		return Math.max(times.size(), 1);
	}

	private LocalTime earliestTime(TreeSet<LocalTime> times) {
		return times.isEmpty() ? null : times.first();
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

	private record MergeKey(String medication, String manufacturer, String dosage) {}

	private record Interval(MergeKey key, LocalDate start, LocalDate end, boolean fromPlan, LocalTime time) {}

	private record Row(String name, LocalDate start, LocalTime time, MedicationScheduleItemPO po) {}

	private static final class DateRange {

		private LocalDate start;
		private LocalDate end;
		private boolean containsPlan;
		private final TreeSet<LocalTime> times = new TreeSet<>();

		private DateRange(Interval interval) {
			start = interval.start();
			end = interval.end();
			extendState(interval);
		}

		private void extend(Interval interval) {
			if (interval.end().isAfter(end)) {
				end = interval.end();
			}
			extendState(interval);
		}

		private void extendState(Interval interval) {
			containsPlan = containsPlan || interval.fromPlan();
			if (interval.time() != null) {
				times.add(interval.time());
			}
		}

		private boolean isSingleIntake() {
			return !containsPlan && start.equals(end) && (times.size() <= 1);
		}
	}
}
