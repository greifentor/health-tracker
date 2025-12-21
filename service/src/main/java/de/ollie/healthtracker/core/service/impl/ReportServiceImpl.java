package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.Comment;
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
		Map<LocalDate, List<Comment>> commentMap = mapComments(comments);
		List<DataPerDay> dataPerDays = new ArrayList<>();
		for (LocalDate currentDay = from; !currentDay.isAfter(to); currentDay = currentDay.plusDays(1)) {
			DataPerDay dataPerDay = new DataPerDay()
				.setDate(currentDay)
				.setComments(commentMap.getOrDefault(currentDay, new ArrayList<>()));
			dataPerDays.add(dataPerDay);
		}
		// TODO Auto-generated method stub
		return new HealthTrackingReport().setDataPerDayOrderedByDate(dataPerDays).setFrom(from).setTo(to);
	}

	private Map<LocalDate, List<Comment>> mapComments(List<Comment> comments) {
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
