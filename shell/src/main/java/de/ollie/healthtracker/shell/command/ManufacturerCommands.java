package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.ManufacturerToStringMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ManufacturerCommands {

	private final ManufacturerService manufacturerService;
	private final ManufacturerToStringMapper manufacturerToStringMapper;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a manufacturer.", key = { "add-manufacturer", "am" })
	public String addManufacturer(@ShellOption(help = "The name of the manufacturer.", value = "name") String name) {
		try {
			manufacturerService.createManufacturer(name);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists manufacturers.", key = { "list-manufacturers", "lm" })
	public String listManufacturers() {
		try {
			outputHandler.println(manufacturerToStringMapper.getHeadLine());
			outputHandler.println(manufacturerToStringMapper.getUnderLine());
			manufacturerService
				.listManufacturers()
				.forEach(bpm -> outputHandler.println(manufacturerToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the manufacturer with the passed id.", key = { "remove-manufacturer", "rm" })
	public String removeManufacturer(
		@ShellOption(help = "The id of the manufacturer to remove.", value = "id") String id
	) {
		try {
			manufacturerService.deleteManufacturer(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
