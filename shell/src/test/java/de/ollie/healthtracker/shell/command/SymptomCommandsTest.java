package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.SymptomToStringMapper;
import java.time.LocalDate;
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
class SymptomCommandsTest {

	private static final UUID ANOTHER_ID = UUID.randomUUID();
	private static final String DESCRIPTION = "description";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String MESSAGE = "message";
	private static final String STRING = "string";

	@Mock
	private BodyPart bodyPart;

	@Mock
	private BodyPartService bodyPartService;

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Symptom symptom0;

	@Mock
	private Symptom symptom1;

	@Mock
	private SymptomService service;

	@Mock
	private SymptomToStringMapper toStringMapper;

	@InjectMocks
	private SymptomCommands unitUnderTest;

	@Nested
	class add_int_int_int_String_LocalDate_LocalTime {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(bodyPartService.findByIdOrNameParticle(ANOTHER_ID.toString())).thenReturn(Optional.of(bodyPart));
			when(service.createSymptom(DESCRIPTION, DATE_OF_RECORDING, bodyPart)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addSymptom(DESCRIPTION, DATE_OF_RECORDING_STR, ANOTHER_ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(bodyPartService.findByIdOrNameParticle(ANOTHER_ID.toString())).thenReturn(Optional.of(bodyPart));
			when(service.createSymptom(DESCRIPTION, DATE_OF_RECORDING, bodyPart)).thenReturn(symptom0);
			// Run
			String returned = unitUnderTest.addSymptom(DESCRIPTION, DATE_OF_RECORDING_STR, ANOTHER_ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenDateOfMeasurementNotSet() {
			// Prepare
			when(bodyPartService.findByIdOrNameParticle(ANOTHER_ID.toString())).thenReturn(Optional.of(bodyPart));
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(service.createSymptom(DESCRIPTION, DATE_OF_RECORDING, bodyPart)).thenReturn(symptom0);
			// Run
			String returned = unitUnderTest.addSymptom(DESCRIPTION, null, ANOTHER_ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Today", "today", "TODAY", "TD", "Td", "td" })
		void returnsOk_whenDateOfMeasurementIsToday(String today) {
			// Prepare
			when(bodyPartService.findByIdOrNameParticle(ANOTHER_ID.toString())).thenReturn(Optional.of(bodyPart));
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(service.createSymptom(DESCRIPTION, DATE_OF_RECORDING, bodyPart)).thenReturn(symptom0);
			// Run
			String returned = unitUnderTest.addSymptom(DESCRIPTION, today, ANOTHER_ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}
	}

	@Nested
	class deleteById_UUID {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			doThrow(exception).when(service).deleteSymptom(ID);
			// Run
			String returned = unitUnderTest.removeSymptom(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeSymptom(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeSymptom(ID.toString());
			// Check
			verify(service, times(1)).deleteSymptom(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(service.listSymptoms()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listSymptoms();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listSymptoms();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(Symptom.class))).thenReturn(STRING);
			when(service.listSymptoms()).thenReturn(List.of(symptom0, symptom1));
			// Run
			unitUnderTest.listSymptoms();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(symptom0)).thenReturn(STRING);
			when(toStringMapper.map(symptom1)).thenReturn(STRING);
			when(service.listSymptoms()).thenReturn(List.of(symptom0, symptom1));
			// Run
			unitUnderTest.listSymptoms();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
