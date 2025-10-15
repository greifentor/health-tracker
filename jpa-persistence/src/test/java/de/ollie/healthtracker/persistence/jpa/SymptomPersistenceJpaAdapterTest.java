package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.SymptomDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.SymptomDboRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SymptomPersistenceJpaAdapterTest {

	private static final LocalDate LOCAL_DATE = LocalDate.of(2025, 10, 15);

	@Mock
	private DboFactory dboFactory;

	@Mock
	private SymptomDboMapper mapper;

	@Mock
	private SymptomDboRepository repository;

	@InjectMocks
	private SymptomPersistenceJpaAdapter unitUnderTest;

	@Nested
	class findAllByDateOfRecording_LocalDate {

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.findAllByDateOfRecording(null));
		}

		@Test
		void returnsTheMappedResultOfTheRepositoryMethodCall() {
			// Prepare
			SymptomDbo dbo = mock(SymptomDbo.class);
			Symptom mapped = mock(Symptom.class);
			List<Symptom> expected = List.of(mapped);
			when(mapper.toModel(dbo)).thenReturn(mapped);
			when(repository.findAllByDateOfRecording(LOCAL_DATE)).thenReturn(List.of(dbo));
			// Run
			List<Symptom> returned = unitUnderTest.findAllByDateOfRecording(LOCAL_DATE);
			// Check
			assertEquals(expected, returned);
		}
	}

	@Nested
	class getMaxDateOfRecording {

		@Test
		void returnsTheResultOfTheRepositoryMethodCall() {
			// Prepare
			when(repository.getMaxDateOfRecording()).thenReturn(LOCAL_DATE);
			// Run & Check
			assertSame(LOCAL_DATE, unitUnderTest.getMaxDateOfRecording());
		}
	}
}
