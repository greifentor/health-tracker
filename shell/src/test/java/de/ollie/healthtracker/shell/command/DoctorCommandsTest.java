package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
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
class DoctorCommandsTest {

	private static final String CONTENT = "content";
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 06, 25);
	private static final String DATE_OF_RECORDING_STR = "25.06.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String MESSAGE = "message";
	private static final String NAME = "name";
	private static final String ID_OR_NAME_PARTICLE = "id-or-name-particle";
	private static final String STRING = "string";
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(8, 0);
	private static final String TIME_OF_RECORDING_STR = "8:00";

	@Mock
	private OutputHandler outputHandler;

	@Mock
	private Doctor model0;

	@Mock
	private Doctor model1;

	@Mock
	private DoctorType doctorType;

	@Mock
	private DoctorService service;

	@Mock
	private DoctorTypeService doctorTypeService;

	@Mock
	private DoctorToStringMapper toStringMapper;

	@InjectMocks
	private DoctorCommands unitUnderTest;

	@Nested
	class add_String_String {

		@Test
		void returnAnErrorMessage_whenServiceCallThrowsAnException() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(doctorTypeService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.of(doctorType));
			when(service.createDoctor(NAME, doctorType)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addDoctor(NAME, ID_OR_NAME_PARTICLE);
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnAnErrorMessage_whenNoMatchingDoctorTypeFound() {
			// Prepare
			when(doctorTypeService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.addDoctor(NAME, ID_OR_NAME_PARTICLE);
			// Check
			assertEquals(Constants.ERROR + DoctorCommands.MSG_NO_SUCH_DOCTOR_TYPE_FOUND + ID_OR_NAME_PARTICLE, returned);
		}

		@Test
		void returnsOk_whenNoErrorIsDetected() {
			// Prepare
			when(doctorTypeService.findByIdOrNameParticle(ID_OR_NAME_PARTICLE)).thenReturn(Optional.of(doctorType));
			when(service.createDoctor(NAME, doctorType)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addDoctor(NAME, ID_OR_NAME_PARTICLE);
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
			doThrow(exception).when(service).deleteDoctor(ID);
			// Run
			String returned = unitUnderTest.removeDoctor(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeDoctor(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeDoctor(ID.toString());
			// Check
			verify(service, times(1)).deleteDoctor(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(service.listDoctors()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listDoctors();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listDoctors();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(any(Doctor.class))).thenReturn(STRING);
			when(service.listDoctors()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctors();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(toStringMapper.map(model0)).thenReturn(STRING);
			when(toStringMapper.map(model1)).thenReturn(STRING);
			when(service.listDoctors()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctors();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
