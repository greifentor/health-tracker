package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.port.print.PrintPort;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportPrintService {
	List<PrintPort.Details> getDetails();

	void printForTimeInterval(LocalDate from, LocalDate to, Map<String, String> parameters);
}
