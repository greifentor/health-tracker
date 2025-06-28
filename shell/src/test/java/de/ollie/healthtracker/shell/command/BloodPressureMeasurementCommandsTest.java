package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementCommandsTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final int DIA_MM_HG = 80;
	private static final String MESSAGE = "message";
	private static final int PULSE_PER_MINUTE = 70;
	private static final BloodPressureMeasurementStatus STATUS = BloodPressureMeasurementStatus.ORANGE;
	private static final String STRING = "string";
	private static final int SYS_MM_HG = 120;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(8, 0);
	private static final String TIME_OF_RECORDING_STR = "08:00";

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
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenThrow(exception);
			// Run
			String returned = unitUnderTest.add(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				STATUS.name(),
				DATE_OF_RECORDING_STR,
				TIME_OF_RECORDING_STR
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
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(bloodPressureMeasurement0);
			// Run
			String returned = unitUnderTest.add(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				STATUS.name(),
				DATE_OF_RECORDING_STR,
				TIME_OF_RECORDING_STR
			);
			// Check
			assertEquals(Constants.OK, returned);
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(bloodPressureMeasurementService.list()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.list();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.list();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(bloodPressureMeasurementToStringMapper.map(any(BloodPressureMeasurement.class))).thenReturn(STRING);
			when(bloodPressureMeasurementService.list())
				.thenReturn(List.of(bloodPressureMeasurement0, bloodPressureMeasurement1));
			// Run
			unitUnderTest.list();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(bloodPressureMeasurementToStringMapper.map(bloodPressureMeasurement0)).thenReturn(STRING);
			when(bloodPressureMeasurementToStringMapper.map(bloodPressureMeasurement1)).thenReturn(STRING);
			when(bloodPressureMeasurementService.list())
				.thenReturn(List.of(bloodPressureMeasurement0, bloodPressureMeasurement1));
			// Run
			unitUnderTest.list();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
