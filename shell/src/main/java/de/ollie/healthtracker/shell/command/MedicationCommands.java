package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationToStringMapper;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class MedicationCommands {

	static final String MSG_NO_SUCH_MANUFACTURER_FOUND = "No manufacturer found for: ";

	private final ManufacturerService manufacturerService;
	private final MedicationService medicationService;
	private final MedicationToStringMapper medicationToStringMapper;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a medication.", key = { "add-medication", "amc" })
	public String addDoctor(
		@ShellOption(help = "The name of the medcation.", value = "name") String name,
		@ShellOption(help = "The manufacturers name part or id.", value = "manufacturer") String manufacturerNamePartOrId
	) {
		try {
			manufacturerService
				.findByIdOrNameParticle(manufacturerNamePartOrId)
				.ifPresentOrElse(
					manufacturer -> medicationService.createMedication(name, manufacturer),
					() -> {
						throw new NoSuchElementException(MSG_NO_SUCH_MANUFACTURER_FOUND + manufacturerNamePartOrId);
					}
				);
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists medications.", key = { "list-medications", "lmc" })
	public String listDoctors() {
		try {
			outputHandler.println(medicationToStringMapper.getHeadLine());
			outputHandler.println(medicationToStringMapper.getUnderLine());
			medicationService.listMedications().forEach(model -> outputHandler.println(medicationToStringMapper.map(model)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the medication with the passed id.", key = { "remove-medication", "rmc" })
	public String removeDoctor(@ShellOption(help = "The id of the medication to remove.", value = "id") String id) {
		try {
			medicationService.deleteMedication(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
