package de.ollie.healthtracker.persistence.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.ManufacturerDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationUnitDboRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DboFactoryTest {

	private static final UUID ANOTHER_ID = UUID.randomUUID();
	private static final String CONTENT = "content";
	private static final LocalDate DATE = LocalDate.of(2025, 7, 28);
	private static final LocalDate DATE_OF_RECORDING = LocalDate.of(2025, 6, 17);
	private static final String DESCRIPTION = "description";
	private static final int DIA_MM_HG = 70;
	private static final UUID ID = UUID.randomUUID();
	private static final boolean IRREGULAR_HEARTBEAT = false;
	private static final String NAME = "name";
	private static final int PULSE_PER_MINUTE = 60;
	private static final String REASON = "reason";
	private static final String RESULT = "result";
	private static final BloodPressureMeasurementStatusDbo STATUS = BloodPressureMeasurementStatusDbo.GREEN;
	private static final int SYS_MM_HG = 130;
	private static final LocalTime TIME = LocalTime.of(12, 52, 42);
	private static final LocalTime TIME_OF_RECORDING = LocalTime.of(23, 31, 42);
	private static final String TOKEN = "token";
	private static final BigDecimal UNIT_COUNT = new BigDecimal(42);

	@Mock
	private DoctorDbo doctorDbo;

	@Mock
	private DoctorDboRepository doctorRepository;

	@Mock
	private DoctorTypeDbo doctorTypeDbo;

	@Mock
	private DoctorTypeDboRepository doctorTypeRepository;

	@Mock
	private ManufacturerDbo manufacturerDbo;

	@Mock
	private ManufacturerDboRepository manufacturerDboRepository;

	@Mock
	private MedicationDbo medicationDbo;

	@Mock
	private MedicationDboRepository medicationDboRepository;

	@Mock
	private MedicationUnitDbo medicationUnitDbo;

	@Mock
	private MedicationUnitDboRepository medicationUnitDboRepository;

	@Mock
	private UuidFactory uuidFactory;

	@InjectMocks
	private DboFactory unitUnderTest;

	@Nested
	class createBloodPressureMeasurement_int_int_int_BloodPresureMeasurementStatus_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						null,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						SYS_MM_HG,
						TIME_OF_RECORDING,
						STATUS,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asDiaMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						DATE_OF_RECORDING,
						0,
						PULSE_PER_MINUTE,
						SYS_MM_HG,
						TIME_OF_RECORDING,
						STATUS,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asPulsePerMinute() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						DATE_OF_RECORDING,
						DIA_MM_HG,
						0,
						SYS_MM_HG,
						TIME_OF_RECORDING,
						STATUS,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asState() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						DATE_OF_RECORDING,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						SYS_MM_HG,
						TIME_OF_RECORDING,
						null,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void throwsAnException_passingAValueLesserThanOne_asSysMmHg() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						DATE_OF_RECORDING,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						0,
						TIME_OF_RECORDING,
						STATUS,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTimeOfRecording() {
			assertThrows(
				IllegalArgumentException.class,
				() ->
					unitUnderTest.createBloodPressureMeasurement(
						DATE_OF_RECORDING,
						DIA_MM_HG,
						PULSE_PER_MINUTE,
						SYS_MM_HG,
						null,
						STATUS,
						IRREGULAR_HEARTBEAT
					)
			);
		}

		@Test
		void returnANewObject() {
			assertNotNull(
				unitUnderTest.createBloodPressureMeasurement(
					DATE_OF_RECORDING,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					SYS_MM_HG,
					TIME_OF_RECORDING,
					STATUS,
					IRREGULAR_HEARTBEAT
				)
			);
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.createBloodPressureMeasurement(
					DATE_OF_RECORDING,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					SYS_MM_HG,
					TIME_OF_RECORDING,
					STATUS,
					IRREGULAR_HEARTBEAT
				),
				unitUnderTest.createBloodPressureMeasurement(
					DATE_OF_RECORDING,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					SYS_MM_HG,
					TIME_OF_RECORDING,
					STATUS,
					IRREGULAR_HEARTBEAT
				)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			BloodPressureMeasurementDbo expected = new BloodPressureMeasurementDbo()
				.setDateOfRecording(DATE_OF_RECORDING)
				.setDiaMmHg(DIA_MM_HG)
				.setId(ID)
				.setPulsePerMinute(PULSE_PER_MINUTE)
				.setStatus(STATUS)
				.setSysMmHg(SYS_MM_HG)
				.setTimeOfRecording(TIME_OF_RECORDING);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(
				expected,
				unitUnderTest.createBloodPressureMeasurement(
					DATE_OF_RECORDING,
					DIA_MM_HG,
					PULSE_PER_MINUTE,
					SYS_MM_HG,
					TIME_OF_RECORDING,
					STATUS,
					IRREGULAR_HEARTBEAT
				)
			);
		}
	}

	@Nested
	class createComment_String_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingABlankString_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createComment("\n\t\r ", DATE_OF_RECORDING));
		}

		@Test
		void throwsAnException_passingANullValue_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createComment(null, DATE_OF_RECORDING));
		}

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createComment(CONTENT, null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING),
				unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			CommentDbo expected = new CommentDbo().setContent(CONTENT).setDateOfRecording(DATE_OF_RECORDING).setId(ID);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createComment(CONTENT, DATE_OF_RECORDING));
		}
	}

	@Nested
	class createDoctor_String_UUID {

		@Test
		void throwsAnException_passingABlankString_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctor("\n\t\r ", ID));
		}

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctor(null, ID));
		}

		@Test
		void throwsAnException_passingANullValue_asDoctorTypeId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctor(NAME, null));
		}

		@Test
		void returnANewObject() {
			// Prepare
			when(doctorTypeRepository.findById(ID)).thenReturn(Optional.of(doctorTypeDbo));
			// Run & Check
			assertNotNull(unitUnderTest.createDoctor(NAME, ID));
		}

		@Test
		void returnANewObject_onEachCall() {
			// Prepare
			when(doctorTypeRepository.findById(ID)).thenReturn(Optional.of(doctorTypeDbo));
			// Run & Check
			assertNotSame(unitUnderTest.createDoctor(NAME, ID), unitUnderTest.createDoctor(NAME, ID));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			DoctorDbo expected = new DoctorDbo().setDoctorType(doctorTypeDbo).setId(ID).setName(NAME);
			when(doctorTypeRepository.findById(ID)).thenReturn(Optional.of(doctorTypeDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createDoctor(NAME, ID));
		}

		@Test
		void throwsAnException_whenThereIsNoDoctorTypeForThePassedId() {
			// Prepare
			when(doctorTypeRepository.findById(ID)).thenReturn(Optional.empty());
			// Run & Check
			assertThrows(NoSuchElementException.class, () -> unitUnderTest.createDoctor(NAME, ID));
		}
	}

	@Nested
	class createDoctorConsultation_LocalDate_LocalTime_UUID_String_String {

		@Test
		void throwsAnException_passingANullValue_asDate() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(null, TIME, ID, REASON, RESULT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asDoctorId() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, null, REASON, RESULT)
			);
		}

		@Test
		void throwsAnException_passingABlankString_asReason() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, ID, "\n\t\r ", RESULT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asResult() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, ID, null, RESULT)
			);
		}

		@Test
		void throwsAnException_passingABlankString_asResult() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, "\n\t\r ")
			);
		}

		@Test
		void throwsAnException_passingANullValue_asReason() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, null)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTime() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, null, ID, REASON, RESULT)
			);
		}

		@Test
		void returnANewObject() {
			// Prepare
			when(doctorRepository.findById(ID)).thenReturn(Optional.of(doctorDbo));
			// Run & Check
			assertNotNull(unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, RESULT));
		}

		@Test
		void returnANewObject_onEachCall() {
			// Prepare
			when(doctorRepository.findById(ID)).thenReturn(Optional.of(doctorDbo));
			// Run & Check
			assertNotSame(
				unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, RESULT),
				unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, RESULT)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			DoctorConsultationDbo expected = new DoctorConsultationDbo()
				.setDate(DATE)
				.setDoctor(doctorDbo)
				.setId(ID)
				.setReason(REASON)
				.setResult(RESULT)
				.setTime(TIME);
			when(doctorRepository.findById(ID)).thenReturn(Optional.of(doctorDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, RESULT));
		}

		@Test
		void throwsAnException_whenThereIsNoDoctorForThePassedId() {
			// Prepare
			when(doctorRepository.findById(ID)).thenReturn(Optional.empty());
			// Run & Check
			assertThrows(
				NoSuchElementException.class,
				() -> unitUnderTest.createDoctorConsultation(DATE, TIME, ID, REASON, RESULT)
			);
		}
	}

	@Nested
	class createDoctorType_String {

		@Test
		void throwsAnException_passingABlankString_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctorType("\n\t\r "));
		}

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createDoctorType(null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createDoctorType(CONTENT));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(unitUnderTest.createDoctorType(CONTENT), unitUnderTest.createDoctorType(CONTENT));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			DoctorTypeDbo expected = new DoctorTypeDbo().setId(ID).setName(NAME);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createDoctorType(NAME));
		}
	}

	@Nested
	class createManufacturer_String {

		@Test
		void throwsAnException_passingABlankString_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createManufacturer("\n\t\r "));
		}

		@Test
		void throwsAnException_passingANullValue_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createManufacturer(null));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(unitUnderTest.createManufacturer(NAME), unitUnderTest.createManufacturer(NAME));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			ManufacturerDbo expected = new ManufacturerDbo().setId(ID).setName(NAME);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createManufacturer(NAME));
		}
	}

	@Nested
	class createMedication_String_UUID {

		@Test
		void throwsAnException_passingABlankString_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedication("\n\t\r ", ID));
		}

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedication(null, ID));
		}

		@Test
		void throwsAnException_passingANullValue_asManufacturerId() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedication(NAME, null));
		}

		@Test
		void returnANewObject() {
			// Prepare
			when(manufacturerDboRepository.findById(ID)).thenReturn(Optional.of(manufacturerDbo));
			// Run & Check
			assertNotNull(unitUnderTest.createMedication(NAME, ID));
		}

		@Test
		void returnANewObject_onEachCall() {
			// Prepare
			when(manufacturerDboRepository.findById(ID)).thenReturn(Optional.of(manufacturerDbo));
			// Run & Check
			assertNotSame(unitUnderTest.createMedication(NAME, ID), unitUnderTest.createMedication(NAME, ID));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			MedicationDbo expected = new MedicationDbo().setId(ID).setManufacturer(manufacturerDbo).setName(NAME);
			when(manufacturerDboRepository.findById(ID)).thenReturn(Optional.of(manufacturerDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createMedication(NAME, ID));
		}

		@Test
		void throwsAnException_whenThereIsNoManufacturerForThePassedId() {
			// Prepare
			when(manufacturerDboRepository.findById(ID)).thenReturn(Optional.empty());
			// Run & Check
			assertThrows(NoSuchElementException.class, () -> unitUnderTest.createMedication(NAME, ID));
		}
	}

	@Nested
	class createLog_UUID_UUID_LocalDate_LocalTime_BigDecimal {

		@Test
		void throwsAnException_passingANullValue_asMedicationId() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createMedicationLog(null, ID, DATE, TIME, UNIT_COUNT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asMedicationUnitId() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createMedicationLog(ID, null, DATE, TIME, UNIT_COUNT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asDateOfIntake() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createMedicationLog(ID, ANOTHER_ID, null, TIME, UNIT_COUNT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asTimeOfIntake() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, null, UNIT_COUNT)
			);
		}

		@Test
		void throwsAnException_passingANullValue_asUnitCount() {
			assertThrows(
				IllegalArgumentException.class,
				() -> unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, null)
			);
		}

		@Test
		void returnANewObject() {
			// Prepare
			when(medicationDboRepository.findById(ID)).thenReturn(Optional.of(medicationDbo));
			when(medicationUnitDboRepository.findById(ANOTHER_ID)).thenReturn(Optional.of(medicationUnitDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertNotNull(unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT));
		}

		@Test
		void returnANewObject_onEachCall() {
			// Prepare
			when(medicationDboRepository.findById(ID)).thenReturn(Optional.of(medicationDbo));
			when(medicationUnitDboRepository.findById(ANOTHER_ID)).thenReturn(Optional.of(medicationUnitDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertNotSame(
				unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT),
				unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			MedicationLogDbo expected = new MedicationLogDbo()
				.setDateOfIntake(DATE)
				.setId(ID)
				.setMedication(medicationDbo)
				.setMedicationUnit(medicationUnitDbo)
				.setTimeOfIntake(TIME)
				.setUnitCount(UNIT_COUNT);
			when(medicationDboRepository.findById(ID)).thenReturn(Optional.of(medicationDbo));
			when(medicationUnitDboRepository.findById(ANOTHER_ID)).thenReturn(Optional.of(medicationUnitDbo));
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT));
		}

		@Test
		void throwsAnException_whenThereIsNoMedicationForThePassedId() {
			// Prepare
			when(medicationDboRepository.findById(ID)).thenReturn(Optional.empty());
			// Run & Check
			assertThrows(
				NoSuchElementException.class,
				() -> unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT)
			);
		}

		@Test
		void throwsAnException_whenThereIsNoMedicationUnitForThePassedId() {
			// Prepare
			when(medicationDboRepository.findById(ID)).thenReturn(Optional.of(medicationDbo));
			when(medicationUnitDboRepository.findById(ANOTHER_ID)).thenReturn(Optional.empty());
			// Run & Check
			assertThrows(
				NoSuchElementException.class,
				() -> unitUnderTest.createMedicationLog(ID, ANOTHER_ID, DATE, TIME, UNIT_COUNT)
			);
		}
	}

	@Nested
	class createMedicationUnit_String_String {

		@Test
		void throwsAnException_passingABlankString_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedicationUnit("\n\t\r ", TOKEN));
		}

		@Test
		void throwsAnException_passingABlankString_asToken() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedicationUnit(NAME, "\n\t\r "));
		}

		@Test
		void throwsAnException_passingANullValue_asName() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedicationUnit(null, TOKEN));
		}

		@Test
		void throwsAnException_passingANullValue_asToken() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createMedicationUnit(NAME, null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createMedicationUnit(NAME, TOKEN));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(unitUnderTest.createMedicationUnit(NAME, TOKEN), unitUnderTest.createMedicationUnit(NAME, TOKEN));
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			MedicationUnitDbo expected = new MedicationUnitDbo().setId(ID).setName(NAME).setToken(TOKEN);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createMedicationUnit(NAME, TOKEN));
		}
	}

	@Nested
	class createSymptom_String_LocalDate_LocalTime {

		@Test
		void throwsAnException_passingABlankString_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createSymptom("\n\t\r ", DATE_OF_RECORDING));
		}

		@Test
		void throwsAnException_passingANullValue_asContent() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createSymptom(null, DATE_OF_RECORDING));
		}

		@Test
		void throwsAnException_passingANullValue_asDateOfRecording() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.createSymptom(CONTENT, null));
		}

		@Test
		void returnANewObject() {
			assertNotNull(unitUnderTest.createSymptom(CONTENT, DATE_OF_RECORDING));
		}

		@Test
		void returnANewObject_onEachCall() {
			assertNotSame(
				unitUnderTest.createSymptom(CONTENT, DATE_OF_RECORDING),
				unitUnderTest.createSymptom(CONTENT, DATE_OF_RECORDING)
			);
		}

		@Test
		void returnANewObject_withCorrectlySetAttributes() {
			// Prepare
			SymptomDbo expected = new SymptomDbo()
				.setDateOfRecording(DATE_OF_RECORDING)
				.setDescription(DESCRIPTION)
				.setId(ID);
			when(uuidFactory.create()).thenReturn(ID);
			// Run & Check
			assertEquals(expected, unitUnderTest.createSymptom(DESCRIPTION, DATE_OF_RECORDING));
		}
	}
}
