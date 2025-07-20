package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.DoctorType;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorTypeToStringMapperImplTest {

	private static final String NAME = "name";
	private static final UUID ID = UUID.randomUUID();

	@Mock
	private DoctorType doctorType;

	@InjectMocks
	private DoctorTypeToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals("(ID)                                   Content", unitUnderTest.getHeadLine());
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"-----------------------------------------------------------------------------------",
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
		void returnsACorrectString_passingDoctorTypeWithNoSetAttributes() {
			// Prepare
			String expected = "(                                null) -";
			// Run
			String returned = unitUnderTest.map(doctorType);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingADoctorTypeWithAllSetAttributes() {
			// Prepare
			String expected = "(" + ID + ") " + NAME;
			doctorType = new DoctorType().setId(ID).setName(NAME);
			// Run
			String returned = unitUnderTest.map(doctorType);
			// Check
			assertEquals(expected, returned);
		}
	}
}
