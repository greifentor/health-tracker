package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorTypePersistencePort;
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
public class DoctorTypeServiceImpl implements DoctorTypeService {

	private final DoctorTypePersistencePort DoctorTypePersistencePort;

	@Override
	public DoctorType createDoctorType(String name) {
		return DoctorTypePersistencePort.create(name);
	}

	@Override
	public void deleteDoctorType(UUID id) {
		DoctorTypePersistencePort.deleteById(id);
	}

	@Override
	public List<DoctorType> listDoctorTypes() {
		return DoctorTypePersistencePort.list();
	}
}
