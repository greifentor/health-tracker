package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.core.service.port.persistence.DoctorTypePersistencePort;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DoctorTypeServiceImpl implements DoctorTypeService {

	private final DoctorTypePersistencePort doctorTypePersistencePort;

	@Override
	public DoctorType createDoctorType(String name) {
		return doctorTypePersistencePort.create(name);
	}

	@Override
	public void deleteDoctorType(UUID id) {
		doctorTypePersistencePort.deleteById(id);
	}

	@Override
	public Optional<DoctorType> findByIdOrNameParticle(String namePartOrId) {
		return doctorTypePersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<DoctorType> listDoctorTypes() {
		return doctorTypePersistencePort.list();
	}
}
