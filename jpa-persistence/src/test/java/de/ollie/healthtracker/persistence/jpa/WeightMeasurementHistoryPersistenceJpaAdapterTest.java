package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.persistence.jpa.dbo.WeightMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.WeightMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.WeightMeasurementDboRepository;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeightMeasurementHistoryPersistenceJpaAdapterTest {

	private static final int DAYS = 7;

	@Mock
	private WeightMeasurement model;

	@Mock
	private WeightMeasurementDbo dbo;

	@Mock
	private WeightMeasurementDboMapper mapper;

	@Mock
	private WeightMeasurementDboRepository repository;

	@InjectMocks
	private WeightMeasurementHistoryPersistenceJpaAdapter unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void returnsTheMappedMeasurementsOfTheRepository() {
			// Prepare
			when(repository.findAllOfLastDays(DAYS)).thenReturn(List.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			List<WeightMeasurement> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertEquals(List.of(model), returned);
		}
	}
}
