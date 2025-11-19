package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationLogToStringMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationLogCommandsTest {

	private static final LocalDate DATE_OF_INTAKE = LocalDate.of(2025, 8, 1);
	private static final String DATE_OF_INTAKE_STR = "01.08.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String MEDICATION_ID_STR = "medication-id-str";
	private static final String MEDICATION_UNIT_ID_STR = "medication-unit-id-str";
	private static final String MESSAGE = "message";
	private static final boolean SELF_MEDICATION = true;
	private static final String STRING = "string";
	private static final LocalTime TIME_OF_INTAKE = LocalTime.of(12, 5);
	private static final String TIME_OF_INTAKE_STR = "12:05";
	private static final BigDecimal UNIT_COUNT = new BigDecimal(42);
	private static final String UNIT_COUNT_STR = "42";

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Medication medication;

	@Mock
	private MedicationLog medicationLog0;

	@Mock
	private MedicationLog medicationLog1;

	@Mock
	private MedicationLogService medicationLogService;

	@Mock
	private MedicationLogToStringMapper medicationLogToStringMapper;

	@InjectMocks
	private MedicationLogCommands unitUnderTest;

	@Mock
	private MedicationService medicationService;

	@Mock
	private MedicationUnitService medicationUnitService;

	@Mock
	private MedicationUnit medicationUnit;

	@Nested
	class add_int_int_int_String_LocalDate_LocalTime {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenThrow(exception);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnAnErrorMessage_whenNoMedicationIsFound_forThePassedMedicationId() {
			// Prepare
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR)).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(
				Constants.ERROR + MedicationLogCommands.MSG_NO_SUCH_MEDICATION_UNIT_FOUND + MEDICATION_UNIT_ID_STR,
				returned
			);
		}

		@Test
		void returnAnErrorMessage_whenNoMedicationUnitIsFound_forThePassedMedicationUnitId() {
			// Prepare
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.ERROR + MedicationLogCommands.MSG_NO_SUCH_MEDICATION_FOUND + MEDICATION_ID_STR, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenDateOfIntakeNotSet() {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_INTAKE);
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					false,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				null,
				null
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Today", "today", "TODAY", "TD", "Td", "td" })
		void returnsOk_whenDateOfMeasurementIsToday(String today) {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_INTAKE);
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					false,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				null,
				null
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenTimeOfIntakeNotSet() {
			// Prepare
			when(localTimeFactory.now()).thenReturn(TIME_OF_INTAKE);
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				null,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Now", "now", "NOW" })
		void returnsOk_whenTimeOfIntakeIsNow(String now) {
			// Prepare
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					UNIT_COUNT
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				UNIT_COUNT_STR,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenNoUnitCountPassed_usesDefaultValueOfOne() {
			// Prepare
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					new BigDecimal(1)
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				null,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "1", "42", "512" })
		void returnsOk_whenUnitCountIsPassed(int unitCount) {
			// Prepare
			when(
				medicationLogService.createMedicationLog(
					medication,
					medicationUnit,
					DATE_OF_INTAKE,
					SELF_MEDICATION,
					TIME_OF_INTAKE,
					new BigDecimal(unitCount)
				)
			)
				.thenReturn(medicationLog0);
			when(medicationService.findByIdOrNameParticle(MEDICATION_ID_STR)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(MEDICATION_UNIT_ID_STR))
				.thenReturn(Optional.of(medicationUnit));
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				"" + unitCount,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource(emptyValue = "EMPTY", value = { "EMPTY", "blubs", "512a" })
		void returnsAnErrorMessage_whenAnInvalidNumberIsPassed_forUnitCount(String unitCount) {
			// Run
			String returned = unitUnderTest.addMedicationLog(
				MEDICATION_ID_STR,
				MEDICATION_UNIT_ID_STR,
				unitCount,
				TIME_OF_INTAKE_STR,
				DATE_OF_INTAKE_STR,
				"" + SELF_MEDICATION
			);
			// Check
			assertEquals(Constants.ERROR + MedicationLogCommands.MSG_INVALID_NUMBER_VALUE + unitCount, returned);
		}
	}

	@Nested
	class deleteById_UUID {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			doThrow(exception).when(medicationLogService).deleteMedicationLog(ID);
			// Run
			String returned = unitUnderTest.removeMedicationLog(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeMedicationLog(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeMedicationLog(ID.toString());
			// Check
			verify(medicationLogService, times(1)).deleteMedicationLog(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(medicationLogService.listMedicationLogs()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listMedicationLogs();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listMedicationLogs();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(medicationLogToStringMapper.map(any(MedicationLog.class))).thenReturn(STRING);
			when(medicationLogService.listMedicationLogs()).thenReturn(List.of(medicationLog0, medicationLog1));
			// Run
			unitUnderTest.listMedicationLogs();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheMedicationLogMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(medicationLogToStringMapper.map(medicationLog0)).thenReturn(STRING);
			when(medicationLogToStringMapper.map(medicationLog1)).thenReturn(STRING);
			when(medicationLogService.listMedicationLogs()).thenReturn(List.of(medicationLog0, medicationLog1));
			// Run
			unitUnderTest.listMedicationLogs();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
