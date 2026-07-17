package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BodyTemperatureStatusServiceImplTest {

	private final BodyTemperatureStatusServiceImpl unitUnderTest = new BodyTemperatureStatusServiceImpl();

	private BodyTemperatureStatus calculate(double celsius, PointOfMeasurement point) {
		return unitUnderTest.calculateStatus(BigDecimal.valueOf(celsius), point);
	}

	private PointOfMeasurement point(Double min, Double max) {
		return new PointOfMeasurement()
			.setName("point")
			.setRegularMinCelsius(min == null ? null : BigDecimal.valueOf(min))
			.setRegularMaxCelsius(max == null ? null : BigDecimal.valueOf(max));
	}

	@Nested
	class WithoutPointOfMeasurement {

		@Test
		void returnsNormalForAValueWithinTheDefaultRange() {
			// Run
			BodyTemperatureStatus status = calculate(37.0, null);
			// Check
			assertEquals(BodyTemperatureStatus.NORMAL, status);
		}

		@Test
		void returnsBelowForAValueUnderTheDefaultMinimum() {
			// Run
			BodyTemperatureStatus status = calculate(36.4, null);
			// Check
			assertEquals(BodyTemperatureStatus.BELOW, status);
		}

		@Test
		void returnsAboveForAValueOverTheDefaultMaximum() {
			// Run
			BodyTemperatureStatus status = calculate(37.5, null);
			// Check
			assertEquals(BodyTemperatureStatus.ABOVE, status);
		}

		@Test
		void treatsTheDefaultMinimumAsInclusive() {
			// Run
			BodyTemperatureStatus status = calculate(36.5, null);
			// Check
			assertEquals(BodyTemperatureStatus.NORMAL, status);
		}

		@Test
		void treatsTheDefaultMaximumAsInclusive() {
			// Run
			BodyTemperatureStatus status = calculate(37.4, null);
			// Check
			assertEquals(BodyTemperatureStatus.NORMAL, status);
		}
	}

	@Nested
	class WithPointOfMeasurement {

		@Test
		void returnsBelowForAValueUnderThePointsMinimum() {
			// Prepare
			PointOfMeasurement point = point(35.0, 39.0);
			// Run
			BodyTemperatureStatus status = calculate(34.9, point);
			// Check
			assertEquals(BodyTemperatureStatus.BELOW, status);
		}

		@Test
		void returnsAboveForAValueOverThePointsMaximum() {
			// Prepare
			PointOfMeasurement point = point(35.0, 39.0);
			// Run
			BodyTemperatureStatus status = calculate(39.1, point);
			// Check
			assertEquals(BodyTemperatureStatus.ABOVE, status);
		}

		@Test
		void returnsNormalForAValueWithinThePointsRange() {
			// Prepare
			PointOfMeasurement point = point(35.0, 39.0);
			// Run
			BodyTemperatureStatus status = calculate(38.0, point);
			// Check
			assertEquals(BodyTemperatureStatus.NORMAL, status);
		}

		@Test
		void fallsBackToTheDefaultMinimumWhenThePointHasNoMinimum() {
			// Prepare
			PointOfMeasurement point = point(null, null);
			// Run
			BodyTemperatureStatus status = calculate(36.0, point);
			// Check
			assertEquals(BodyTemperatureStatus.BELOW, status);
		}

		@Test
		void fallsBackToTheDefaultMaximumWhenThePointHasNoMaximum() {
			// Prepare
			PointOfMeasurement point = point(null, null);
			// Run
			BodyTemperatureStatus status = calculate(38.0, point);
			// Check
			assertEquals(BodyTemperatureStatus.ABOVE, status);
		}
	}
}
