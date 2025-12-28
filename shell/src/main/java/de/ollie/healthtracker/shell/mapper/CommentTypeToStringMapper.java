package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.CommentType;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface CommentTypeToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(CommentType model);
}
