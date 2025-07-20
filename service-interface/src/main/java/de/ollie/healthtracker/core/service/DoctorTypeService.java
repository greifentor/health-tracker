package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.DoctorType;
import java.util.List;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface DoctorTypeService {
	DoctorType createDoctorType(String name);

	void deleteDoctorType(UUID id);

	List<DoctorType> listDoctorTypes();
}
