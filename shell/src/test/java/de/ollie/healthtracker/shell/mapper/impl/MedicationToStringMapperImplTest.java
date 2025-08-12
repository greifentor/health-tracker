package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationToStringMapperImplTest {

	private static final String NAME = "name";
	private static final UUID ID = UUID.randomUUID();
	private static final String MANUFACTURER_NAME = "type-name";

	@Mock
	private Medication medication;

	@Mock
	private Manufacturer manufacturer;

	@InjectMocks
	private MedicationToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals(
				"(ID)                                   Name                                     Manufacturer",
				unitUnderTest.getHeadLine()
			);
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"--------------------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_Medication {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingMedicationWithNoSetAttributes() {
			// Prepare
			String expected = "(                                null) null                                     -";
			// Run
			String returned = unitUnderTest.map(medication);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingAManufacturerWithAllSetAttributes() {
			// Prepare
			String expected = "(" + ID + ") " + NAME + "                                     " + MANUFACTURER_NAME;
			manufacturer = new Manufacturer().setName(MANUFACTURER_NAME);
			medication = new Medication().setId(ID).setManufacturer(manufacturer).setName(NAME);
			// Run
			String returned = unitUnderTest.map(medication);
			// Check
			assertEquals(expected, returned);
		}
	}
}
