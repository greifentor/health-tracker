package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.report.DataPerDay;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;

@Named
class ReportServiceImpl implements ReportService {

	@Override
	public HealthTrackingReport collectData(LocalDate from, LocalDate to) {
		// TODO Auto-generated method stub
		return new HealthTrackingReport()
			.setDataPerDayOrderedByDate(
				List.of(
					new DataPerDay().setDate(LocalDate.of(2025, 12, 1)),
					new DataPerDay().setDate(LocalDate.of(2025, 12, 3))
				)
			)
			.setFrom(LocalDate.of(2025, 12, 1))
			.setTo(LocalDate.of(2025, 12, 31));
	}
}
