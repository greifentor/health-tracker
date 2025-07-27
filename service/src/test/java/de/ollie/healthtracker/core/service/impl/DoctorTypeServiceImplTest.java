package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorTypePersistencePort;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorTypeServiceImplTest {

	private static final String NAME_PARTICLE_OR_ID = "name-particle-or-id";

	@Mock
	private DoctorTypePersistencePort doctorTypePersistencePort;

	@InjectMocks
	private DoctorTypeServiceImpl unitUnderTest;

	@Nested
	class findAllByIdOrNameParticle_String {

		@Test
		void callsPersistencePortMethodCorrectly() {
			// Prepare
			Optional<DoctorType> expected = Optional.empty();
			when(doctorTypePersistencePort.findByIdOrNameParticle(NAME_PARTICLE_OR_ID)).thenReturn(expected);
			// Run
			Optional<DoctorType> returned = unitUnderTest.findByIdOrNameParticle(NAME_PARTICLE_OR_ID);
			// Check
			assertSame(expected, returned);
		}
	}
}
