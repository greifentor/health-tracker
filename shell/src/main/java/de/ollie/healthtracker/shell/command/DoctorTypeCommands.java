package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorTypeToStringMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class DoctorTypeCommands {

	private final DoctorTypeService DoctorTypeService;
	private final DoctorTypeToStringMapper DoctorTypeToStringMapper;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a doctor type.", key = { "add-doctor-type", "adt" })
	public String addDoctorType(@ShellOption(help = "The name of the doctor type.", value = "name") String name) {
		try {
			DoctorTypeService.createDoctorType(name);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists doctor types.", key = { "list-doctor-types", "ldt" })
	public String listDoctorTypes() {
		try {
			outputHandler.println(DoctorTypeToStringMapper.getHeadLine());
			outputHandler.println(DoctorTypeToStringMapper.getUnderLine());
			DoctorTypeService.listDoctorTypes().forEach(bpm -> outputHandler.println(DoctorTypeToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the doctor type with the passed id.", key = { "remove-doctor-type", "rdt" })
	public String removeDoctorType(@ShellOption(help = "The id of the doctorType to remove.", value = "id") String id) {
		try {
			DoctorTypeService.deleteDoctorType(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
