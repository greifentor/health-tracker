package de.ollie.healthtracker.shell.mapper.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.shell.mapper.HealthTrackingReportToStringMapper;
import jakarta.inject.Named;

@Named
public class HealthTrackingReportToStringMapperImpl implements HealthTrackingReportToStringMapper {

	static final String FROM = "From: ";
	static final String TO = "To: ";

	@Override
	public String toString(HealthTrackingReport report) {
		ensure(report != null, "report cannot be null!");
		return FROM + dateToString(report.getFrom()) + "\n" + TO + dateToString(report.getTo());
	}
}
