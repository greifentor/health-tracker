package de.ollie.healthtracker.core.service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementStatusTest {

	@Nested
	class ofValue_int {

		@ParameterizedTest
		@CsvSource({ "1,GREEN", "2,YELLOW", "3,ORANGE", "4,RED" })
		void returnsTheCorrectIdentifier_passingAnExistingValue(int value, BloodPressureMeasurementStatus expected) {
			assertEquals(expected, BloodPressureMeasurementStatus.ofValue(value));
		}

		@ParameterizedTest
		@CsvSource({ "0", "5", "42" })
		void returnsNull_passingANotExistingValue(int value) {
			assertNull(BloodPressureMeasurementStatus.ofValue(value));
		}
	}
}
