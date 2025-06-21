package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.persistence.jpa.DboFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DboFactoryTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final int DIA_MM_HG = 70;
	private static final int PULSE_PER_MINUTE = 60;
	private static final BloodPressureMeasurementStatusDbo STATUS = BloodPressureMeasurementStatusDbo.GREEN;
	private static final int SYS_MM_HG = 130;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);
	private static final UUID ID = UUID.randomUUID();

	@Mock
	private UuidFactory uuidFactory;

	@InjectMocks
	private DboFactory unitUnderTest;

	@Nested
	class create_int_int_int_BloodPresureMeasurementStatus_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(SYS_MM_HG, DIA_MM_HG, PULSE_PER_MINUTE, STATUS, null, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asDiaMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(SYS_MM_HG, 0, PULSE_PER_MINUTE, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asPulsePerMinute() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(SYS_MM_HG, DIA_MM_HG, 0, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asState() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(SYS_MM_HG, DIA_MM_HG, PULSE_PER_MINUTE, null, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asSysMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(0, DIA_MM_HG, PULSE_PER_MINUTE, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTimeOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.create(SYS_MM_HG, DIA_MM_HG, PULSE_PER_MINUTE, STATUS, DATE_OF_RECORDING, null)
			);
		}

		@Test
		void returnANewObject() {
			assertNotNull(
				unitUnderTest.create(SYS_MM_HG, PULSE_PER_MINUTE, DIA_MM_HG, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.create(SYS_MM_HG, PULSE_PER_MINUTE, DIA_MM_HG, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING),
				unitUnderTest.create(SYS_MM_HG, PULSE_PER_MINUTE, DIA_MM_HG, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			BloodPressureMeasurementDbo expected = new BloodPressureMeasurementDbo()
				.setDateOfRecording(DATE_OF_RECORDING)
				.setDiaMmHg(DIA_MM_HG)
				.setId(ID)
				.setPulsePerMinute(PULSE_PER_MINUTE)
				.setStatus(STATUS)
				.setSysMmHg(SYS_MM_HG)
				.setTimeOfRecording(TIME_OF_RECORDING);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.create(SYS_MM_HG, DIA_MM_HG, PULSE_PER_MINUTE, STATUS, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}
	}
}
