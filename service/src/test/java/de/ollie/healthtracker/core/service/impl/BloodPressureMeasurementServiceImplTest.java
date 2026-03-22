package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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
	private static final UUID ID = UUID.randomUUID();
	private static final boolean IRREGULAR_HEARTBEAT = true;
	private static final int PULSE_PER_MINUTE = 60;
	private static final BloodPressureMeasurementStatus STATE = BloodPressureMeasurementStatus.GREEN;
	private static final int SYS_MM_HG = 130;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);

	@Mock
	private BloodPressureMeasurement bloodPressureMeasurement;

	@Mock
	private BloodPressureMeasurementPersistencePort persistencePort;

	@Mock
	private BloodPressureMeasurementPrettier bloodPressureMeasurementPrettier;

	@InjectMocks
	private BloodPressureMeasurementServiceImpl unitUnderTest;

	@Nested
	class createRecording_int_int_int_RecordingState_LocalDate_LocalTime_String_String {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			when(
				persistencePort.create(
					DATE_OF_RECORDING,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					SYS_MM_HG,
					TIME_OF_RECORDING,
					STATE,
					IRREGULAR_HEARTBEAT
				)
			)
				.thenReturn(bloodPressureMeasurement);
			// Run
			BloodPressureMeasurement returned = unitUnderTest.createBloodPressureMeasurement(
				DATE_OF_RECORDING,
				DIA_MM_HG,
				PULSE_PER_MINUTE,
				SYS_MM_HG,
				TIME_OF_RECORDING,
				STATE,
				IRREGULAR_HEARTBEAT
			);
			// Check
			assertSame(bloodPressureMeasurement, returned);
		}
	}

	@Nested
	class deleteRecording_UUID {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Run
			unitUnderTest.deleteBloodPressureMeasurement(ID);
			// Check
			verify(persistencePort, times(1)).deleteById(ID);
		}
	}

	@Nested
	class findAllBloodPressureMeasurementsPrettifiedByTimeInterval_LocalDateTime_LocalDateTime {

		private static final LocalDate FROM = LocalDate.of(2026, 1, 1);
		private static final LocalDate TO = LocalDate.of(2026, 12, 31);

		private BloodPressureMeasurement bpm0;
		private BloodPressureMeasurement bpm1;
		private BloodPressureMeasurement bpm2;
		private BloodPressureMeasurement bpm3;

		@BeforeEach
		void beforeEach() {
			bpm0 = new BloodPressureMeasurement();
			bpm1 = new BloodPressureMeasurement();
			bpm2 = new BloodPressureMeasurement();
			bpm3 = new BloodPressureMeasurement();
		}

		@Test
		void returnsAListWithPrettifiedBloodPressureMeasurementsInTimeIntervalOrderedByLocalDateTime() {
			// Prepare
			when(persistencePort.findAllByTimeInterval(FROM, TO)).thenReturn(List.of(bpm0, bpm2, bpm1));
			when(bloodPressureMeasurementPrettier.prettify(List.of(bpm0, bpm2, bpm1))).thenReturn(List.of(bpm3, bpm2));
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
				FROM,
				TO
			);
			// Check
			assertEquals(List.of(bpm3, bpm2), returned);
		}

		@Test
		void returnsAListWithPrettifiedBloodPressureMeasurementsInTimeIntervalOrderedByLocalDateTime_acceptsNullValuesAsParameters() {
			// Prepare
			when(persistencePort.findAllByTimeInterval(null, null)).thenReturn(List.of(bpm0, bpm2, bpm1));
			when(bloodPressureMeasurementPrettier.prettify(List.of(bpm0, bpm2, bpm1))).thenReturn(List.of(bpm3, bpm2));
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
				null,
				null
			);
			// Check
			assertEquals(List.of(bpm3, bpm2), returned);
		}
	}

	@Nested
	class list {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			List<BloodPressureMeasurement> list = List.of(bloodPressureMeasurement);
			when(persistencePort.list()).thenReturn(list);
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.listBloodPressureMeasurements();
			// Check
			assertSame(list, returned);
		}
	}
}
