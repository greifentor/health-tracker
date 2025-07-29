package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.ManufacturerToStringMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManufacturerCommandsTest {

	private static final UUID ID = UUID.randomUUID();
	private static final String NAME = "name";
	private static final String STRING = "string";

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Manufacturer model0;

	@Mock
	private Manufacturer model1;

	@Mock
	private ManufacturerService service;

	@Mock
	private ManufacturerToStringMapper toStringMapper;

	@InjectMocks
	private ManufacturerCommands unitUnderTest;

	@Nested
	class addManufacturer_String_String {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(STRING);
			when(service.createManufacturer(NAME)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addManufacturer(NAME);
			// Check
			assertEquals(Constants.ERROR + STRING, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(service.createManufacturer(NAME)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addManufacturer(NAME);
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
			doThrow(exception).when(service).deleteManufacturer(ID);
			// Run
			String returned = unitUnderTest.removeManufacturer(ID.toString());
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeManufacturer(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeManufacturer(ID.toString());
			// Check
			verify(service, times(1)).deleteManufacturer(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(NAME);
			when(service.listManufacturers()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listManufacturers();
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listManufacturers();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(Manufacturer.class))).thenReturn(STRING);
			when(service.listManufacturers()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listManufacturers();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(model0)).thenReturn(STRING);
			when(toStringMapper.map(model1)).thenReturn(STRING);
			when(service.listManufacturers()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listManufacturers();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
