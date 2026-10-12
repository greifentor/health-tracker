package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholConsumptionHistoryPersistencePort;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlcoholConsumptionHistoryServiceImplTest {

	private static final int DAYS = 7;

	@Mock
	private AlcoholConsumptionHistoryPersistencePort alcoholConsumptionHistoryPersistencePort;

	@InjectMocks
	private AlcoholConsumptionHistoryServiceImpl unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Prepare
			List<AlcoholConsumption> expected = List.of();
			when(alcoholConsumptionHistoryPersistencePort.findAllOfLastDays(DAYS)).thenReturn(expected);
			// Run
			List<AlcoholConsumption> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertSame(expected, returned);
		}
	}
}
