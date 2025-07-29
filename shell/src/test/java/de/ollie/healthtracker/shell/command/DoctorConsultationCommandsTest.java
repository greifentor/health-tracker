package de.ollie.healthtracker.shell.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorConsultationToStringMapper;
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
class DoctorConsultationCommandsTest {

	private static final LocalDate DATE = LocalDate.of(2025, 06, 25);
	private static final String DATE_STR = "25.06.2025";
	private static final UUID ID = UUID.randomUUID();
	private static final String MESSAGE = "message";
	private static final String REASON = "reason";
	private static final String RESULT = "result";
	private static final String STRING = "string";
	private static final LocalTime TIME = LocalTime.of(8, 0);
	private static final String TIME_STR = "8:00";

	@Mock
	private Doctor doctor;

	@Mock
	private DoctorConsultation model0;

	@Mock
	private DoctorConsultation model1;

	@Mock
	private DoctorConsultationService doctorConsultationService;

	@Mock
	private DoctorConsultationToStringMapper doctorConsultationToStringMapper;

	@Mock
	private DoctorService doctorService;

	@Mock
	private LocalDateFactory localDateFactory;

	@Mock
	private LocalTimeFactory localTimeFactory;

	@Mock
	private OutputHandler outputHandler;

	@InjectMocks
	private DoctorConsultationCommands unitUnderTest;

	@Nested
	class add_String_String_String_String_String {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(doctorService.findByIdOrNameParticle(ID.toString())).thenReturn(Optional.of(doctor));
			when(doctorConsultationService.createDoctorConsultation(DATE, TIME, doctor, REASON, RESULT)).thenThrow(exception);
			// Run
			String returned = unitUnderTest.addDoctorConsultation(DATE_STR, TIME_STR, ID.toString(), REASON, RESULT);
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsExceptionMessage_whenNoDoctorIsFound_forPassedIdOrNameParticle() {
			// Prepare
			when(doctorService.findByIdOrNameParticle(ID.toString())).thenReturn(Optional.empty());
			// Run
			String returned = unitUnderTest.addDoctorConsultation(DATE_STR, TIME_STR, ID.toString(), REASON, RESULT);
			// Check
			assertEquals(Constants.ERROR + DoctorConsultationCommands.MSG_NO_SUCH_DOCTOR_FOUND + ID, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Prepare
			when(doctorService.findByIdOrNameParticle(ID.toString())).thenReturn(Optional.of(doctor));
			when(doctorConsultationService.createDoctorConsultation(DATE, TIME, doctor, REASON, RESULT)).thenReturn(model0);
			// Run
			String returned = unitUnderTest.addDoctorConsultation(DATE_STR, TIME_STR, ID.toString(), REASON, RESULT);
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
			doThrow(exception).when(doctorConsultationService).deleteDoctorConsultation(ID);
			// Run
			String returned = unitUnderTest.removeDoctorConsultation(ID.toString());
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.removeDoctorConsultation(ID.toString());
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Run
			unitUnderTest.removeDoctorConsultation(ID.toString());
			// Check
			verify(doctorConsultationService, times(1)).deleteDoctorConsultation(UUID.fromString(ID.toString()));
		}
	}

	@Nested
	class list {

		@Test
		void returnsExceptionMessage_whenAnExceptionIsThrownWhileRunningServiceMethod() {
			// Prepare
			RuntimeException exception = new RuntimeException(MESSAGE);
			when(doctorConsultationService.listDoctorConsultations()).thenThrow(exception);
			// Run
			String returned = unitUnderTest.listDoctorConsultations();
			// Check
			assertEquals(Constants.ERROR + MESSAGE, returned);
		}

		@Test
		void returnsOkWhenFinishedCorrectly() {
			// Run
			String returned = unitUnderTest.listDoctorConsultations();
			// Check
			assertEquals(Constants.OK, returned);
		}

		@Test
		void callsTheOutputHandlerForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(doctorConsultationToStringMapper.map(any(DoctorConsultation.class))).thenReturn(STRING);
			when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctorConsultations();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}

		@Test
		void callsTheBloodPressureMeasurementMapperForEachRecordReturnedByServiceMethodCall() {
			// Prepare
			when(doctorConsultationToStringMapper.map(model0)).thenReturn(STRING);
			when(doctorConsultationToStringMapper.map(model1)).thenReturn(STRING);
			when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(model0, model1));
			// Run
			unitUnderTest.listDoctorConsultations();
			// Check
			verify(outputHandler, times(2)).println(anyString());
		}
	}
}
