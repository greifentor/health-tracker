package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.Comment;

public interface CommentToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(Comment comment);
}
