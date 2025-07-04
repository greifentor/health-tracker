package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentPersistencePort commentPersistencePort;

	@Override
	public Comment createComment(String content, LocalDate dateOfRecording, LocalTime timeOfRecording) {
		return commentPersistencePort.create(content, dateOfRecording, timeOfRecording);
	}

	@Override
	public void deleteComment(UUID id) {
		commentPersistencePort.deleteById(id);
	}

	@Override
	public List<Comment> listComments() {
		return commentPersistencePort.list();
	}
}
