package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorToStringMapperImplTest {

	private static final String NAME = "name";
	private static final UUID ID = UUID.randomUUID();
	private static final String TYPE_NAME = "type-name";

	@Mock
	private Doctor doctor;

	@Mock
	private DoctorType doctorType;

	@InjectMocks
	private DoctorToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals(
				"(ID)                                   Name                           Typ",
				unitUnderTest.getHeadLine()
			);
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"-------------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_DoctorType {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingDoctorWithNoSetAttributes() {
			// Prepare
			String expected = "(                                null) null                           -";
			// Run
			String returned = unitUnderTest.map(doctor);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingADoctorTypeWithAllSetAttributes() {
			// Prepare
			String expected = "(" + ID + ") " + NAME + "                           " + TYPE_NAME;
			doctorType = new DoctorType().setName(TYPE_NAME);
			doctor = new Doctor().setId(ID).setName(NAME).setDoctorType(doctorType);
			// Run
			String returned = unitUnderTest.map(doctor);
			// Check
			assertEquals(expected, returned);
		}
	}
}
