package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Comment;
import jakarta.inject.Named;
import java.time.LocalDate;
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
public interface CommentPersistencePort {
	Comment create(String content, LocalDate dateOfRecording);

	void deleteById(UUID id);

	Optional<Comment> findById(UUID id);

	Optional<Comment> findByIdOrContentParticle(String content);

	List<Comment> list();

	Comment update(Comment toSave);
}
