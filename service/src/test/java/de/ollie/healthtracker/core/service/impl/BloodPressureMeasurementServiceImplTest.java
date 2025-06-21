package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.impl.BloodPressureMeasurementServiceImpl;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementServiceImplTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final int DIA_MM_HG = 70;
	private static final int PULSE_PER_MINUTE = 60;
	private static final BloodPressureMeasurementStatus STATE = BloodPressureMeasurementStatus.GREEN;
	private static final int SYS_MM_HG = 130;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);

	@Mock
	private BloodPressureMeasurement recording;

	@Mock
	private BloodPressureMeasurementPersistencePort recordingPersistencePort;

	@InjectMocks
	private BloodPressureMeasurementServiceImpl unitUnderTest;

	@Nested
	class createRecording_int_int_int_RecordingState_LocalDate_LocalTime_String_String {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			when(
				recordingPersistencePort.create(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					STATE,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			)
				.thenReturn(recording);
			// Run
			BloodPressureMeasurement returned = unitUnderTest.createRecording(
				SYS_MM_HG,
				PULSE_PER_MINUTE,
				DIA_MM_HG,
				STATE,
				DATE_OF_RECORDING,
				TIME_OF_RECORDING
			);
			// Check
			assertSame(recording, returned);
		}
	}
}
