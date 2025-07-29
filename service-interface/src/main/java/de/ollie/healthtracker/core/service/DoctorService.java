package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
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
public interface DoctorService {
	Doctor createDoctor(String name, DoctorType doctorType);

	void deleteDoctor(UUID id);

	Optional<Doctor> findByIdOrNameParticle(String namePartOrId);

	List<Doctor> listDoctors();
}
