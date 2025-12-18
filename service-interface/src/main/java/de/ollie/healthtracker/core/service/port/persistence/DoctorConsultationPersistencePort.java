package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
public interface DoctorConsultationPersistencePort {
	DoctorConsultation create(LocalDate date, LocalTime time, Doctor doctor, boolean open, String reason, String result);

	void deleteById(UUID id);

	Optional<DoctorConsultation> findById(UUID id);

	List<DoctorConsultation> list();

	DoctorConsultation update(DoctorConsultation toSave);
}
