package de.ollie.healthtracker.shell.adapter.print;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.HealthTrackingReportToStringMapper;
import jakarta.inject.Named;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ConsolePrintAdapter implements PrintPort {

	static final String DESCRIPTION = "PrintPort implementation for console output.";
	static final String ID = "CON";

	private final HealthTrackingReportToStringMapper healthTrackingReportToStringMapper;
	private final OutputHandler outputHandler;

	@Override
	public Details getDetails() {
		return new Details(ID, DESCRIPTION);
	}

	@Override
	public byte[] print(HealthTrackingReport report, Map<String, Object> parameters) {
		outputHandler.println(healthTrackingReportToStringMapper.toString(report));
		return new byte[0];
	}
}
