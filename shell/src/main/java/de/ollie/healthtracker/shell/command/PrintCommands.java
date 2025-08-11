package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.ReportPrintService;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class PrintCommands implements CommandsWithTimeOrDate {

	private final ReportPrintService reportPrintService;

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	@ShellMethod(value = "Prints the health tracking report.", key = { "print-report", "pr" })
	public String print(
		@ShellOption(help = "Id of the print port to use.", value = "ppid", defaultValue = "CON") String printPortId,
		@ShellOption(
			help = "The date where the report starts (DD.MM.JJJJ, TODAY or TD).",
			value = "from",
			defaultValue = "TODAY"
		) String dateFromStr,
		@ShellOption(
			help = "The date where the report ends (DD.MM.JJJJ, TODAY or TD).",
			value = "to",
			defaultValue = "TODAY"
		) String dateToStr
	) {
		reportPrintService.printForTimeInterval(
			getDateFromParameter(dateFromStr),
			getDateFromParameter(dateToStr),
			printPortId,
			Map.of()
		);
		return Constants.OK;
	}
}
