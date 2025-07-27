package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.DoctorType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

@Generated
public interface DoctorTypeService {
	DoctorType createDoctorType(String name);

	void deleteDoctorType(UUID id);

	Optional<DoctorType> findByIdOrNameParticle(String namePartOrId);

	List<DoctorType> listDoctorTypes();
}
