package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufacturerToStringMapperImplTest {

	private static final String NAME = "name";
	private static final UUID ID = UUID.randomUUID();

	@Mock
	private Manufacturer manufacturer;

	@InjectMocks
	private ManufacturerToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals("(ID)                                   Name", unitUnderTest.getHeadLine());
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
	class map_Manufacturer {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingManufacturerWithNoSetAttributes() {
			// Prepare
			String expected = "(                                null) null";
			// Run
			String returned = unitUnderTest.map(manufacturer);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingAManufacturerWithAllSetAttributes() {
			// Prepare
			String expected = "(" + ID + ") " + NAME;
			manufacturer = new Manufacturer().setId(ID).setName(NAME);
			// Run
			String returned = unitUnderTest.map(manufacturer);
			// Check
			assertEquals(expected, returned);
		}
	}
}
