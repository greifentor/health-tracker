package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeStringToLocalDateConverterTest {

	@InjectMocks
	private TimeStringToLocalDateConverter unitUnderTest;

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
			LocalTime expected = LocalTime.of(23, 15, 0);
			// Run & Check
			assertEquals(expected, unitUnderTest.convert("23:15"));
		}
	}
}
