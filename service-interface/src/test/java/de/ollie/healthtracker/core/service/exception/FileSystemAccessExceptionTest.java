package de.ollie.healthtracker.core.service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileSystemAccessExceptionTest {

	@Nested
	class constructor_String_Exception {

		private static final Exception EXCEPTION = new RuntimeException();
		private static final String MESSAGE = "message";

		@Test
		void setsTheExceptionCorrectly() {
			assertSame(EXCEPTION, new FileSystemAccessException(MESSAGE, EXCEPTION).getCause());
		}

		@Test
		void setsTheMessageCorrectly() {
			assertEquals(MESSAGE, new FileSystemAccessException(MESSAGE, EXCEPTION).getMessage());
		}
	}
}
