package de.ollie.healthtracker.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class CommentServiceImpl implements CommentService {

	private final CommentPersistencePort commentPersistencePort;

	@Override
	public Comment createComment(CommentType commentType, String content, LocalDate dateOfRecording) {
		return commentPersistencePort.create(commentType, content, dateOfRecording);
	}

	@Override
	public void deleteComment(UUID id) {
		commentPersistencePort.deleteById(id);
	}

	@Override
	public Optional<Comment> findById(UUID id) {
		return commentPersistencePort.findById(id);
	}

	@Override
	public Optional<Comment> findByIdOrContentParticle(String namePartOrId) {
		return commentPersistencePort.findByIdOrContentParticle(namePartOrId);
	}

	@Override
	public List<Comment> listComments() {
		return commentPersistencePort.list();
	}

	@Override
	public List<Comment> listCommentsBetweenDatesOrderedByDateAndContentTypeName(LocalDate from, LocalDate to) {
		ensure(from != null, "from cannot be null!");
		ensure(to != null, "to cannot be null!");
		ensure(from.isBefore(to) || from.equals(to), "to cannot be before from!");
		return commentPersistencePort.listBetweenDatesOrderedByDateAndContentTypeName(from, to);
	}

	@Override
	public Comment updateComment(Comment toSave) {
		return commentPersistencePort.update(toSave);
	}
}
