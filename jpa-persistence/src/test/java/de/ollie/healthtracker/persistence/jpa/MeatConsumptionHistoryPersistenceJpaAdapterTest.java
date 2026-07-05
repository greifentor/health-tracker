package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MeatConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MeatConsumptionDboRepository;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeatConsumptionHistoryPersistenceJpaAdapterTest {

	private static final int DAYS = 30;

	@Mock
	private MeatConsumption model;

	@Mock
	private MeatConsumptionDbo dbo;

	@Mock
	private MeatConsumptionDboMapper mapper;

	@Mock
	private MeatConsumptionDboRepository repository;

	@InjectMocks
	private MeatConsumptionHistoryPersistenceJpaAdapter unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void returnsTheMappedConsumptionsOfTheRepository() {
			// Prepare
			when(repository.findAllOfLastDays(DAYS)).thenReturn(List.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			List<MeatConsumption> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertEquals(List.of(model), returned);
		}
	}
}
