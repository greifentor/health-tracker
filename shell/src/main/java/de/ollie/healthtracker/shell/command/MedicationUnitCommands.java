package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationUnitToStringMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class MedicationUnitCommands {

	private final MedicationUnitService medicationUnitService;
	private final MedicationUnitToStringMapper medicationUnitToStringMapper;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a medication unit.", key = { "add-medication-unit", "amu" })
	public String addMedicationUnit(
		@ShellOption(help = "The name of the medication unit.", value = "name") String name,
		@ShellOption(help = "The token of the medication unit.", value = "token") String token
	) {
		try {
			medicationUnitService.createMedicationUnit(name, token);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists medication units.", key = { "list-medication-units", "lmu" })
	public String listMedicationUnits() {
		try {
			outputHandler.println(medicationUnitToStringMapper.getHeadLine());
			outputHandler.println(medicationUnitToStringMapper.getUnderLine());
			medicationUnitService
				.listMedicationUnits()
				.forEach(lmu -> outputHandler.println(medicationUnitToStringMapper.map(lmu)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the medication unit with the passed id.", key = { "remove-medication-unit", "rmu" })
	public String removeMedicationUnit(
		@ShellOption(help = "The id of the medication unit to remove.", value = "id") String id
	) {
		try {
			medicationUnitService.deleteMedicationUnit(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
