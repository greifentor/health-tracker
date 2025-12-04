package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentTypeDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface CommentTypeDboMapper {
	CommentType toModel(CommentTypeDbo dbo);

	CommentTypeDbo toDbo(CommentType model);
}
