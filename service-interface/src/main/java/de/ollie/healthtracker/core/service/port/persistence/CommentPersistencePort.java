package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Named
public interface CommentPersistencePort {
	Comment create(CommentType commentType, String content, LocalDate dateOfRecording);

	void deleteById(UUID id);

	Optional<Comment> findById(UUID id);

	Optional<Comment> findByIdOrContentParticle(String content);

	List<Comment> list();

	List<Comment> listBetweenDatesOrderedByDateAndContentTypeName(LocalDate from, LocalDate to);

	Comment update(Comment toSave);
}
