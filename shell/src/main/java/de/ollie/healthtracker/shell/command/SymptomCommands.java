package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.SymptomToStringMapper;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class SymptomCommands implements CommandsWithTimeOrDate {

	private static final String MSG_NO_SUCH_BODY_PART_FOUND = "No body part found with id: ";

	private final BodyPartService bodyPartService;
	private final SymptomService symptomService;
	private final SymptomToStringMapper symptomToStringMapper;

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a symptom.", key = { "add-symptom", "as" })
	public String addSymptom(
		@ShellOption(help = "The content of the symptom.", value = "content") String content,
		@ShellOption(
			help = "The date of measurement (DD.MM.JJJJ or TODAY or TD).",
			value = "date",
			defaultValue = "TODAY"
		) String dateOfMeasurementStr,
		@ShellOption(help = "The name of the body part or id.", value = "bodyPartIdOrNamePart") String bodyPartIdOrNamePart
	) {
		try {
			LocalDate dateOfMeasurement = getDateFromParameter(dateOfMeasurementStr);
			bodyPartService
				.findByIdOrNameParticle(bodyPartIdOrNamePart)
				.ifPresentOrElse(
					bodyPart -> symptomService.createSymptom(content, dateOfMeasurement, bodyPart),
					() -> {
						throw new NoSuchElementException(MSG_NO_SUCH_BODY_PART_FOUND + bodyPartIdOrNamePart);
					}
				);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists symptoms.", key = { "list-symptoms", "ls" })
	public String listSymptoms() {
		try {
			outputHandler.println(symptomToStringMapper.getHeadLine());
			outputHandler.println(symptomToStringMapper.getUnderLine());
			symptomService.listSymptoms().forEach(bpm -> outputHandler.println(symptomToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the symptom with the passed id.", key = { "remove-symptom", "rs" })
	public String removeSymptom(@ShellOption(help = "The id of the symptom to remove.", value = "id") String id) {
		try {
			symptomService.deleteSymptom(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
