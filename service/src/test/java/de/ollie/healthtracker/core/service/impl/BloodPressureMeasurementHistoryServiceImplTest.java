package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementHistoryPersistencePort;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementHistoryServiceImplTest {

	private static final int DAYS = 7;

	@Mock
	private BloodPressureMeasurementHistoryPersistencePort bloodPressureMeasurementHistoryPersistencePort;

	@InjectMocks
	private BloodPressureMeasurementHistoryServiceImpl unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Prepare
			List<BloodPressureMeasurement> expected = List.of();
			when(bloodPressureMeasurementHistoryPersistencePort.findAllOfLastDays(DAYS)).thenReturn(expected);
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertSame(expected, returned);
		}
	}
}
