package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final UUID ID = UUID.randomUUID();
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);

	@Mock
	private Comment comment;

	@Mock
	private CommentPersistencePort commentPersistencePort;

	@InjectMocks
	private CommentServiceImpl unitUnderTest;

	@Nested
	class createComment_String_LocalDate_LocalTime_String_String {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			when(commentPersistencePort.create(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING)).thenReturn(comment);
			// Run
			Comment returned = unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING, TIME_OF_RECORDING);
			// Check
			assertSame(comment, returned);
		}
	}

	@Nested
	class deleteComment_UUID {

		@Test
		void callsThePersistencePortMethodCorrectly() {
			// Run
			unitUnderTest.deleteComment(ID);
			// Check
			verify(commentPersistencePort, times(1)).deleteById(ID);
		}
	}

	@Nested
	class list {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			List<Comment> list = List.of(comment);
			when(commentPersistencePort.list()).thenReturn(list);
			// Run
			List<Comment> returned = unitUnderTest.listComments();
			// Check
			assertSame(list, returned);
		}
	}
}
