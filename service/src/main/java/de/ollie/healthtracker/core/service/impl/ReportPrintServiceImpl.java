package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.ReportPrintService;
import de.ollie.healthtracker.core.service.port.print.PrintPort;
import de.ollie.healthtracker.core.service.port.print.PrintPort.Details;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class ReportPrintServiceImpl implements ReportPrintService {

	private final List<PrintPort> printPortList;

	private final Map<String, PrintPort> printPorts = new HashMap<>();

	@PostConstruct
	void postConstruct() {
		printPortList.forEach(p -> printPorts.put(p.getDetails().getId(), p));
	}

	@Override
	public List<Details> getDetails() {
		return printPorts
			.entrySet()
			.stream()
			.map(Entry::getValue)
			.map(PrintPort::getDetails)
			.sorted(this::compareTo)
			.toList();
	}

	private int compareTo(Details dt0, Details dt1) {
		return dt0.getId().compareTo(dt1.getId());
	}

	@Override
	public void printForTimeInterval(LocalDate from, LocalDate to, Map<String, String> parameters) {
		// TODO Auto-generated method stub
	}
}
