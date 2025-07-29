package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorTypeToStringMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorTypeCommandsTest {

	private static final UUID ID = UUID.randomUUID();
	private static final String NAME = "name";
	private static final String STRING = "string";

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private DoctorType model0;

	@Mock
	private DoctorType model1;

	@Mock
	private DoctorTypeService service;

	@Mock
	private DoctorTypeToStringMapper toStringMapper;

	@InjectMocks
	private DoctorTypeCommands unitUnderTest;

	@Nested
	class addDoctorType_String_String {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(STRING);
			when(service.createDoctorType(NAME)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addDoctorType(NAME);
			// Check
			assertEquals(Constants.ERROR + STRING, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(service.createDoctorType(NAME)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addDoctorType(NAME);
			// Check
			assertEquals(Constants.OK, returned);
		}
	}

	@Nested
	class deleteById_UUID {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(NAME);
			doThrow(exception).when(service).deleteDoctorType(ID);
			// Run
			String returned = unitUnderTest.removeDoctorType(ID.toString());
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeDoctorType(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeDoctorType(ID.toString());
			// Check
			verify(service, times(1)).deleteDoctorType(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(NAME);
			when(service.listDoctorTypes()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listDoctorTypes();
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listDoctorTypes();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(DoctorType.class))).thenReturn(STRING);
			when(service.listDoctorTypes()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctorTypes();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(model0)).thenReturn(STRING);
			when(toStringMapper.map(model1)).thenReturn(STRING);
			when(service.listDoctorTypes()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctorTypes();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
