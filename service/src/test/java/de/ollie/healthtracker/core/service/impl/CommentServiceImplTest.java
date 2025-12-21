package de.ollie.healthtracker.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
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
class CommentServiceImplTest {

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final UUID ID = UUID.randomUUID();

	@Mock
	private Comment comment;

	@Mock
	private CommentType commentType;

	@Mock
	private CommentPersistencePort commentPersistencePort;

	@InjectMocks
	private CommentServiceImpl unitUnderTest;

	@Nested
	class createComment_String_LocalDate_LocalTime_String_String {

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			when(commentPersistencePort.create(commentType, CONTENT, DATE_OF_RECORDING)).thenReturn(comment);
			// Run
			Comment returned = unitUnderTest.createComment(commentType, CONTENT, DATE_OF_RECORDING);
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

	@Nested
	class listCommentsBetweenDatesOrderedByDateAndContentTypeName_LocalDate_LocalDate {

		private static final LocalDate FROM = LocalDate.of(2025, 12, 1);
		private static final LocalDate TO = LocalDate.of(2025, 12, 31);

		@Test
		void throwsException_passingANullValue_asFromDate() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(null, TO)
			);
		}

		@Test
		void throwsException_passingANullValue_asToDate() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(FROM, null)
			);
		}

		@Test
		void throwsException_passingFromDateAfterToDate() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(TO, FROM)
			);
		}

		@Test
		void returnsTheResultOfThePersistencePortMethodCall() {
			// Prepare
			List<Comment> list = List.of(comment);
			when(commentPersistencePort.listBetweenDatesOrderedByDateAndCommentTypeName(FROM, TO)).thenReturn(list);
			// Run
			List<Comment> returned = unitUnderTest.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(FROM, TO);
			// Check
			assertSame(list, returned);
		}
	}
}
