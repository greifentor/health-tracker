package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.CommentDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.CommentDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class CommentPersistenceJpaAdapter implements CommentPersistencePort {

	private final DboFactory dboFactory;
	private final CommentDboMapper mapper;
	private final CommentDboRepository repository;

	@Override
	public Comment create(String content, LocalDate dateOfRecording, LocalTime timeOfRecording) {
		return mapper.toModel(repository.save(dboFactory.createComment(content, dateOfRecording, timeOfRecording)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public List<Comment> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
