package de.ollie.healthtracker.core.service.exception;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TooManyElementsExceptionTest {

	@Nested
	class constructor {

		@Test
		void returnsANewObject() {
			assertNotNull(new TooManyElementsException());
		}
	}
}
