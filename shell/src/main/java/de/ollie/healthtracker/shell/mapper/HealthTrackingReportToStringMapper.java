package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;

public interface HealthTrackingReportToStringMapper extends ToStringMapper {
	String toString(HealthTrackingReport report);
}
