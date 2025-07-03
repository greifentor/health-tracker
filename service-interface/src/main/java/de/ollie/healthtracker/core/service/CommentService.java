package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Comment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CommentService {
	Comment createComment(String content, LocalDate dateOfRecording, LocalTime timeOfRecording);

	void deleteComment(UUID id);

	List<Comment> listComments();
}
