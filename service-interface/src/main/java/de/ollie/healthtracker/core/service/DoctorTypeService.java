package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.DoctorType;
import java.util.List;
import java.util.Optional;
import lombok.Generated;

import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface DoctorTypeService {

	DoctorType createDoctorType(String name);

	void deleteDoctorType(UUID id);
	
	Optional<DoctorType> findById(UUID id);

	Optional<DoctorType> findByIdOrNameParticle(String namePartOrId);

	List<DoctorType> listDoctorTypes();

	DoctorType updateDoctorType(DoctorType toSave);
}
