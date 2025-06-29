package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementToStringMapperImplTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final int DIA_MM_HG = 80;
	private static final int PULSE_PER_MINUTE = 70;
	private static final BloodPressureMeasurementStatus STATUS = BloodPressureMeasurementStatus.ORANGE;
	private static final int SYS_MM_HG = 120;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(8, 0);
	private static final String TIME_OF_RECORDING_STR = "08:00";

	@Mock
	private BloodPressureMeasurement bloodPressureMeasurement;

	@InjectMocks
	private BloodPressureMeasurementToStringMapperImpl unitUnderTest;

	@Nested
	class map_BloodPressureMeasurement {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingABloodPressureMeasurementWithNoSetAttributes() {
			// Prepare
			String expected = "         -     -   0   0   0 -     ";
			// Run
			String returned = unitUnderTest.map(bloodPressureMeasurement);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingABloodPressureMeasurementWithAllSetAttributes() {
			// Prepare
			String expected =
				DATE_OF_RECORDING_STR +
				" " +
				TIME_OF_RECORDING_STR +
				" " +
				SYS_MM_HG +
				"  " +
				DIA_MM_HG +
				"  " +
				PULSE_PER_MINUTE +
				" " +
				STATUS.name();
			BloodPressureMeasurement bloodPressureMeasurement = new BloodPressureMeasurement()
				.setDateOfRecording(DATE_OF_RECORDING)
				.setDiaMmHg(DIA_MM_HG)
				.setPulsePerMinute(PULSE_PER_MINUTE)
				.setStatus(STATUS)
				.setSysMmHg(SYS_MM_HG)
				.setTimeOfRecording(TIME_OF_RECORDING);
			// Run
			String returned = unitUnderTest.map(bloodPressureMeasurement);
			// Check
			assertEquals(expected, returned);
		}
	}
}
