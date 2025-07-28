package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import de.ollie.healthtracker.persistence.jpa.repository.ManufacturerDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DboFactory {

	private final DoctorDboRepository doctorRepository;
	private final DoctorTypeDboRepository doctorTypeRepository;
	private final ManufacturerDboRepository manufacturerDboRepository;
	private final UuidFactory uuidFactory;

	BloodPressureMeasurementDbo createBloodPressureMeasurement(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatusDbo state,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
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
			.setPulsePerMinute(pulsePerMinute)
			.setStatus(state)
			.setSysMmHg(sysMmHg)
			.setTimeOfRecording(timeOfRecording);
	}

	CommentDbo createComment(String content, LocalDate dateOfRecording, LocalTime timeOfRecording) {
		ensure(content != null, "content cannot be null!");
		ensure(!content.isBlank(), "content cannot be blank!");
		ensure(dateOfRecording != null, "date of recording cannot be null!");
		ensure(timeOfRecording != null, "time of recording cannot be null!");
		return new CommentDbo()
			.setContent(content)
			.setDateOfRecording(dateOfRecording)
			.setId(uuidFactory.create())
			.setTimeOfRecording(timeOfRecording);
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
			.setReason(reason)
			.setResult(result)
			.setTime(time);
	}

	DoctorTypeDbo createDoctorType(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new DoctorTypeDbo().setId(uuidFactory.create()).setName(name);
	}

	ManufacturerDbo createManufacturer(String name) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		return new ManufacturerDbo().setId(uuidFactory.create()).setName(name);
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

	MedicationUnitDbo createMedicationUnit(String name, String token) {
		ensure(name != null, "name cannot be null!");
		ensure(!name.isBlank(), "name cannot be blank!");
		ensure(token != null, "doctor type id cannot be null!");
		ensure(!token.isBlank(), "token cannot be blank!");
		return new MedicationUnitDbo().setId(uuidFactory.create()).setName(name).setToken(token);
	}
}
