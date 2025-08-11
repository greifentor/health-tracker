package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.ReportService;
import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import jakarta.inject.Named;
import java.time.LocalDate;

@Named
class ReportServiceImpl implements ReportService {

	@Override
	public HealthTrackingReport collectData(LocalDate from, LocalDate to) {
		// TODO Auto-generated method stub
		return new HealthTrackingReport(from, to);
	}
}
