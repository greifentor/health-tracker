package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.BloodPressureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BloodPressureMeasurementDboRepository;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementHistoryPersistenceJpaAdapterTest {

	private static final int DAYS = 7;

	@Mock
	private BloodPressureMeasurement model;

	@Mock
	private BloodPressureMeasurementDbo dbo;

	@Mock
	private BloodPressureMeasurementDboMapper mapper;

	@Mock
	private BloodPressureMeasurementDboRepository repository;

	@InjectMocks
	private BloodPressureMeasurementHistoryPersistenceJpaAdapter unitUnderTest;

	@Nested
	class findAllOfLastDays_int {

		@Test
		void returnsTheMappedMeasurementsOfTheRepository() {
			// Prepare
			when(repository.findAllOfLastDays(DAYS)).thenReturn(List.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			List<BloodPressureMeasurement> returned = unitUnderTest.findAllOfLastDays(DAYS);
			// Check
			assertEquals(List.of(model), returned);
		}
	}
}
