package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementCommandsTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final int DIA_MM_HG = 80;
	private static final UUID ID = UUID.randomUUID();
	private static final boolean IRREGULAR_HEARTBEAT = true;
	private static final String MESSAGE = "message";
	private static final int PULSE_PER_MINUTE = 70;
	private static final BloodPressureMeasurementStatus STATUS = BloodPressureMeasurementStatus.ORANGE;
	private static final String STRING = "string";
	private static final int SYS_MM_HG = 120;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(8, 0);
	private static final String TIME_OF_RECORDING_STR = "8:00";

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private BloodPressureMeasurement bloodPressureMeasurement0;

	@Mock
	private BloodPressureMeasurement bloodPressureMeasurement1;

	@Mock
	private BloodPressureMeasurementService bloodPressureMeasurementService;

	@Mock
	private BloodPressureMeasurementToStringMapper bloodPressureMeasurementToStringMapper;

	@InjectMocks
	private BloodPressureMeasurementCommands unitUnderTest;

	@Nested
	class add_int_int_int_String_LocalDate_LocalTime {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenThrow(exception);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				TIME_OF_RECORDING_STR,
				DATE_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				TIME_OF_RECORDING_STR,
				DATE_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenDateOfMeasurementNotSet() {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				TIME_OF_RECORDING_STR,
				null
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Today", "today", "TODAY", "TD", "Td", "td" })
		void returnsOk_whenDateOfMeasurementIsToday(String today) {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				TIME_OF_RECORDING_STR,
				today
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenTimeOfMeasurementNotSet() {
			// Prepare
			when(localTimeFactory.now()).thenReturn(TIME_OF_RECORDING);
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				null,
				DATE_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Now", "now", "NOW" })
		void returnsOk_whenTimeOfMeasurementIsNow(String now) {
			// Prepare
			when(localTimeFactory.now()).thenReturn(TIME_OF_RECORDING);
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				STATUS.name(),
				now,
				DATE_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@EnumSource(BloodPressureMeasurementStatus.class)
		void returnsOk_whenNoErrorIsDetected_StatusNamePassed(BloodPressureMeasurementStatus status) {
			// Prepare
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					status,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				status.name(),
				TIME_OF_RECORDING_STR,
				DATE_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@EnumSource(BloodPressureMeasurementStatus.class)
		void returnsOk_whenNoErrorIsDetected_StatusNumberPassed(BloodPressureMeasurementStatus status) {
			// Prepare
			when(
				bloodPressureMeasurementService.createRecording(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					IRREGULAR_HEARTBEAT,
					status,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.addBloodPressureMeasurement(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				"Y",
				"" + status.getValue(),
				TIME_OF_RECORDING_STR,
				DATE_OF_RECORDING_STR
			);
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
			doThrow(exception).when(bloodPressureMeasurementService).deleteRecording(ID);
			// Run
			String returned = unitUnderTest.removeBloodPressureMeasurement(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeBloodPressureMeasurement(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeBloodPressureMeasurement(ID.toString());
			// Check
			verify(bloodPressureMeasurementService, times(1)).deleteRecording(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(bloodPressureMeasurementService.listRecordings()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listBloodPressureMeasurements();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listBloodPressureMeasurements();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(bloodPressureMeasurementToStringMapper.map(any(BloodPressureMeasurement.class))).thenReturn(STRING);
			when(bloodPressureMeasurementService.listRecordings())
				.thenReturn(List.of(bloodPressureMeasurement0, bloodPressureMeasurement1));
			// Run
			unitUnderTest.listBloodPressureMeasurements();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(bloodPressureMeasurementToStringMapper.map(bloodPressureMeasurement0)).thenReturn(STRING);
			when(bloodPressureMeasurementToStringMapper.map(bloodPressureMeasurement1)).thenReturn(STRING);
			when(bloodPressureMeasurementService.listRecordings())
				.thenReturn(List.of(bloodPressureMeasurement0, bloodPressureMeasurement1));
			// Run
			unitUnderTest.listBloodPressureMeasurements();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
