package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.shell.mapper.CommentToStringMapper;
import jakarta.inject.Named;

@Named
class CommentToStringMapperImpl implements CommentToStringMapper {

	private static final String LINE_FORMAT = "%10s %5s (%36s) %s";

	@Override
	public String getHeadLine() {
		return "Date       Time  (ID)                                   Content";
	}

	@Override
	public String getUnderLine() {
		return "----------------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(Comment comment) {
		return comment == null
			? null
			: String.format(
				LINE_FORMAT,
				dateToString(comment.getDateOfRecording()),
				timeToString(comment.getTimeOfRecording()),
				comment.getId(),
				(comment.getContent() == null ? "-" : comment.getContent())
			);
	}
}
