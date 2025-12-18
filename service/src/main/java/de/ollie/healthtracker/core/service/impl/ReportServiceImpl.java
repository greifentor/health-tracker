package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.report.DataPerDay;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ReportServiceImpl implements ReportService {

	private final CommentService commentService;

	@Override
	public HealthTrackingReport collectData(LocalDate from, LocalDate to) {
		List<Comment> comments = commentService.listCommentsBetweenDatesOrderedByDateAndContent(from, to);
		List<DataPerDay> dataPerDays = comments
			.stream()
			.map(c -> new DataPerDay().setDate(c.getDateOfRecording()).setComment(c))
			.toList();
		// TODO Auto-generated method stub
		return new HealthTrackingReport().setDataPerDayOrderedByDate(dataPerDays).setFrom(from).setTo(to);
	}
}
