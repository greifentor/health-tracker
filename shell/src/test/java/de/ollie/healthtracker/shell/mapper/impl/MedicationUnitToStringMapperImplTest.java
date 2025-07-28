package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationUnitToStringMapperImplTest {

	private static final UUID ID = UUID.randomUUID();
	private static final String NAME = "name";
	private static final String TOKEN = "token";

	@Mock
	private MedicationUnit medicationUnit;

	@InjectMocks
	private MedicationUnitToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals(
				"(ID)                                   Name                           Token",
				unitUnderTest.getHeadLine()
			);
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"--------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_MedicationUnit {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingMedicationUnitWithNoSetAttributes() {
			// Prepare
			String expected = "(                                null) null                           null";
			// Run
			String returned = unitUnderTest.map(medicationUnit);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingAMedicationUnitWithAllSetAttributes() {
			// Prepare
			String expected = "(" + ID + ") " + NAME + "                           " + TOKEN;
			medicationUnit = new MedicationUnit().setId(ID).setName(NAME).setToken(TOKEN);
			// Run
			String returned = unitUnderTest.map(medicationUnit);
			// Check
			assertEquals(expected, returned);
		}
	}
}
