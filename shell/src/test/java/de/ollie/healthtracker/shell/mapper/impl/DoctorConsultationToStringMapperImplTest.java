package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.model.DoctorType;
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
class DoctorConsultationToStringMapperImplTest {

	private static final LocalDate DATE = LocalDate.of(2025, 06, 25);
	private static final String DATE_STR = "25.06.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String NAME = "name";
	private static final String REASON = "reason";
	private static final String RESULT = "result";
	private static final LocalTime TIME = LocalTime.of(8, 0);
	private static final String TIME_STR = "08:00";
	private static final String TYPE_NAME = "type-name";

	@Mock
	private Doctor doctor;

	@Mock
	private DoctorConsultation doctorConsultation;

	@Mock
	private DoctorType doctorType;

	@InjectMocks
	private DoctorConsultationToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals(
				"(ID)                                   Date     Time  Doctor                         Doctor Type\n" +
				"Reason\n" +
				"Result",
				unitUnderTest.getHeadLine()
			);
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"--------------------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_DoctorConsultation {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingDoctorConsultationWithNoSetAttributes() {
			// Prepare
			String expected =
				"(                                   -) -        -     -                              -\n" + "null\n" + "null";
			// Run
			String returned = unitUnderTest.map(doctorConsultation);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingADoctorConsultationWithAllSetAttributes() {
			// Prepare
			String expected =
				"(" +
				ID +
				") " +
				DATE_STR +
				" " +
				TIME_STR +
				" " +
				NAME +
				"                           " +
				TYPE_NAME +
				"\n" +
				REASON +
				"\n" +
				RESULT;
			doctorType = new DoctorType().setName(TYPE_NAME);
			doctor = new Doctor().setId(ID).setName(NAME).setDoctorType(doctorType);
			doctorConsultation =
				new DoctorConsultation()
					.setDate(DATE)
					.setDoctor(doctor)
					.setId(ID)
					.setReason(REASON)
					.setResult(RESULT)
					.setTime(TIME);
			// Run
			String returned = unitUnderTest.map(doctorConsultation);
			// Check
			assertEquals(expected, returned);
		}
	}
}
