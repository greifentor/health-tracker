package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
	Comment createComment(CommentType commentType, String content, LocalDate dateOfRecording);

	void deleteComment(UUID id);

	Optional<Comment> findById(UUID id);

	Optional<Comment> findByIdOrContentParticle(String namePartOrId);

	List<Comment> listComments();

	List<Comment> listCommentsBetweenDatesOrderedByDateAndCommentTypeName(LocalDate from, LocalDate to);

	Comment updateComment(Comment toSave);
}
