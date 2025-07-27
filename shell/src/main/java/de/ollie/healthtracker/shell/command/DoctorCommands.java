package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorToStringMapper;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class DoctorCommands {

	static final String MSG_NO_SUCH_DOCTOR_TYPE_FOUND = "No doctor type found for: ";
	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;
	private final DoctorToStringMapper doctorToStringMapper;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a doctor.", key = { "add-doctor", "ad" })
	public String addDoctor(
		@ShellOption(help = "The name of the doctor.", value = "name") String name,
		@ShellOption(
			help = "The name of the doctor type name part or id.",
			value = "doctorType"
		) String doctorTypeNamePartOrId
	) {
		try {
			doctorTypeService
				.findByIdOrNameParticle(doctorTypeNamePartOrId)
				.ifPresentOrElse(
					doctorType -> doctorService.createDoctor(name, doctorType),
					() -> {
						throw new NoSuchElementException(MSG_NO_SUCH_DOCTOR_TYPE_FOUND + doctorTypeNamePartOrId);
					}
				);
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists doctors.", key = { "list-doctors", "ld" })
	public String listDoctors() {
		try {
			outputHandler.println(doctorToStringMapper.getHeadLine());
			outputHandler.println(doctorToStringMapper.getUnderLine());
			doctorService.listDoctors().forEach(bpm -> outputHandler.println(doctorToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the doctor with the passed id.", key = { "remove-doctor", "rd" })
	public String removeDoctor(@ShellOption(help = "The id of the doctorType to remove.", value = "id") String id) {
		try {
			doctorService.deleteDoctor(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
