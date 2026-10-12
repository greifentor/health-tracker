package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.AlcoholConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholConsumptionDboRepository;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlcoholConsumptionHistoryPersistenceJpaAdapterTest {

	private static final int DAYS = 7;

	@Mock
	private AlcoholConsumption model;

	@Mock
	private AlcoholConsumptionDbo dbo;

	@Mock
	private AlcoholConsumptionDboMapper mapper;

	@Mock
	private AlcoholConsumptionDboRepository repository;

	@InjectMocks
	private AlcoholConsumptionHistoryPersistenceJpaAdapter unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void returnsTheMappedConsumptionsOfTheRepository() {
			// Prepare
			when(repository.findAllOfLastDays(DAYS)).thenReturn(List.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			List<AlcoholConsumption> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertEquals(List.of(model), returned);
		}
	}
}
