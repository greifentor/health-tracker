package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.CommentDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.CommentDboRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentPersistenceJpaAdapterTest {

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final UUID ID = UUID.randomUUID();

	@Mock
	private Comment model;

	@Mock
	private CommentDbo dbo;

	@Mock
	private CommentDbo dboSaved;

	@Mock
	private DboFactory dboFactory;

	@Mock
	private CommentDboMapper mapper;

	@Mock
	private CommentDboRepository repository;

	@InjectMocks
	private CommentPersistenceJpaAdapter unitUnderTest;

	@Nested
	class create_String_LocalDate_LocalTime {

		@Test
		void returnsANewSavedObject() {
			// Prepare
			when(dboFactory.createComment(CONTENT, DATE_OF_RECORDING)).thenReturn(dbo);
			when(mapper.toModel(dboSaved)).thenReturn(model);
			when(repository.save(dbo)).thenReturn(dboSaved);
			// Run & Check
			assertSame(model, unitUnderTest.create(CONTENT, DATE_OF_RECORDING));
		}
	}

	@Nested
	class deleteById_UUID {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.deleteById(null));
		}

		@Test
		void callsTheRepositoryMethodCorrectly() {
			// Run
			unitUnderTest.deleteById(ID);
			// Check
			verify(repository, times(1)).deleteById(ID);
		}
	}

	@Nested
	class list {

		@Test
		void returnsAMappedList() {
			// Prepare
			when(mapper.toModel(dboSaved)).thenReturn(model);
			when(repository.findAllOrdered()).thenReturn(List.of(dboSaved));
			// Run & Check
			assertEquals(List.of(model), unitUnderTest.list());
		}
	}
}
