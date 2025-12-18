package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.port.persistence.DoctorConsultationPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class DoctorConsultationServiceImpl implements DoctorConsultationService {

	private final DoctorConsultationPersistencePort doctorConsultationPersistencePort;

	@Override
	public DoctorConsultation createDoctorConsultation(
		LocalDate date,
		LocalTime time,
		Doctor doctor,
		boolean open,
		String reason,
		String result
	) {
		return doctorConsultationPersistencePort.create(date, time, doctor, open, reason, result);
	}

	@Override
	public void deleteDoctorConsultation(UUID id) {
		doctorConsultationPersistencePort.deleteById(id);
	}

	@Override
	public Optional<DoctorConsultation> findById(UUID id) {
		return doctorConsultationPersistencePort.findById(id);
	}

	@Override
	public List<DoctorConsultation> listDoctorConsultations() {
		return doctorConsultationPersistencePort.list();
	}

	@Override
	public DoctorConsultation updateDoctorConsultation(DoctorConsultation toSave) {
		return doctorConsultationPersistencePort.update(toSave);
	}
}
