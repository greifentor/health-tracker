package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorPersistencePort;
import jakarta.inject.Named;
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
class DoctorServiceImpl implements DoctorService {

	private final DoctorPersistencePort doctorPersistencePort;

	@Override
	public Doctor createDoctor(String name, DoctorType doctorType) {
		return doctorPersistencePort.create(name, doctorType);
	}

	@Override
	public void deleteDoctor(UUID id) {
		doctorPersistencePort.deleteById(id);
	}

	@Override
	public Optional<Doctor> findByIdOrNameParticle(String namePartOrId) {
		return doctorPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<Doctor> listDoctors() {
		return doctorPersistencePort.list();
	}
}
