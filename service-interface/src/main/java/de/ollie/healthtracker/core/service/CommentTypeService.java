package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.CommentType;
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
public interface CommentTypeService {
	CommentType createCommentType(String name);

	void deleteCommentType(UUID id);

	Optional<CommentType> findById(UUID id);

	Optional<CommentType> findByIdOrNameParticle(String namePartOrId);

	List<CommentType> listCommentTypes();

	CommentType updateCommentType(CommentType toSave);
}
