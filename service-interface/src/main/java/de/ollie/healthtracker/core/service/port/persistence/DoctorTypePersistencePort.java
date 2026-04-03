package de.ollie.healthtracker.core.service.port.persistence;

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
public interface DoctorTypePersistencePort {
	DoctorType create(String name);

	void deleteById(UUID id);

	Optional<DoctorType> findById(UUID id);

	Optional<DoctorType> findByIdOrNameParticle(String name);

	List<DoctorType> list();

	DoctorType update(DoctorType toSave);
}
