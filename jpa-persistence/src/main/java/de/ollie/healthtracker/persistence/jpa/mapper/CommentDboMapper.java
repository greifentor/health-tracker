package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface CommentDboMapper {
	Comment toModel(CommentDbo dbo);
	CommentDbo toDbo(Comment model);
}
