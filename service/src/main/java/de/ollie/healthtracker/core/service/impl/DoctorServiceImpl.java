package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Named
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorPersistencePort DoctorPersistencePort;

	@Override
	public Doctor createDoctor(String name, DoctorType doctorType) {
		return DoctorPersistencePort.create(name, doctorType);
	}

	@Override
	public void deleteDoctor(UUID id) {
		DoctorPersistencePort.deleteById(id);
	}

	@Override
	public List<Doctor> listDoctors() {
		return DoctorPersistencePort.list();
	}
}
