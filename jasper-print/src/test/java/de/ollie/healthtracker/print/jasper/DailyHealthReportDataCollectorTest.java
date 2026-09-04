package de.ollie.healthtracker.print.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.NutritionClassCalculationService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.model.NutritionClass;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.print.jasper.po.DailyHealthReportPO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DailyHealthReportDataCollectorTest {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final LocalDate TO = LocalDate.now();
	private static final LocalDate FROM = TO.minusDays(30);
	private static final LocalDate DAY = TO;

	@Mock
	private AlcoholConsumptionService alcoholConsumptionService;

	@Mock
	private BloodPressureMeasurementService bloodPressureMeasurementService;

	@Mock
	private BodyTemperatureMeasurementHistoryService bodyTemperatureMeasurementHistoryService;

	@Mock
	private MeatConsumptionHistoryService meatConsumptionHistoryService;

	@Mock
	private MedicationLogService medicationLogService;

	@Mock
	private MedicationPlanService medicationPlanService;

	@Mock
	private NutritionClassCalculationService nutritionClassCalculationService;

	@Mock
	private SymptomService symptomService;

	@Mock
	private WeightMeasurementHistoryService weightMeasurementHistoryService;

	@InjectMocks
	private DailyHealthReportDataCollector unitUnderTest;

	@BeforeEach
	void setUp() {
		// All sources are queried on every run; default them to empty so tests only add the data they care about.
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		when(bloodPressureMeasurementService.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(any(), any()))
			.thenReturn(List.of());
		when(medicationLogService.listMedicationLogs()).thenReturn(List.of());
		when(medicationPlanService.listMedicationPlans()).thenReturn(List.of());
		when(symptomService.listSymptoms()).thenReturn(List.of());
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt())).thenReturn(List.of());
		when(alcoholConsumptionService.listAlcoholConsumptions()).thenReturn(List.of());
	}

	private DailyHealthReportPO firstRow() {
		return unitUnderTest.collect(FROM, TO).get(0);
	}

	@Test
	void averagesTheWeightPerDay() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					new WeightMeasurement().setDateOfRecording(DAY).setKg(BigDecimal.valueOf(80.0)),
					new WeightMeasurement().setDateOfRecording(DAY).setKg(BigDecimal.valueOf(82.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("81,0 kg", result.getWeight());
	}

	@Test
	void averagesTheBodyTemperaturePerDay() {
		// Prepare
		when(bodyTemperatureMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					new BodyTemperatureMeasurement().setDateOfRecording(DAY).setCelsius(BigDecimal.valueOf(37.0)),
					new BodyTemperatureMeasurement().setDateOfRecording(DAY).setCelsius(BigDecimal.valueOf(37.2))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("37,1 °C", result.getBodyTemperature());
	}

	@Test
	void formatsTheDaysBloodPressureMeasurements() {
		// Prepare
		when(bloodPressureMeasurementService.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(any(), any()))
			.thenReturn(
				List.of(
					new BloodPressureMeasurement()
						.setDateOfRecording(DAY)
						.setTimeOfRecording(LocalTime.of(7, 30))
						.setSysMmHg(120)
						.setDiaMmHg(80)
						.setPulsePerMinute(60)
						.setIrregularHeartbeat(false)
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("07:30 120/80 P60", result.getBloodPressure());
	}

	@Test
	void joinsSeveralBloodPressureMeasurementsOfADayWithADash() {
		// Prepare
		when(bloodPressureMeasurementService.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(any(), any()))
			.thenReturn(
				List.of(
					new BloodPressureMeasurement()
						.setDateOfRecording(DAY)
						.setTimeOfRecording(LocalTime.of(7, 30))
						.setSysMmHg(120)
						.setDiaMmHg(80)
						.setPulsePerMinute(60)
						.setIrregularHeartbeat(false),
					new BloodPressureMeasurement()
						.setDateOfRecording(DAY)
						.setTimeOfRecording(LocalTime.of(19, 0))
						.setSysMmHg(130)
						.setDiaMmHg(84)
						.setPulsePerMinute(66)
						.setIrregularHeartbeat(false)
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("07:30 120/80 P60 - 19:00 130/84 P66", result.getBloodPressure());
	}

	@Test
	void listsTheDaysMedications() {
		// Prepare
		when(medicationLogService.listMedicationLogs())
			.thenReturn(
				List.of(
					new MedicationLog()
						.setDateOfIntake(DAY)
						.setMedication(new Medication().setName("Aspirin"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(1.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Aspirin 1 Tbl", result.getMedications());
	}

	@Test
	void joinsSeveralMedicationsOfADayWithLineBreaks() {
		// Prepare
		when(medicationLogService.listMedicationLogs())
			.thenReturn(
				List.of(
					new MedicationLog()
						.setDateOfIntake(DAY)
						.setMedication(new Medication().setName("Aspirin"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(1.0)),
					new MedicationLog()
						.setDateOfIntake(DAY)
						.setMedication(new Medication().setName("Ibuprofen"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(2.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Aspirin 1 Tbl\nIbuprofen 2 Tbl", result.getMedications());
	}

	@Test
	void includesMedicationsFromMedicationPlansActiveOnTheDay() {
		// Prepare
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(
				List.of(
					new MedicationPlan()
						.setStartDate(DAY)
						.setEndDate(DAY)
						.setMedication(new Medication().setName("Aspirin"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(1.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Aspirin 1 Tbl", result.getMedications());
	}

	@Test
	void mergesPlanAndLogAmountsForTheSameMedication() {
		// Prepare
		when(medicationLogService.listMedicationLogs())
			.thenReturn(
				List.of(
					new MedicationLog()
						.setDateOfIntake(DAY)
						.setMedication(new Medication().setName("Aspirin"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(1.0))
				)
			);
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(
				List.of(
					new MedicationPlan()
						.setStartDate(DAY)
						.setEndDate(DAY)
						.setMedication(new Medication().setName("Aspirin"))
						.setMedicationUnit(new MedicationUnit().setToken("Tbl"))
						.setUnitCount(BigDecimal.valueOf(1.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Aspirin 2 Tbl", result.getMedications());
	}

	@Test
	void listsTheDaysSymptoms() {
		// Prepare
		when(symptomService.listSymptoms())
			.thenReturn(List.of(new Symptom().setDateOfRecording(DAY).setDescription("Kopfschmerz")));
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Kopfschmerz", result.getSymptoms());
	}

	@Test
	void classifiesTheNutritionCategory() {
		// Prepare
		when(meatConsumptionHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					new MeatConsumption()
						.setDateOfRecording(DAY)
						.setMeatProduct(new MeatProduct().setMeatType(new MeatType().setCategory(MeatCategory.MEAT)))
				)
			);
		when(nutritionClassCalculationService.calculate(any())).thenReturn(NutritionClass.OMNIVOR);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals("Karnivor", result.getNutrition());
	}

	@Test
	void flagsAlcoholConsumption() {
		// Prepare
		when(alcoholConsumptionService.listAlcoholConsumptions())
			.thenReturn(List.of(new AlcoholConsumption().setDate(DAY)));
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertTrue(result.isAlcohol());
	}

	@Test
	void ordersTheDaysFromNewestToOldest() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(
					new WeightMeasurement().setDateOfRecording(TO.minusDays(2)).setKg(BigDecimal.valueOf(80.0)),
					new WeightMeasurement().setDateOfRecording(TO).setKg(BigDecimal.valueOf(81.0))
				)
			);
		// Run
		DailyHealthReportPO result = firstRow();
		// Check
		assertEquals(TO.format(GERMAN_DATE), result.getDate());
	}

	@Test
	void ignoresDataOutsideTheInterval() {
		// Prepare
		when(weightMeasurementHistoryService.findAllOfLastDays(anyInt()))
			.thenReturn(
				List.of(new WeightMeasurement().setDateOfRecording(FROM.minusDays(1)).setKg(BigDecimal.valueOf(80.0)))
			);
		// Run
		List<DailyHealthReportPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertTrue(result.isEmpty());
	}
}
