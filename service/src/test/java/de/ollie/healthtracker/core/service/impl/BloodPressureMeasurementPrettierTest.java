package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementPrettierTest {

	@InjectMocks
	private BloodPressureMeasurementPrettier unitUnderTest;

	@Nested
	class prettify_ListBloodPressureMeasurement {

		private static final int DIA_MM_HG_0 = 78;
		private static final int DIA_MM_HG_1 = 82;
		private static final int DIA_MM_HG_AVG = 80;
		private static final LocalDateTime FROM = LocalDateTime.of(2026, 1, 1, 0, 0, 0);
		private static final int PPM_0 = 58;
		private static final int PPM_1 = 62;
		private static final int PPM_AVG = 60;
		private static final int SYS_MM_HG_0 = 128;
		private static final int SYS_MM_HG_1 = 132;
		private static final int SYS_MM_HG_AVG = 130;
		private static final LocalDateTime TO = LocalDateTime.of(2026, 12, 31, 23, 59, 59);

		private BloodPressureMeasurement bpm0;
		private BloodPressureMeasurement bpm1;
		private BloodPressureMeasurement bpm2;
		private BloodPressureMeasurement bpmA;

		@BeforeEach
		void beforeEach() {
			LocalDate fromDate = FROM.toLocalDate();
			LocalTime fromTime = FROM.toLocalTime();
			LocalDate toDate = TO.toLocalDate();
			LocalTime toTime = TO.toLocalTime();
			bpm0 =
				new BloodPressureMeasurement()
					.setDateOfRecording(fromDate)
					.setTimeOfRecording(fromTime)
					.setDiaMmHg(DIA_MM_HG_0)
					.setPulsePerMinute(PPM_0)
					.setStatus(BloodPressureMeasurementStatus.ORANGE)
					.setSysMmHg(SYS_MM_HG_0);
			bpm1 =
				new BloodPressureMeasurement()
					.setDateOfRecording(fromDate)
					.setTimeOfRecording(fromTime)
					.setDiaMmHg(DIA_MM_HG_1)
					.setPulsePerMinute(PPM_1)
					.setStatus(BloodPressureMeasurementStatus.YELLOW)
					.setSysMmHg(SYS_MM_HG_1);
			bpm2 =
				new BloodPressureMeasurement()
					.setDateOfRecording(toDate.plusDays(1))
					.setTimeOfRecording(toTime)
					.setDiaMmHg(DIA_MM_HG_0 + 1)
					.setPulsePerMinute(PPM_0 + 1)
					.setStatus(BloodPressureMeasurementStatus.GREEN)
					.setSysMmHg(SYS_MM_HG_0 + 1);
			bpmA =
				new BloodPressureMeasurement()
					.setDateOfRecording(fromDate)
					.setTimeOfRecording(fromTime)
					.setDiaMmHg(DIA_MM_HG_AVG)
					.setPulsePerMinute(PPM_AVG)
					.setStatus(BloodPressureMeasurementStatus.ORANGE)
					.setSysMmHg(SYS_MM_HG_AVG);
		}

		@Test
		void throwsAnException_passingANullValue_asListOfBloodPressureMeasurements() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.prettify(null));
		}

		@Test
		void returnsThePassedList_whenNothingIsToPrettify() {
			// Prepare
			List<BloodPressureMeasurement> passed = List.of(bpm0, bpm2);
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.prettify(passed);
			// Check
			assertEquals(passed, returned);
		}

		@Test
		void returnsACorrectList_whenThereAreValuesToPrettify() {
			// Prepare
			List<BloodPressureMeasurement> passed = List.of(bpm0, bpm1, bpm2);
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.prettify(passed);
			// Check
			assertEquals(List.of(bpmA, bpm2), returned);
		}
	}
}
