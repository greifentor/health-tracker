package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.NutritionClassCalculationService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.core.service.model.NutritionClass;
import de.ollie.healthtracker.print.jasper.po.DailyHealthReportPO;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * Collects, per day of a given interval, all health data (weight, body temperature, blood pressure measurements,
 * medications, symptoms, nutrition category and an alcohol-consumption flag) and maps it to {@link DailyHealthReportPO}s
 * ordered from the most recent day to the oldest. Only days that have at least one data point are included.
 */
@Named
@RequiredArgsConstructor
class DailyHealthReportDataCollector {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter
		.ofPattern("dd.MM.yyyy")
		.withLocale(Locale.GERMANY);
	private static final DateTimeFormatter GERMAN_TIME = DateTimeFormatter.ofPattern("HH:mm");

	private final AlcoholConsumptionService alcoholConsumptionService;
	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BodyTemperatureMeasurementHistoryService bodyTemperatureMeasurementHistoryService;
	private final MeatConsumptionHistoryService meatConsumptionHistoryService;
	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;
	private final NutritionClassCalculationService nutritionClassCalculationService;
	private final SymptomService symptomService;
	private final WeightMeasurementHistoryService weightMeasurementHistoryService;

	List<DailyHealthReportPO> collect(LocalDate from, LocalDate to) {
		int days = (int) ChronoUnit.DAYS.between(from, to);
		Map<LocalDate, DayData> dataPerDay = new TreeMap<>(Comparator.reverseOrder());

		weightMeasurementHistoryService
			.findAllOfLastDays(days)
			.forEach(wm -> {
				DayData day = dayFor(dataPerDay, wm.getDateOfRecording(), from, to);
				if (day != null && wm.getKg() != null) {
					day.weightSum += wm.getKg().doubleValue();
					day.weightCount++;
				}
			});
		bodyTemperatureMeasurementHistoryService
			.findAllOfLastDays(days)
			.forEach(btm -> {
				DayData day = dayFor(dataPerDay, btm.getDateOfRecording(), from, to);
				if (day != null && btm.getCelsius() != null) {
					day.temperatureSum += btm.getCelsius().doubleValue();
					day.temperatureCount++;
				}
			});
		bloodPressureMeasurementService
			.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(from, to)
			.forEach(bpm -> {
				DayData day = dayFor(dataPerDay, bpm.getDateOfRecording(), from, to);
				if (day != null) {
					day.bloodPressure.add(formatBloodPressure(bpm));
				}
			});
		medicationLogService
			.listMedicationLogs()
			.forEach(ml -> {
				DayData day = dayFor(dataPerDay, ml.getDateOfIntake(), from, to);
				if (day != null) {
					addMedication(day, ml.getMedication(), ml.getMedicationUnit(), ml.getUnitCount());
				}
			});
		medicationPlanService
			.listMedicationPlans()
			.forEach(plan -> {
				LocalDate planStart = plan.getStartDate() == null ? from : plan.getStartDate();
				LocalDate planEnd = plan.getEndDate() == null ? to : plan.getEndDate();
				LocalDate rangeStart = planStart.isBefore(from) ? from : planStart;
				LocalDate rangeEnd = planEnd.isAfter(to) ? to : planEnd;
				for (LocalDate date = rangeStart; !date.isAfter(rangeEnd); date = date.plusDays(1)) {
					DayData day = dayFor(dataPerDay, date, from, to);
					if (day != null) {
						addMedication(day, plan.getMedication(), plan.getMedicationUnit(), plan.getUnitCount());
					}
				}
			});
		symptomService
			.listSymptoms()
			.forEach(symptom -> {
				DayData day = dayFor(dataPerDay, symptom.getDateOfRecording(), from, to);
				if (day != null) {
					day.symptoms.add(symptom.getDescription());
				}
			});
		meatConsumptionHistoryService
			.findAllOfLastDays(days)
			.forEach(mc -> {
				DayData day = dayFor(dataPerDay, mc.getDateOfRecording(), from, to);
				if (day != null) {
					MeatCategory category = mc.getMeatProduct().getMeatType().getCategory();
					if (category == MeatCategory.FISH) {
						day.fish++;
					} else if (category == MeatCategory.MEAT) {
						day.meat++;
					}
				}
			});
		alcoholConsumptionService
			.listAlcoholConsumptions()
			.forEach(ac -> {
				DayData day = dayFor(dataPerDay, ac.getDate(), from, to);
				if (day != null) {
					day.alcohol = true;
				}
			});

		List<DailyHealthReportPO> result = new ArrayList<>();
		dataPerDay.forEach((date, day) -> result.add(toPO(date, day)));
		return result;
	}

	private DayData dayFor(Map<LocalDate, DayData> dataPerDay, LocalDate date, LocalDate from, LocalDate to) {
		if (date == null || date.isBefore(from) || date.isAfter(to)) {
			return null;
		}
		return dataPerDay.computeIfAbsent(date, d -> new DayData());
	}

	private DailyHealthReportPO toPO(LocalDate date, DayData day) {
		return new DailyHealthReportPO()
			.setDate(date.format(GERMAN_DATE))
			.setWeight(day.weightCount > 0 ? format(day.weightSum / day.weightCount) + " kg" : "-")
			.setBodyTemperature(day.temperatureCount > 0 ? format(day.temperatureSum / day.temperatureCount) + " °C" : "-")
			.setBloodPressure(day.bloodPressure.isEmpty() ? "-" : String.join(" - ", day.bloodPressure))
			.setMedications(
				day.medications.isEmpty()
					? "-"
					: day.medications.values().stream().map(MedicationAmount::format).collect(Collectors.joining("\n"))
			)
			.setSymptoms(day.symptoms.isEmpty() ? "-" : String.join("\n", day.symptoms))
			.setNutrition(nutritionLabel(day))
			.setAlcohol(day.alcohol);
	}

	private String nutritionLabel(DayData day) {
		if (day.fish == 0 && day.meat == 0) {
			return "-";
		}
		NutritionClass nutritionClass = nutritionClassCalculationService.calculate(
			new NutritionCalculationData().setFishConsumptionDays(day.fish).setMeatConsumptionDays(day.meat)
		);
		switch (nutritionClass) {
			case VEGETARIAN:
				return "Vegetarisch";
			case PESCETARIAN:
				return "Pescetarisch";
			case OMNIVOR:
				return "Karnivor";
			default:
				return "-";
		}
	}

	private String formatBloodPressure(BloodPressureMeasurement bpm) {
		return (
			bpm.getTimeOfRecording().format(GERMAN_TIME) +
			" " +
			bpm.getSysMmHg() +
			"/" +
			bpm.getDiaMmHg() +
			" P" +
			bpm.getPulsePerMinute() +
			(bpm.isIrregularHeartbeat() ? " IHB" : "")
		);
	}

	/**
	 * Adds the medication (with its unit count) to the day, merging it with an already present entry for the same
	 * medication and unit by summing the counts - so a medication given both by plan and as an individual log is listed
	 * once with the combined amount.
	 */
	private void addMedication(DayData day, Medication medication, MedicationUnit unit, BigDecimal count) {
		if (count == null) {
			return;
		}
		String name = medication != null ? medication.getName() : "?";
		String token = unit != null ? unit.getToken() : "";
		day.medications.computeIfAbsent(name + "|" + token, key -> new MedicationAmount(name, token)).add(count);
	}

	private String format(double value) {
		return String.format(Locale.GERMANY, "%.1f", value);
	}

	private static class DayData {

		private double weightSum;
		private int weightCount;
		private double temperatureSum;
		private int temperatureCount;
		private final List<String> bloodPressure = new ArrayList<>();
		private final Map<String, MedicationAmount> medications = new LinkedHashMap<>();
		private final List<String> symptoms = new ArrayList<>();
		private int fish;
		private int meat;
		private boolean alcohol;
	}

	private static class MedicationAmount {

		private final String name;
		private final String unitToken;
		private BigDecimal count = BigDecimal.ZERO;

		private MedicationAmount(String name, String unitToken) {
			this.name = name;
			this.unitToken = unitToken;
		}

		private void add(BigDecimal toAdd) {
			count = count.add(toAdd);
		}

		private String format() {
			String unit = unitToken == null || unitToken.isBlank() ? "" : " " + unitToken;
			return name + " " + count.stripTrailingZeros().toPlainString() + unit;
		}
	}
}
