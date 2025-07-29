package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import jakarta.inject.Named;
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
@Named
public interface DoctorPersistencePort {
	Doctor create(String name, DoctorType doctorType);

	void deleteById(UUID id);

	Optional<Doctor> findByIdOrNameParticle(String name);

	List<Doctor> list();
}
