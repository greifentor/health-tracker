package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import de.ollie.healthtracker.persistence.jpa.repository.DoctorTypeDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DboFactory {

	private final DoctorTypeDboRepository doctorTypeRepository;
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
		ensure(name != null, "name be null!");
		ensure(!name.isBlank(), "name be blank!");
		ensure(doctorTypeId != null, "doctor type id be null!");
		DoctorTypeDbo doctorType = doctorTypeRepository
			.findById(doctorTypeId)
			.orElseThrow(() -> new NoSuchElementException("no doctor type found with id: " + doctorTypeId));
		return new DoctorDbo().setDoctorType(doctorType).setId(uuidFactory.create()).setName(name);
	}

	DoctorTypeDbo createDoctorType(String name) {
		ensure(name != null, "name be null!");
		ensure(!name.isBlank(), "name be blank!");
		return new DoctorTypeDbo().setId(uuidFactory.create()).setName(name);
	}
}
