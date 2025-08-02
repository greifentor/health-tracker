package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Comment;
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
public interface CommentService {
	Comment createComment(String content, LocalDate dateOfRecording);

	void deleteComment(UUID id);

	Optional<Comment> findByIdOrContentParticle(String namePartOrId);

	List<Comment> listComments();
}
