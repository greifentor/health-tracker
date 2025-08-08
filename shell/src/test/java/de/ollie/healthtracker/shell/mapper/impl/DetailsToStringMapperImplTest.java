package de.ollie.healthtracker.shell.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DetailsToStringMapperImplTest {

	private static final String DESCRIPTION = "description";
	private static final String ID = "ID";

	@Mock
	private Details details;

	@InjectMocks
	private DetailsToStringMapperImpl unitUnderTest;

	@Nested
	class getHeadLine {

		@Test
		void returnsTheCorrectHeadLine() {
			assertEquals("Id                   Description", unitUnderTest.getHeadLine());
		}
	}

	@Nested
	class getUnderLine {

		@Test
		void returnsTheCorrectUnderLine() {
			assertEquals(
				"----------------------------------------------------------------------------------------------",
				unitUnderTest.getUnderLine()
			);
		}
	}

	@Nested
	class map_Comment {

		@Test
		void returnsANullValue_passingANullValue() {
			assertNull(unitUnderTest.map(null));
		}

		@Test
		void returnsACorrectString_passingACommentWithNoSetAttributes() {
			// Prepare
			String expected = "null                 null";
			// Run
			String returned = unitUnderTest.map(details);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsACorrectString_passingACommentWithAllSetAttributes() {
			// Prepare
			String expected = ID + "                   " + DESCRIPTION;
			details = new Details(ID, DESCRIPTION);
			// Run
			String returned = unitUnderTest.map(details);
			// Check
			assertEquals(expected, returned);
		}
	}
}
