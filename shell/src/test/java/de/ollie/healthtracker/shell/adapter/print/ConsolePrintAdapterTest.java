package de.ollie.healthtracker.shell.adapter.print;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsolePrintAdapterTest {

	@InjectMocks
	private ConsolePrintAdapter unitUnderTest;

	@Nested
	class getDetails {

		@Test
		void returnsTheCorrectDetailsObject() {
			assertEquals(new Details(ConsolePrintAdapter.ID, ConsolePrintAdapter.DESCRIPTION), unitUnderTest.getDetails());
		}
	}
}
