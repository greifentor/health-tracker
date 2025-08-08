package de.ollie.healthtracker.shell.adapter.print;

import de.ollie.healthtracker.core.service.model.report.HealthTrackingReport;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import jakarta.inject.Named;
import java.util.Map;

@Named
class ConsolePrintAdapter implements PrintPort {

	static final String DESCRIPTION = "PrintPort implementation for console output.";
	static final String ID = "CON";

	@Override
	public Details getDetails() {
		return new Details(ID, DESCRIPTION);
	}

	@Override
	public void print(HealthTrackingReport report, Map<String, String> parameters) {
		// TODO Auto-generated method stub

	}
}
