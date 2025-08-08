package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import java.time.LocalDate;

public interface ReportService {
	HealthTrackingReport collectData(LocalDate from, LocalDate to);
}
