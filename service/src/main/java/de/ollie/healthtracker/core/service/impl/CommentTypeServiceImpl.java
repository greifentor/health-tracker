package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.port.persistence.CommentTypePersistencePort;
import jakarta.inject.Named;
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
class CommentTypeServiceImpl implements CommentTypeService {

	private final CommentTypePersistencePort commentTypePersistencePort;

	@Override
	public CommentType createCommentType(String name) {
		return commentTypePersistencePort.create(name);
	}

	@Override
	public void deleteCommentType(UUID id) {
		commentTypePersistencePort.deleteById(id);
	}

	@Override
	public Optional<CommentType> findById(UUID id) {
		return commentTypePersistencePort.findById(id);
	}

	@Override
	public Optional<CommentType> findByIdOrNameParticle(String namePartOrId) {
		return commentTypePersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<CommentType> listCommentTypes() {
		return commentTypePersistencePort.list();
	}

	@Override
	public CommentType updateCommentType(CommentType toSave) {
		return commentTypePersistencePort.update(toSave);
	}
}
