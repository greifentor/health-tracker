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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class MedicationScheduleDataCollector {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter
		.ofPattern("dd.MM.yyyy")
		.withLocale(Locale.GERMANY);
	private static final DateTimeFormatter GERMAN_TIME = DateTimeFormatter.ofPattern("HH:mm");
	private static final Comparator<ScheduleEntry> ORDER = Comparator
		.comparing(ScheduleEntry::name, String.CASE_INSENSITIVE_ORDER)
		.thenComparing(ScheduleEntry::date, Comparator.nullsLast(Comparator.naturalOrder()))
		.thenComparing(ScheduleEntry::time, Comparator.nullsLast(Comparator.naturalOrder()));

	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;

	List<MedicationScheduleItemPO> collect(LocalDate from, LocalDate to) {
		return Stream
			.concat(
				medicationPlanService
					.listMedicationPlans()
					.stream()
					.filter(plan -> planIntersectsInterval(plan, from, to))
					.map(this::toEntry),
				medicationLogService
					.listMedicationLogs()
					.stream()
					.filter(log -> logFallsIntoInterval(log, from, to))
					.map(this::toEntry)
			)
			.sorted(ORDER)
			.map(ScheduleEntry::po)
			.toList();
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

	private ScheduleEntry toEntry(MedicationPlan plan) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(medicationName(plan.getMedication()))
			.setManufacturer(manufacturerName(plan.getMedication()))
			.setPeriod(formatPeriod(plan.getStartDate(), plan.getEndDate()))
			.setFrequency("1x täglich")
			.setDosage(formatDosage(plan.getUnitCount(), plan.getMedicationUnit()))
			.setTime(formatTime(plan.getTimeOfIntake()));
		return new ScheduleEntry(po.getMedication(), plan.getStartDate(), plan.getTimeOfIntake(), po);
	}

	private ScheduleEntry toEntry(MedicationLog log) {
		MedicationScheduleItemPO po = new MedicationScheduleItemPO()
			.setMedication(medicationName(log.getMedication()))
			.setManufacturer(manufacturerName(log.getMedication()))
			.setPeriod(formatDate(log.getDateOfIntake()))
			.setFrequency("einmalig")
			.setDosage(formatDosage(log.getUnitCount(), log.getMedicationUnit()))
			.setTime(formatTime(log.getTimeOfIntake()));
		return new ScheduleEntry(po.getMedication(), log.getDateOfIntake(), log.getTimeOfIntake(), po);
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

	private String formatTime(LocalTime time) {
		return time == null ? "-" : "um " + time.format(GERMAN_TIME) + " Uhr";
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

	private record ScheduleEntry(String name, LocalDate date, LocalTime time, MedicationScheduleItemPO po) {}
}
