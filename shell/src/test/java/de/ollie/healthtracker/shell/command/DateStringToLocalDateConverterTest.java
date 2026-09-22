package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateStringToLocalDateConverterTest {

	@InjectMocks
	private DateStringToLocalDateConverter unitUnderTest;

	@Nested
	class convert_String {

		@Test
		void throwsAnException_passingANullValueAsDateString() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.convert(null));
		}

		@Test
		void throwsAnException_passingAStringWithNoDateContent() {
			assertThrows(DateTimeParseException.class, () -> unitUnderTest.convert(";op"));
		}

		@Test
		void returnsALocalDateObject_passingAStringWithAValidDateContent() {
			// Prepare
			LocalDate expected = LocalDate.of(2000, 4, 7);
			// Run & Check
			assertEquals(expected, unitUnderTest.convert("2000-04-07"));
		}
	}
}
