package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationUnitToStringMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MedicationUnitUnitCommandsTest {

	private static final UUID ID = UUID.randomUUID();
	private static final String NAME = "name";
	private static final String STRING = "string";
	private static final String TOKEN = "token";

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private MedicationUnit model0;

	@Mock
	private MedicationUnit model1;

	@Mock
	private MedicationUnitService service;

	@Mock
	private MedicationUnitToStringMapper toStringMapper;

	@InjectMocks
	private MedicationUnitCommands unitUnderTest;

	@Nested
	class addMedicationUnit_String_String {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(STRING);
			when(service.createMedicationUnit(NAME, TOKEN)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addMedicationUnit(NAME, TOKEN);
			// Check
			assertEquals(Constants.ERROR + STRING, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(service.createMedicationUnit(NAME, TOKEN)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addMedicationUnit(NAME, TOKEN);
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
			doThrow(exception).when(service).deleteMedicationUnit(ID);
			// Run
			String returned = unitUnderTest.removeMedicationUnit(ID.toString());
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeMedicationUnit(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeMedicationUnit(ID.toString());
			// Check
			verify(service, times(1)).deleteMedicationUnit(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(NAME);
			when(service.listMedicationUnits()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listMedicationUnits();
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listMedicationUnits();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(MedicationUnit.class))).thenReturn(STRING);
			when(service.listMedicationUnits()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listMedicationUnits();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(model0)).thenReturn(STRING);
			when(toStringMapper.map(model1)).thenReturn(STRING);
			when(service.listMedicationUnits()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listMedicationUnits();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
