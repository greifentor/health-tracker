package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.port.persistence.MeatConsumptionHistoryPersistencePort;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeatConsumptionHistoryServiceImplTest {

	private static final int DAYS = 30;

	@Mock
	private MeatConsumptionHistoryPersistencePort meatConsumptionHistoryPersistencePort;

	@InjectMocks
	private MeatConsumptionHistoryServiceImpl unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Prepare
			List<MeatConsumption> expected = List.of();
			when(meatConsumptionHistoryPersistencePort.findAllOfLastDays(DAYS)).thenReturn(expected);
			// Run
			List<MeatConsumption> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertSame(expected, returned);
		}
	}
}
