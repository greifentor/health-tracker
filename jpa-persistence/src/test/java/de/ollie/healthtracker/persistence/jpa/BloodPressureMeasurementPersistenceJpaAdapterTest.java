package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.persistence.jpa.BloodPressureMeasurementPersistenceJpaAdapter;
import de.ollie.healthtracker.persistence.jpa.DboFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.BloodPressureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BloodPressureMeasurementDboRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BloodPressureMeasurementPersistenceJpaAdapterTest {

	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final int DIA_MM_HG = 70;
	private static final int PULSE_PER_MINUTE = 60;
	private static final BloodPressureMeasurementStatus STATE = BloodPressureMeasurementStatus.GREEN;
	private static final BloodPressureMeasurementStatusDbo STATE_DBO = BloodPressureMeasurementStatusDbo.GREEN;
	private static final int SYS_MM_HG = 130;
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);

	@Mock
	private BloodPressureMeasurement model;

	@Mock
	private BloodPressureMeasurementDbo dbo;

	@Mock
	private BloodPressureMeasurementDbo dboSaved;

	@Mock
	private DboFactory dboFactory;

	@Mock
	private BloodPressureMeasurementDboMapper mapper;

	@Mock
	private BloodPressureMeasurementDboRepository repository;

	@InjectMocks
	private BloodPressureMeasurementPersistenceJpaAdapter unitUnderTest;

	@Nested
	class create_int_int_int_BloodPresureMeassurementStatus_LocalDate_LocalTime {

		@Test
		void returnsANewSavedObject() {
			// Prepare
			when(dboFactory.create(SYS_MM_HG, PULSE_PER_MINUTE, DIA_MM_HG, STATE_DBO, DATE_OF_RECORDING, TIME_OF_RECORDING))
				.thenReturn(dbo);
			when(mapper.toDbo(STATE)).thenReturn(STATE_DBO);
			when(mapper.toModel(dboSaved)).thenReturn(model);
			when(repository.save(dbo)).thenReturn(dboSaved);
			// Run & Check
			assertSame(
				model,
				unitUnderTest.create(SYS_MM_HG, PULSE_PER_MINUTE, DIA_MM_HG, STATE, DATE_OF_RECORDING, TIME_OF_RECORDING)
			);
		}
	}
}
