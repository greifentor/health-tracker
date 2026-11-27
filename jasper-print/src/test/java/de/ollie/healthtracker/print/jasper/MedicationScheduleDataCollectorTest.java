package de.ollie.healthtracker.print.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.print.jasper.po.MedicationScheduleItemPO;
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
class MedicationScheduleDataCollectorTest {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final LocalDate TO = LocalDate.now();
	private static final LocalDate FROM = TO.minusDays(30);
	private static final LocalDate DAY = TO.minusDays(5);

	@Mock
	private MedicationLogService medicationLogService;

	@Mock
	private MedicationPlanService medicationPlanService;

	@InjectMocks
	private MedicationScheduleDataCollector unitUnderTest;

	@BeforeEach
	void setUp() {
		when(medicationPlanService.listMedicationPlans()).thenReturn(List.of());
		when(medicationLogService.listMedicationLogs()).thenReturn(List.of());
	}

	private Medication medication(String name, String manufacturerName) {
		Manufacturer manufacturer = manufacturerName == null ? null : new Manufacturer().setName(manufacturerName);
		return new Medication().setName(name).setManufacturer(manufacturer);
	}

	private MedicationUnit unit(String name) {
		return new MedicationUnit().setName(name);
	}

	@Test
	void listsAPlanAsADailyScheduleEntryWithManufacturer() {
		// Prepare
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(
				List.of(
					new MedicationPlan()
						.setMedication(medication("Nasenspray", "Ratiopharm"))
						.setMedicationUnit(unit("Tablette"))
						.setUnitCount(new BigDecimal("1.0"))
						.setStartDate(DAY)
						.setEndDate(DAY.plusDays(10))
						.setTimeOfIntake(LocalTime.of(8, 30))
				)
			);
		MedicationScheduleItemPO expected = new MedicationScheduleItemPO()
			.setMedication("Nasenspray")
			.setManufacturer("Ratiopharm")
			.setPeriod(DAY.format(GERMAN_DATE) + " - " + DAY.plusDays(10).format(GERMAN_DATE))
			.setFrequency("1x täglich")
			.setDosage("1 Tablette")
			.setTime("um 08:30 Uhr");
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(List.of(expected), result);
	}

	@Test
	void listsALogAsAnEinmaligScheduleEntry() {
		// Prepare
		when(medicationLogService.listMedicationLogs())
			.thenReturn(
				List.of(
					new MedicationLog()
						.setMedication(medication("Aspirin", "Bayer"))
						.setMedicationUnit(unit("Tablette"))
						.setUnitCount(new BigDecimal("2.0"))
						.setDateOfIntake(DAY)
						.setTimeOfIntake(LocalTime.of(12, 0))
				)
			);
		MedicationScheduleItemPO expected = new MedicationScheduleItemPO()
			.setMedication("Aspirin")
			.setManufacturer("Bayer")
			.setPeriod(DAY.format(GERMAN_DATE))
			.setFrequency("einmalig")
			.setDosage("2 Tablette")
			.setTime("um 12:00 Uhr");
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(List.of(expected), result);
	}

	@Test
	void showsADashAsManufacturerWhenMedicationHasNone() {
		// Prepare
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(
				List.of(
					new MedicationPlan()
						.setMedication(medication("Ibuprofen", null))
						.setMedicationUnit(unit("Tablette"))
						.setUnitCount(new BigDecimal("1.0"))
						.setStartDate(DAY)
						.setEndDate(DAY.plusDays(1))
						.setTimeOfIntake(LocalTime.of(20, 0))
				)
			);
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals("-", result.get(0).getManufacturer());
	}

	@Test
	void excludesPlansEndingBeforeTheReportInterval() {
		// Prepare
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(
				List.of(
					new MedicationPlan()
						.setMedication(medication("Nasenspray", "Ratiopharm"))
						.setMedicationUnit(unit("Tablette"))
						.setUnitCount(new BigDecimal("1.0"))
						.setStartDate(FROM.minusDays(20))
						.setEndDate(FROM.minusDays(10))
						.setTimeOfIntake(LocalTime.of(8, 30))
				)
			);
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertTrue(result.isEmpty());
	}

	private MedicationPlan plan(String name, LocalDate start, LocalDate end, LocalTime time, String amount) {
		return new MedicationPlan()
			.setMedication(medication(name, "Ratiopharm"))
			.setMedicationUnit(unit("Tablette"))
			.setUnitCount(new BigDecimal(amount))
			.setStartDate(start)
			.setEndDate(end)
			.setTimeOfIntake(time);
	}

	private MedicationLog log(String name, LocalDate date, LocalTime time, String amount) {
		return new MedicationLog()
			.setMedication(medication(name, "Ratiopharm"))
			.setMedicationUnit(unit("Tablette"))
			.setUnitCount(new BigDecimal(amount))
			.setDateOfIntake(date)
			.setTimeOfIntake(time);
	}

	@Test
	void mergesSameMedicationOnConsecutiveDaysIntoOnePeriod() {
		// Prepare
		LocalTime time = LocalTime.of(8, 30);
		when(medicationLogService.listMedicationLogs())
			.thenReturn(List.of(log("Nasenspray", DAY, time, "1.0"), log("Nasenspray", DAY.plusDays(1), time, "1.0")));
		MedicationScheduleItemPO expected = new MedicationScheduleItemPO()
			.setMedication("Nasenspray")
			.setManufacturer("Ratiopharm")
			.setPeriod(DAY.format(GERMAN_DATE) + " - " + DAY.plusDays(1).format(GERMAN_DATE))
			.setFrequency("1x täglich")
			.setDosage("1 Tablette")
			.setTime("um 08:30 Uhr");
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(List.of(expected), result);
	}

	@Test
	void mergesALogFollowingAPlanIntoThePlanPeriod() {
		// Prepare
		LocalTime time = LocalTime.of(8, 30);
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(List.of(plan("Nasenspray", DAY, DAY.plusDays(2), time, "1.0")));
		when(medicationLogService.listMedicationLogs())
			.thenReturn(List.of(log("Nasenspray", DAY.plusDays(3), time, "1.0")));
		MedicationScheduleItemPO expected = new MedicationScheduleItemPO()
			.setMedication("Nasenspray")
			.setManufacturer("Ratiopharm")
			.setPeriod(DAY.format(GERMAN_DATE) + " - " + DAY.plusDays(3).format(GERMAN_DATE))
			.setFrequency("1x täglich")
			.setDosage("1 Tablette")
			.setTime("um 08:30 Uhr");
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(List.of(expected), result);
	}

	@Test
	void mergesALogPrecedingAPlanIntoThePlanPeriod() {
		// Prepare
		LocalTime time = LocalTime.of(8, 30);
		when(medicationPlanService.listMedicationPlans())
			.thenReturn(List.of(plan("Nasenspray", DAY, DAY.plusDays(2), time, "1.0")));
		when(medicationLogService.listMedicationLogs())
			.thenReturn(List.of(log("Nasenspray", DAY.minusDays(1), time, "1.0")));
		MedicationScheduleItemPO expected = new MedicationScheduleItemPO()
			.setMedication("Nasenspray")
			.setManufacturer("Ratiopharm")
			.setPeriod(DAY.minusDays(1).format(GERMAN_DATE) + " - " + DAY.plusDays(2).format(GERMAN_DATE))
			.setFrequency("1x täglich")
			.setDosage("1 Tablette")
			.setTime("um 08:30 Uhr");
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(List.of(expected), result);
	}

	@Test
	void keepsNonConsecutiveLogsAsSeparateEntries() {
		// Prepare
		LocalTime time = LocalTime.of(8, 30);
		when(medicationLogService.listMedicationLogs())
			.thenReturn(List.of(log("Nasenspray", DAY, time, "1.0"), log("Nasenspray", DAY.plusDays(2), time, "1.0")));
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(2, result.size());
	}

	@Test
	void doesNotMergeConsecutiveDaysWithDifferentDosage() {
		// Prepare
		LocalTime time = LocalTime.of(8, 30);
		when(medicationLogService.listMedicationLogs())
			.thenReturn(List.of(log("Nasenspray", DAY, time, "1.0"), log("Nasenspray", DAY.plusDays(1), time, "2.0")));
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertEquals(2, result.size());
	}

	@Test
	void excludesLogsOutsideTheReportInterval() {
		// Prepare
		when(medicationLogService.listMedicationLogs())
			.thenReturn(
				List.of(
					new MedicationLog()
						.setMedication(medication("Aspirin", "Bayer"))
						.setMedicationUnit(unit("Tablette"))
						.setUnitCount(new BigDecimal("1.0"))
						.setDateOfIntake(TO.plusDays(5))
						.setTimeOfIntake(LocalTime.of(12, 0))
				)
			);
		// Run
		List<MedicationScheduleItemPO> result = unitUnderTest.collect(FROM, TO);
		// Check
		assertTrue(result.isEmpty());
	}
}
