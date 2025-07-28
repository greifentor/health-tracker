package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface DoctorConsultationService {
	DoctorConsultation createDoctorConsultation(
		LocalDate date,
		LocalTime time,
		Doctor doctor,
		String reason,
		String result
	);

	void deleteDoctorConsultation(UUID id);

	List<DoctorConsultation> listDoctorConsultations();
}
