package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.shell.mapper.CommentToStringMapper;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Named
class CommentToStringMapperImpl implements CommentToStringMapper {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
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
				comment.getContent()
			);
	}

	private String dateToString(LocalDate localDate) {
		return localDate == null ? "-" : DATE_FORMATTER.format(localDate);
	}

	private String timeToString(LocalTime localTime) {
		return localTime == null ? "-" : TIME_FORMATTER.format(localTime);
	}
}
