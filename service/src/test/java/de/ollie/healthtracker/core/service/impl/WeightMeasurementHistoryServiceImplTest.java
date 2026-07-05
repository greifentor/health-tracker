package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.WeightMeasurementHistoryPersistencePort;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeightMeasurementHistoryServiceImplTest {

	private static final int DAYS = 7;

	@Mock
	private WeightMeasurementHistoryPersistencePort weightMeasurementHistoryPersistencePort;

	@InjectMocks
	private WeightMeasurementHistoryServiceImpl unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Prepare
			List<WeightMeasurement> expected = List.of();
			when(weightMeasurementHistoryPersistencePort.findAllOfLastDays(DAYS)).thenReturn(expected);
			// Run
			List<WeightMeasurement> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertSame(expected, returned);
		}
	}
}
