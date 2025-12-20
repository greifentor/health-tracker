package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.CommentToStringMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentCommandsTest {

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String MESSAGE = "message";
	private static final String STRING = "string";

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Comment comment0;

	@Mock
	private Comment comment1;

	@Mock
	private CommentService service;

	@Mock
	private CommentToStringMapper toStringMapper;

	@InjectMocks
	private CommentCommands unitUnderTest;

	@Nested
	class add_int_int_int_String_LocalDate_LocalTime {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(service.createComment(null, CONTENT, DATE_OF_RECORDING)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addComment(CONTENT, DATE_OF_RECORDING_STR);
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(service.createComment(null, CONTENT, DATE_OF_RECORDING)).thenReturn(comment0);
			// Run
			String returned = unitUnderTest.addComment(CONTENT, DATE_OF_RECORDING_STR);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void returnsOk_whenDateOfMeasurementNotSet() {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(service.createComment(null, CONTENT, DATE_OF_RECORDING)).thenReturn(comment0);
			// Run
			String returned = unitUnderTest.addComment(CONTENT, null);
			// Check
			assertEquals(Constants.OK, returned);
		}

		@ParameterizedTest
		@CsvSource({ "Today", "today", "TODAY", "TD", "Td", "td" })
		void returnsOk_whenDateOfMeasurementIsToday(String today) {
			// Prepare
			when(localDateFactory.now()).thenReturn(DATE_OF_RECORDING);
			when(service.createComment(null, CONTENT, DATE_OF_RECORDING)).thenReturn(comment0);
			// Run
			String returned = unitUnderTest.addComment(CONTENT, today);
			// Check
			assertEquals(Constants.OK, returned);
		}
	}

	@Nested
	class deleteById_UUID {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			doThrow(exception).when(service).deleteComment(ID);
			// Run
			String returned = unitUnderTest.removeComment(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeComment(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeComment(ID.toString());
			// Check
			verify(service, times(1)).deleteComment(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(service.listComments()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listComments();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listComments();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(Comment.class))).thenReturn(STRING);
			when(service.listComments()).thenReturn(List.of(comment0, comment1));
			// Run
			unitUnderTest.listComments();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(comment0)).thenReturn(STRING);
			when(toStringMapper.map(comment1)).thenReturn(STRING);
			when(service.listComments()).thenReturn(List.of(comment0, comment1));
			// Run
			unitUnderTest.listComments();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
