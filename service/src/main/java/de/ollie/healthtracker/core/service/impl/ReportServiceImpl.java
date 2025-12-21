package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.model.report.DataPerDay;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ReportServiceImpl implements ReportService {

	private final CommentService commentService;

	@Override
	public HealthTrackingReport collectData(LocalDate from, LocalDate to) {
		List<Comment> comments = commentService.listCommentsBetweenDatesOrderedByDateAndCommentTypeName(from, to);
		Map<LocalDate, Map<CommentType, List<Comment>>> commentMap = mapComments(comments);
		List<DataPerDay> dataPerDays = new ArrayList<>();
		for (LocalDate currentDay = from; !currentDay.isAfter(to); currentDay = currentDay.plusDays(1)) {
			System.out.println(currentDay);
			DataPerDay dataPerDay = new DataPerDay().setDate(currentDay).setComments(commentMap.get(currentDay));
			dataPerDays.add(dataPerDay);
		}
		// TODO Auto-generated method stub
		return new HealthTrackingReport().setDataPerDayOrderedByDate(dataPerDays).setFrom(from).setTo(to);
	}

	private Map<LocalDate, Map<CommentType, List<Comment>>> mapComments(List<Comment> comments) {
		return new HashMap<>();
	}
}
