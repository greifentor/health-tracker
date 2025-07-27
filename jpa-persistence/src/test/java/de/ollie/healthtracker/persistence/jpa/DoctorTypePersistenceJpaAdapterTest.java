package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.DoctorTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorTypePersistenceJpaAdapterTest {

	private static final String NAME_PARTICLE = "name-particle";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private DboFactory dboFactory;

	@Mock
	private DoctorType model;

	@Mock
	private DoctorTypeDbo dbo;

	@Mock
	private DoctorTypeDboMapper mapper;

	@Mock
	private DoctorTypeDboRepository repository;

	@InjectMocks
	private DoctorTypePersistenceJpaAdapter unitUnderTest;

	@Nested
	class findByIdOrNameParticle_String {

		@Test
		void throwsAnException_passingANullValue_asNameParticleOrId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findByIdOrNameParticle(null));
		}

		@Test
		void returnTheExactHit_whenIdMatchesAnObject() {
			// Prepare
			when(repository.findById(UID)).thenReturn(Optional.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			Optional<DoctorType> returned = unitUnderTest.findByIdOrNameParticle(UID.toString());
			// Check
			assertEquals(model, returned.get());
		}

		@Test
		void returnTheExactHit_whenNameParticleMatchesOnlyOneObject() {
			// Prepare
			when(repository.findAllByNameMatch(NAME_PARTICLE)).thenReturn(List.of(dbo));
			when(mapper.toModel(dbo)).thenReturn(model);
			// Run
			Optional<DoctorType> returned = unitUnderTest.findByIdOrNameParticle(NAME_PARTICLE);
			// Check
			assertEquals(model, returned.get());
		}

		@Test
		void throwsAnException_whenNameParticleMatchesMoreThanOneObject() {
			// Prepare
			when(repository.findAllByNameMatch(NAME_PARTICLE)).thenReturn(List.of(dbo, dbo));
			// Run & Check
			assertThrows(TooManyElementsException.class, () -> unitUnderTest.findByIdOrNameParticle(NAME_PARTICLE));
		}

		@Test
		void returnAnEmptyOptional_whenNeitherIdNorNameParticleMatchesAnObject() {
			// Prepare
			when(repository.findAllByNameMatch(NAME_PARTICLE)).thenReturn(List.of());
			// Run
			Optional<DoctorType> returned = unitUnderTest.findByIdOrNameParticle(NAME_PARTICLE);
			// Check
			assertTrue(returned.isEmpty());
		}
	}
}
