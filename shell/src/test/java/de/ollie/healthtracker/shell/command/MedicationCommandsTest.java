package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationToStringMapper;
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
class MedicationCommandsTest {

	private static final UUID ID = UUID.randomUUID();
	private static final String ID_OR_NAME_PARTICLE = "id-or-name-particle";
	private static final String NAME = "name";
	private static final String STRING = "string";

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Manufacturer manufacturer;

	@Mock
	private Medication model0;

	@Mock
	private Medication model1;

	@Mock
	private ManufacturerService manufacturerService;

	@Mock
	private MedicationService service;

	@Mock
	private MedicationToStringMapper toStringMapper;

	@InjectMocks
	private MedicationCommands unitUnderTest;

	@Nested
	class addMedication_String_String {

		@Test
		void returnsAnError_whenThereIsNoManufacturerForThePassedIdOrNameParticle() {
			// Prepare
			when(manufacturerService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.addMedication(NAME, ID_OR_NAME_PARTICLE);
			// Check
			assertEquals(Constants.ERROR + MedicationCommands.MSG_NO_SUCH_MANUFACTURER_FOUND + ID_OR_NAME_PARTICLE, returned);
		}

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(STRING);
			when(manufacturerService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.of(manufacturer));
			when(service.createMedication(NAME, manufacturer)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addMedication(NAME, ID_OR_NAME_PARTICLE);
			// Check
			assertEquals(Constants.ERROR + STRING, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(manufacturerService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.of(manufacturer));
			when(service.createMedication(NAME, manufacturer)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addMedication(NAME, ID_OR_NAME_PARTICLE);
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
			doThrow(exception).when(service).deleteMedication(ID);
			// Run
			String returned = unitUnderTest.removeMedication(ID.toString());
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeMedication(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeMedication(ID.toString());
			// Check
			verify(service, times(1)).deleteMedication(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(NAME);
			when(service.listMedications()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listMedications();
			// Check
			assertEquals(Constants.ERROR + NAME, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listMedications();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(Medication.class))).thenReturn(STRING);
			when(service.listMedications()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listMedications();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(model0)).thenReturn(STRING);
			when(toStringMapper.map(model1)).thenReturn(STRING);
			when(service.listMedications()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listMedications();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
