package de.ollie.healthtracker.shell;

import de.ollie.healthtracker.core.service.ReportPrintService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DetailsToStringMapper;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Generated // OLI: Is a POC
@Named
@RequiredArgsConstructor
class StartProcedureRunner {

	private final DetailsToStringMapper mapper;
	private final OutputHandler outPutHandler;
	private final ReportPrintService reportPrintService;

	@PostConstruct
	void postConstruct() {
		outPutHandler.println("Started ...");
		outPutHandler.println("");
		outPutHandler.println(mapper.getHeadLine());
		outPutHandler.println(mapper.getUnderLine());
		reportPrintService.getDetails().forEach(d -> outPutHandler.println(mapper.map(d)));
		outPutHandler.println(reportPrintService.getDetails().size() + " found");
		outPutHandler.println("");
	}
}
