package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Comment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CommentPersistencePort {
	Comment create(String content, LocalDate dateOfRecording, LocalTime timeOfRecording);

	void deleteById(UUID id);

	List<Comment> list();
}
