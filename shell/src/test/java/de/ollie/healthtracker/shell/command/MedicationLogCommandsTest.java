package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
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
	private DateStringToLocalDateConverter dateStringToLocalDateConverter;

	@Mock
	private Medication medication;

	@Mock
	private MedicationService medicationService;

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
	}
}
