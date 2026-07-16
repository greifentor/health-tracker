package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholProductDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BodyTemperatureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ExerciseDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.GeneralBodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatCategoryDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatProductDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationPlanDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.PointOfMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.WeightMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.WhoBloodPressureClassificationDbo;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholProductDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.BodyPartDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.CommentTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorConsultationDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.GeneralBodyPartDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.ManufacturerDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MeatProductDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MeatTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.MedicationUnitDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.PointOfMeasurementDboRepository;
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

	private final AlcoholProductDboRepository alcoholProductDboRepository;
	private final BodyPartDboRepository bodyPartRepository;
	private final CommentTypeDboRepository commentTypeDboRepository;
	private final DoctorConsultationDboRepository doctorConsultationRepository;
	private final DoctorDboRepository doctorRepository;
	private final DoctorTypeDboRepository doctorTypeRepository;
	private final GeneralBodyPartDboRepository generalBodyPartRepository;
	private final ManufacturerDboRepository manufacturerDboRepository;
	private final MeatProductDboRepository meatProductDboRepository;
	private final MeatTypeDboRepository meatTypeDboRepository;
	private final MedicationDboRepository medicationDboRepository;
	private final MedicationUnitDboRepository medicationUnitDboRepository;
	private final PointOfMeasurementDboRepository pointOfMeasurementDboRepository;
	private final UuidFactory uuidFactory;

	AlcoholConsumptionDbo createAlcoholConsumption(
		LocalDate date,
		UUID alcoholProductId,
		String comment,
		BigDecimal liter
	) {
		ensure(alcoholProductId != null, "alcohol product id cannot be null!");
		ensure(comment != null, "comment cannot be null!");
		ensure(!comment.isBlank(), "comment cannot be blank!");
		ensure(date != null, "date cannot be null!");
		ensure(liter != null, "liter cannot be null!");
		AlcoholProductDbo alcoholProduct = alcoholProductDboRepository
			.findById(alcoholProductId)
			.orElseThrow(() -> new NoSuchElementException("no alcohol product found with id: " + alcoholProductId));
		return new AlcoholConsumptionDbo()
			.setAlcoholProduct(alcoholProduct)
			.setComment(comment)
			.setDate(date)
			.setId(uuidFactory.create())
			.setLiter(liter);
	}

	AlcoholProductDbo createAlcoholProduct(String name, BigDecimal percentVol) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		ensure(percentVol != null, "percent vol cannot be null!");
		return new AlcoholProductDbo().setId(uuidFactory.create()).setName(name).setPercentVol(percentVol);
	}

	BloodPressureMeasurementDbo createBloodPressureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		WhoBloodPressureClassification state,
		boolean irregularHeartbeat
	) {
		ensure(diaMmHg > 0, "DiaMmHg cannot be lesser then 1!");
		ensure(pulsePerMinute > 0, "PulsePerMinute cannot be lesser then 1!");
		ensure(state != null, "State cannot be null!");
		ensure(sysMmHg > 0, "SysMmHg cannot be lesser then 1!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		return new BloodPressureMeasurementDbo()
			.setComment(comment)
			.setDateOfRecording(dateOfRecording)
			.setDiaMmHg(diaMmHg)
			.setId(uuidFactory.create())
			.setIrregularHeartbeat(irregularHeartbeat)
			.setPulsePerMinute(pulsePerMinute)
			.setStatus(state == null ? null : WhoBloodPressureClassificationDbo.valueOf(state.name()))
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

	BodyTemperatureMeasurementDbo createBodyTemperatureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording,
		UUID pointOfMeasurementId
	) {
		ensure(celsius != null, "celsius cannot be null!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		PointOfMeasurementDbo pointOfMeasurement = pointOfMeasurementId == null
			? null
			: pointOfMeasurementDboRepository.findById(pointOfMeasurementId).orElse(null);
		return new BodyTemperatureMeasurementDbo()
			.setComment(comment)
			.setDateOfRecording(dateOfRecording)
			.setId(uuidFactory.create())
			.setCelsius(celsius)
			.setTimeOfRecording(timeOfRecording)
			.setPointOfMeasurement(pointOfMeasurement);
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
		String result,
		UUID doctorConsultationId
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
		DoctorConsultationDbo doctorConsultation = doctorConsultationId == null
			? null
			: doctorConsultationRepository.findById(doctorConsultationId).orElse(null);
		return new DoctorConsultationDbo()
			.setDate(date)
			.setDoctor(doctor)
			.setId(uuidFactory.create())
			.setOpen(open)
			.setReason(reason)
			.setResult(result)
			.setSubsequentAppointmentOf(doctorConsultation)
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

	MeatConsumptionDbo createMeatConsumption(LocalDate dateOfRecording, UUID meatProductId, BigDecimal units) {
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		MeatProductDbo meatProduct = meatProductDboRepository.findById(meatProductId).orElse(null);
		return new MeatConsumptionDbo()
			.setDateOfRecording(dateOfRecording)
			.setId(uuidFactory.create())
			.setMeatProduct(meatProduct)
			.setUnits(units);
	}

	MeatProductDbo createMeatProduct(int amoutInGr, String description, UUID meatTypeId) {
		ensure(description != null, "description cannot be null!");
		ensure(!description.isBlank(), "description cannot be blank!");
		MeatTypeDbo meatType = meatTypeDboRepository.findById(meatTypeId).orElse(null);
		return new MeatProductDbo()
			.setAmountInGr(amoutInGr)
			.setDescription(description)
			.setId(uuidFactory.create())
			.setMeatType(meatType);
	}

	MeatTypeDbo createMeatType(MeatCategory category, String name) {
		ensure(category != null, "category cannot be null!");
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new MeatTypeDbo()
			.setId(uuidFactory.create())
			.setCategory((category == null ? null : MeatCategoryDbo.valueOf(category.name())))
			.setName(name);
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
		String comment,
		boolean confirmed,
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
			.setComment(comment)
			.setConfirmed(confirmed)
			.setDateOfIntake(dateOfIntake)
			.setId(uuidFactory.create())
			.setMedication(medication)
			.setMedicationUnit(medicationUnit)
			.setSelfMedication(selfMedication)
			.setTimeOfIntake(timeOfIntake)
			.setUnitCount(unitCount);
	}

	MedicationPlanDbo createMedicationPlan(
		LocalDate endDate,
		UUID medicationId,
		UUID medicationUnitId,
		LocalDate nextDateOfIntake,
		boolean selfMedication,
		LocalDate startDate,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		ensure(nextDateOfIntake != null, "next date of intake cannot be null!");
		ensure(endDate != null, "end date cannot be null!");
		ensure(medicationId != null, "medication id cannot be null!");
		ensure(medicationUnitId != null, "medication unit id cannot be null!");
		ensure(startDate != null, "start date cannot be null!");
		ensure(timeOfIntake != null, "time of intake cannot be null!");
		ensure(unitCount != null, "unit count cannot be null!");
		MedicationDbo medication = medicationDboRepository
			.findById(medicationId)
			.orElseThrow(() -> new NoSuchElementException("no medication found with id: " + medicationId));
		MedicationUnitDbo medicationUnit = medicationUnitDboRepository
			.findById(medicationUnitId)
			.orElseThrow(() -> new NoSuchElementException("no medication unit found with id: " + medicationUnitId));
		return new MedicationPlanDbo()
			.setId(uuidFactory.create())
			.setMedication(medication)
			.setMedicationUnit(medicationUnit)
			.setNextDateOfIntake(nextDateOfIntake)
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

	PointOfMeasurementDbo createPointOfMeasurement(
		String name,
		BigDecimal regularMaxCelsius,
		BigDecimal regularMinCelsius
	) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new PointOfMeasurementDbo()
			.setId(uuidFactory.create())
			.setName(name)
			.setRegularMaxCelsius(regularMaxCelsius)
			.setRegularMinCelsius(regularMinCelsius);
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

	WeightMeasurementDbo createWeightMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal kg,
		LocalTime timeOfRecording
	) {
		ensure(kg != null, "kg cannot be null!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		return new WeightMeasurementDbo()
			.setComment(comment)
			.setDateOfRecording(dateOfRecording)
			.setId(uuidFactory.create())
			.setKg(kg)
			.setTimeOfRecording(timeOfRecording);
	}
}
