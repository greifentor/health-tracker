package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.port.persistence.CommentPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.CommentDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.CommentDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class CommentPersistenceJpaAdapter implements CommentPersistencePort {

	private final DboFactory dboFactory;
	private final CommentDboMapper mapper;
	private final CommentDboRepository repository;

	@Override
	public Comment create(CommentType commentType, String content, LocalDate dateOfRecording) {
		return mapper.toModel(repository.save(dboFactory.createComment(commentType.getId(), content, dateOfRecording)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<Comment> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<Comment> findByIdOrContentParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<CommentDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<CommentDbo> found = repository.findAllByContentMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<Comment> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public List<Comment> listBetweenDatesOrderedByDateAndCommentTypeName(LocalDate from, LocalDate to) {
		return repository
			.findAllBetweenFromAndToOrderByDateOfRecordingAndCommentTypeName(from, to)
			.stream()
			.map(mapper::toModel)
			.toList();
	}

	@Override
	public Comment update(Comment toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
