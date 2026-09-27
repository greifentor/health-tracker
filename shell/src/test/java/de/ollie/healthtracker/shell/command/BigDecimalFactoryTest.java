package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.healthtracker.shell.command.BigDecimalFactory;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BigDecimalFactoryTest {

	@InjectMocks
	private BigDecimalFactory unitUnderTest;

	@Nested
	class create_String {

		@Test
		void throwsAnException_passingANullValueAsString() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.create(null));
		}

		@Test
		void throwsAnException_passingAnInvalidNumberString() {
			assertThrows(NumberFormatException.class, () -> unitUnderTest.create(";op"));
		}

		@Test
		void returnsACorrectValue_passingAValidNumberString() {
			assertEquals(new BigDecimal("47.11"), unitUnderTest.create("47.11"));
		}
	}
}
