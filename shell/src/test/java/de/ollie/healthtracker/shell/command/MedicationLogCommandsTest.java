package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationLogCommandsTest {

	private static final String DATE_STRING = "2026-09-01";
	private static final String MEDICATION_SEARCH_STRING = "medication-search-string";
	private static final String MESSAGE = "message";
	private static final String TIME_STRING = "23:15";
	private static final String UNIT_COUNT_STRING = "-47.11";
	private static final String UNIT_STRING = "unit";

	@Mock
	private BigDecimalFactory bigDecimalFactory;

	@Mock
	private DateStringToLocalDateConverter dateStringToLocalDateConverter;

	@Mock
	private Medication medication;

	@Mock
	private MedicationUnit medicationUnit;

	@Mock
	private MedicationService medicationService;

	@Mock
	private MedicationLogService medicationLogService;

	@Mock
	private MedicationUnitService medicationUnitService;

	@Mock
	private TimeStringToLocalDateConverter timeStringToLocalDateConverter;

	@InjectMocks
	private MedicationLogCommands unitUnderTest;

	@Nested
	class addMedicationLogEntry_String_String_String {

		@Test
		void returnsCorrectErrorMessage_passingDateAsNullValue() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				null +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > Date is not set!";
			// Run
			String returned = unitUnderTest.addMedicationLogEntry(
				null,
				TIME_STRING,
				MEDICATION_SEARCH_STRING,
				UNIT_COUNT_STRING,
				UNIT_STRING
			);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsCorrectErrorMessage_passingTimeAsNullValue() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				null +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > Time is not set!";
			// Run
			String returned = unitUnderTest.addMedicationLogEntry(
				DATE_STRING,
				null,
				MEDICATION_SEARCH_STRING,
				UNIT_COUNT_STRING,
				UNIT_STRING
			);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectMessage_passingTheMedicationSearchStringAsANullValue() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				null +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > Medication search string is not set!";
			// Run
			String returned = unitUnderTest.addMedicationLogEntry(
				DATE_STRING,
				TIME_STRING,
				null,
				UNIT_COUNT_STRING,
				UNIT_STRING
			);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectMessage_passingTheUnitCountStringAsANullValue() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				null +
				" " +
				UNIT_STRING +
				" > Unit count is not set!";
			// Run
			String returned = unitUnderTest.addMedicationLogEntry(
				DATE_STRING,
				TIME_STRING,
				MEDICATION_SEARCH_STRING,
				null,
				UNIT_STRING
			);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectErrorMessage_passingAnInvalidDateString() {
			// Prepare
			String invalidDateString = ";op";
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				invalidDateString +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > Date string does not contain a valid date!";
			when(dateStringToLocalDateConverter.convert(invalidDateString))
				.thenThrow(new DateTimeParseException("a message", invalidDateString, 0));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					invalidDateString,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		void returnsACorrectErrorMessage_passingAnInvalidTimeString() {
			// Prepare
			String invalidTimeString = ";op";
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				invalidTimeString +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > Time string does not contain a valid time!";
			when(timeStringToLocalDateConverter.convert(invalidTimeString))
				.thenThrow(new DateTimeParseException("a message", invalidTimeString, 0));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					invalidTimeString,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_passingAnUnmatchingMedication() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > No medication found for: " +
				MEDICATION_SEARCH_STRING +
				"!";
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.empty());
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_passingAMedicationSearchstring_matchingManyMedications() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > " +
				MESSAGE;
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING))
				.thenThrow(new TooManyElementsException(MESSAGE));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_passingAnUnmatchingMedicationUnit() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > No medication unit found for: " +
				UNIT_STRING +
				"!";
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenReturn(Optional.empty());
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_passingAUnitString_matchingManyMedicationUnits() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > " +
				MESSAGE;
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenThrow(new TooManyElementsException(MESSAGE));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_passingAlreadyExistingData() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > " +
				"Medication log entry is already existing!";
			BigDecimal units = new BigDecimal(1701);
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			when(bigDecimalFactory.create(UNIT_COUNT_STRING)).thenReturn(units);
			when(dateStringToLocalDateConverter.convert(DATE_STRING)).thenReturn(date);
			when(timeStringToLocalDateConverter.convert(TIME_STRING)).thenReturn(time);
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationLogService.isDuplicate(medication, medicationUnit, date, time, units)).thenReturn(true);
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenReturn(Optional.of(medicationUnit));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectErrorMessage_whenSomethingWentWrongWhileSavingTheData() {
			// Prepare
			String expected =
				"ERROR in line: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING +
				" > " +
				MESSAGE;
			BigDecimal units = new BigDecimal(1701);
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			when(bigDecimalFactory.create(UNIT_COUNT_STRING)).thenReturn(units);
			when(dateStringToLocalDateConverter.convert(DATE_STRING)).thenReturn(date);
			when(timeStringToLocalDateConverter.convert(TIME_STRING)).thenReturn(time);
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationLogService.isDuplicate(medication, medicationUnit, date, time, units)).thenReturn(false);
			when(medicationLogService.createMedicationLog("", false, medication, medicationUnit, date, false, time, units))
				.thenThrow(new RuntimeException(MESSAGE));
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenReturn(Optional.of(medicationUnit));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void returnsACorrectSuccessMessage_passingValidData() {
			// Prepare
			String expected =
				"OK: MEDICATION_LOG " +
				DATE_STRING +
				" " +
				TIME_STRING +
				" " +
				MEDICATION_SEARCH_STRING +
				" " +
				UNIT_COUNT_STRING +
				" " +
				UNIT_STRING;
			BigDecimal units = new BigDecimal(1701);
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			when(bigDecimalFactory.create(UNIT_COUNT_STRING)).thenReturn(units);
			when(dateStringToLocalDateConverter.convert(DATE_STRING)).thenReturn(date);
			when(timeStringToLocalDateConverter.convert(TIME_STRING)).thenReturn(time);
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationLogService.isDuplicate(medication, medicationUnit, date, time, units)).thenReturn(false);
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenReturn(Optional.of(medicationUnit));
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.addMedicationLogEntry(
					DATE_STRING,
					TIME_STRING,
					MEDICATION_SEARCH_STRING,
					UNIT_COUNT_STRING,
					UNIT_STRING
				)
			);
		}

		@Test
		void callsTheCreateMethodOfTheMedicationLogServiceCorrectly_passingValidData() {
			// Prepare
			BigDecimal units = new BigDecimal(1701);
			LocalDate date = LocalDate.now();
			LocalTime time = LocalTime.now();
			when(bigDecimalFactory.create(UNIT_COUNT_STRING)).thenReturn(units);
			when(dateStringToLocalDateConverter.convert(DATE_STRING)).thenReturn(date);
			when(timeStringToLocalDateConverter.convert(TIME_STRING)).thenReturn(time);
			when(medicationService.findByIdOrNameParticle(MEDICATION_SEARCH_STRING)).thenReturn(Optional.of(medication));
			when(medicationLogService.isDuplicate(medication, medicationUnit, date, time, units)).thenReturn(false);
			when(medicationUnitService.findByIdOrNameParticle(UNIT_STRING)).thenReturn(Optional.of(medicationUnit));
			// Run
			unitUnderTest.addMedicationLogEntry(
				DATE_STRING,
				TIME_STRING,
				MEDICATION_SEARCH_STRING,
				UNIT_COUNT_STRING,
				UNIT_STRING
			);
			// Check
			verify(medicationLogService, times(1))
				.createMedicationLog("", false, medication, medicationUnit, date, false, time, units);
		}
	}
}
