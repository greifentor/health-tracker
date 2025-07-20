package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
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

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final int DIA_MM_HG = 70;
	private static final String NAME = "name";
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
	class createBloodPressureMeasurement_int_int_int_BloodPresureMeasurementStatus_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						SYS_MM_HG,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						STATUS,
						null,
						TIME_OF_RECORDING
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asDiaMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						SYS_MM_HG,
						0,
						PULSE_PER_MINUTE,
						STATUS,
						DATE_OF_RECORDING,
						TIME_OF_RECORDING
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asPulsePerMinute() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						SYS_MM_HG,
						DIA_MM_HG,
						0,
						STATUS,
						DATE_OF_RECORDING,
						TIME_OF_RECORDING
					)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asState() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						SYS_MM_HG,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						null,
						DATE_OF_RECORDING,
						TIME_OF_RECORDING
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asSysMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						0,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						STATUS,
						DATE_OF_RECORDING,
						TIME_OF_RECORDING
					)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTimeOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						SYS_MM_HG,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						STATUS,
						DATE_OF_RECORDING,
						null
					)
			);
		}

		@Test
		void returnANewObject() {
			assertNotNull(
				unitUnderTest.createBloodPressureMeasurement(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			);
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.createBloodPressureMeasurement(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				),
				unitUnderTest.createBloodPressureMeasurement(
					SYS_MM_HG,
					PULSE_PER_MINUTE,
					DIA_MM_HG,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
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
				unitUnderTest.createBloodPressureMeasurement(
					SYS_MM_HG,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					STATUS,
					DATE_OF_RECORDING,
					TIME_OF_RECORDING
				)
			);
		}
	}

	@Nested
	class createComment_String_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingABlankString_asContent() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createComment("\n\t\r ", DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asContent() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createComment(null, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createComment(CONTENT, null, TIME_OF_RECORDING));
		}

		@Test
		void throwsAnException_passingANullValue_asTimeOfRecording() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING),
				unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			CommentDbo expected = new CommentDbo()
				.setContent(CONTENT)
				.setDateOfRecording(DATE_OF_RECORDING)
				.setId(ID)
				.setTimeOfRecording(TIME_OF_RECORDING);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING));
		}
	}

	@Nested
	class createDoctorType_String {

		@Test
		void throwsAnException_passingABlankString_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctorType("\n\t\r "));
		}

		@Test
		void throwsAnException_passingANullValue_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctorType(null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createDoctorType(CONTENT));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(unitUnderTest.createDoctorType(CONTENT), unitUnderTest.createDoctorType(CONTENT));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			DoctorTypeDbo expected = new DoctorTypeDbo().setId(ID).setName(NAME);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createDoctorType(NAME));
		}
	}
}
