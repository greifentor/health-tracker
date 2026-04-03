package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.CommentType;
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
public interface CommentTypePersistencePort {
	CommentType create(String name);

	void deleteById(UUID id);

	Optional<CommentType> findById(UUID id);

	Optional<CommentType> findByIdOrNameParticle(String name);

	List<CommentType> list();

	CommentType update(CommentType toSave);
}
