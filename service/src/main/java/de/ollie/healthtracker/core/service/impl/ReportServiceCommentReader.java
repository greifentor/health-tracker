package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ReportServiceCommentReader {

	private final CommentService commentService;

	Map<LocalDate, List<Comment>> readCommentsForReport(LocalDate from, LocalDate to) {
		List<Comment> comments = commentService.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(from, to);
		Map<LocalDate, List<Comment>> m = new HashMap<>();
		for (Comment c : comments) {
			List<Comment> lc = m.get(c.getDateOfRecording());
			if (lc == null) {
				lc = new ArrayList<>();
				m.put(c.getDateOfRecording(), lc);
			}
			lc.add(c);
		}
		return m;
	}
}
