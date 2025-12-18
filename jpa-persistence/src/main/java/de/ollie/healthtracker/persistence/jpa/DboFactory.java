package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ExerciseDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.GeneralBodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import de.ollie.healthtracker.persistence.jpa.repository.BodyPartDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.CommentTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.GeneralBodyPartDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.ManufacturerDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MeatTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationUnitDboRepository;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DboFactory {

	private final BodyPartDboRepository bodyPartRepository;
	private final CommentTypeDboRepository commentTypeDboRepository;
	private final DoctorDboRepository doctorRepository;
	private final DoctorTypeDboRepository doctorTypeRepository;
	private final GeneralBodyPartDboRepository generalBodyPartRepository;
	private final ManufacturerDboRepository manufacturerDboRepository;
	private final MeatTypeDboRepository meatTypeDboRepository;
	private final MedicationDboRepository medicationDboRepository;
	private final MedicationUnitDboRepository medicationUnitDboRepository;
	private final UuidFactory uuidFactory;

	BloodPressureMeasurementDbo createBloodPressureMeasurement(
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus state,
		boolean irregularHeartbeat
	) {
		ensure(diaMmHg > 0, "DiaMmHg cannot be lesser then 1!");
		ensure(pulsePerMinute > 0, "PulsePerMinute cannot be lesser then 1!");
		ensure(state != null, "State cannot be null!");
		ensure(sysMmHg > 0, "SysMmHg cannot be lesser then 1!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		return new BloodPressureMeasurementDbo()
			.setDateOfRecording(dateOfRecording)
			.setDiaMmHg(diaMmHg)
			.setId(uuidFactory.create())
			.setIrregularHeartbeat(irregularHeartbeat)
			.setPulsePerMinute(pulsePerMinute)
			.setStatus(state == null ? null : BloodPressureMeasurementStatusDbo.valueOf(state.name()))
			.setSysMmHg(sysMmHg)
			.setTimeOfRecording(timeOfRecording);
	}

	BodyPartDbo createBodyPart(UUID generalBodyPartId, String name) {
		ensure(generalBodyPartId != null, "general body part id cannot be null!");
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		GeneralBodyPartDbo generalBodyPart = generalBodyPartRepository
			.findById(generalBodyPartId)
			.orElseThrow(() -> new NoSuchElementException("no general body part found with id: " + generalBodyPartId));
		return new BodyPartDbo().setId(uuidFactory.create()).setGeneralBodyPart(generalBodyPart).setName(name);
	}

	CommentDbo createComment(UUID commentTypeId, String content, LocalDate dateOfRecording) {
		ensure(commentTypeId != null, "comment type id cannot be null!");
		ensure(content != null, "content cannot be null!");
		ensure(!content.isBlank(), "content cannot be blank!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		CommentTypeDbo commentTypeDbo = commentTypeDboRepository
			.findById(commentTypeId)
			.orElseThrow(() -> new NoSuchElementException("no general body part found with id: " + commentTypeId));
		return new CommentDbo()
			.setCommentType(commentTypeDbo)
			.setContent(content)
			.setDateOfRecording(dateOfRecording)
			.setId(uuidFactory.create());
	}

	CommentTypeDbo createCommentType(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new CommentTypeDbo().setName(name).setId(uuidFactory.create());
	}

	DoctorDbo createDoctor(String name, UUID doctorTypeId) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		ensure(doctorTypeId != null, "doctor type id cannot be null!");
		DoctorTypeDbo doctorType = doctorTypeRepository
			.findById(doctorTypeId)
			.orElseThrow(() -> new NoSuchElementException("no doctor type found with id: " + doctorTypeId));
		return new DoctorDbo().setDoctorType(doctorType).setId(uuidFactory.create()).setName(name);
	}

	DoctorConsultationDbo createDoctorConsultation(
		LocalDate date,
		LocalTime time,
		UUID doctorId,
		boolean open,
		String reason,
		String result
	) {
		ensure(date != null, "date cannot be null!");
		ensure(doctorId != null, "doctor id cannot be null!");
		ensure(reason != null, "reason cannot be null!");
		ensure(!reason.isBlank(), "reason cannot be blank!");
		ensure(result != null, "result cannot be null!");
		ensure(!result.isBlank(), "result cannot be blank!");
		ensure(time != null, "time cannot be null!");
		DoctorDbo doctor = doctorRepository
			.findById(doctorId)
			.orElseThrow(() -> new NoSuchElementException("no doctor found with id: " + doctorId));
		return new DoctorConsultationDbo()
			.setDate(date)
			.setDoctor(doctor)
			.setId(uuidFactory.create())
			.setOpen(open)
			.setReason(reason)
			.setResult(result)
			.setTime(time);
	}

	DoctorTypeDbo createDoctorType(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new DoctorTypeDbo().setId(uuidFactory.create()).setName(name);
	}

	ExerciseDbo createExercise(UUID bodyPartId, String name, String description) {
		ensure(bodyPartId != null, "body part id cannot be null!");
		ensure(description != null, "description cannot be null!");
		ensure(!description.isBlank(), "description cannot be blank!");
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		BodyPartDbo bodyPart = bodyPartRepository
			.findById(bodyPartId)
			.orElseThrow(() -> new NoSuchElementException("no body part found with id: " + bodyPartId));
		return new ExerciseDbo()
			.setId(uuidFactory.create())
			.setBodyPart(bodyPart)
			.setDescription(description)
			.setName(name);
	}

	GeneralBodyPartDbo createGeneralBodyPart(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new GeneralBodyPartDbo().setId(uuidFactory.create()).setName(name);
	}

	ManufacturerDbo createManufacturer(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new ManufacturerDbo().setId(uuidFactory.create()).setName(name);
	}

	MeatConsumptionDbo createMeatConsumption(
		int amoutInGr,
		LocalDate dateOfRecording,
		String description,
		UUID meatTypeId
	) {
		ensure(description != null, "description cannot be null!");
		ensure(!description.isBlank(), "description cannot be blank!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		MeatTypeDbo meatType = meatTypeDboRepository.findById(meatTypeId).orElse(null);
		return new MeatConsumptionDbo()
			.setAmountInGr(amoutInGr)
			.setDateOfRecording(dateOfRecording)
			.setDescription(description)
			.setId(uuidFactory.create())
			.setMeatType(meatType);
	}

	MeatTypeDbo createMeatType(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new MeatTypeDbo().setId(uuidFactory.create()).setName(name);
	}

	MedicationDbo createMedication(String name, UUID manufacturerId) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		ensure(manufacturerId != null, "manufacturer id cannot be null!");
		ManufacturerDbo manufacturer = manufacturerDboRepository
			.findById(manufacturerId)
			.orElseThrow(() -> new NoSuchElementException("no manufacturer found with id: " + manufacturerId));
		return new MedicationDbo().setId(uuidFactory.create()).setManufacturer(manufacturer).setName(name);
	}

	MedicationLogDbo createMedicationLog(
		UUID medicationId,
		UUID medicationUnitId,
		LocalDate dateOfIntake,
		boolean selfMedication,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		ensure(dateOfIntake != null, "date of intake cannot be null!");
		ensure(medicationId != null, "medication id cannot be null!");
		ensure(medicationUnitId != null, "medication unit id cannot be null!");
		ensure(timeOfIntake != null, "time of intake cannot be null!");
		ensure(unitCount != null, "unit count cannot be null!");
		MedicationDbo medication = medicationDboRepository
			.findById(medicationId)
			.orElseThrow(() -> new NoSuchElementException("no medication found with id: " + medicationId));
		MedicationUnitDbo medicationUnit = medicationUnitDboRepository
			.findById(medicationUnitId)
			.orElseThrow(() -> new NoSuchElementException("no medication unit found with id: " + medicationUnitId));
		return new MedicationLogDbo()
			.setDateOfIntake(dateOfIntake)
			.setId(uuidFactory.create())
			.setMedication(medication)
			.setMedicationUnit(medicationUnit)
			.setSelfMedication(selfMedication)
			.setTimeOfIntake(timeOfIntake)
			.setUnitCount(unitCount);
	}

	MedicationUnitDbo createMedicationUnit(String name, String token) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		ensure(token != null, "doctor type id cannot be null!");
		ensure(!token.isBlank(), "token cannot be blank!");
		return new MedicationUnitDbo().setId(uuidFactory.create()).setName(name).setToken(token);
	}

	SymptomDbo createSymptom(String description, LocalDate dateOfRecording, UUID bodyPartId) {
		ensure(bodyPartId != null, "body part id cannot be null!");
		ensure(description != null, "description cannot be null!");
		ensure(!description.isBlank(), "description cannot be blank!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		BodyPartDbo bodyPart = bodyPartRepository
			.findById(bodyPartId)
			.orElseThrow(() -> new NoSuchElementException("no body part found with id: " + bodyPartId));
		return new SymptomDbo()
			.setBodyPart(bodyPart)
			.setDateOfRecording(dateOfRecording)
			.setDescription(description)
			.setId(uuidFactory.create());
	}
}
