package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.CommentToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands implements CommandsWithTimeOrDate {

	private final CommentService commentService;
	private final CommentToStringMapper commentToStringMapper;

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a comment.", key = { "add-comment", "ac" })
	public String addComment(
		@ShellOption(help = "The content of the comment.", value = "content") String content,
		@ShellOption(
			help = "The time of measurement (HH:MM or NOW).",
			value = "time",
			defaultValue = "NOW"
		) String timeOfMeasurementStr,
		@ShellOption(
			help = "The date of measurement (DD.MM.JJJJ or TODAY or TD).",
			value = "date",
			defaultValue = "TODAY"
		) String dateOfMeasurementStr
	) {
		try {
			LocalDate dateOfMeasurement = getDateFromParameter(dateOfMeasurementStr);
			LocalTime timeOfMeasurement = getTimeFromParameter(timeOfMeasurementStr);
			commentService.createComment(content, dateOfMeasurement, timeOfMeasurement);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Lists comments.", key = { "list-comments", "lc" })
	public String listComments() {
		try {
			outputHandler.println(commentToStringMapper.getHeadLine());
			outputHandler.println(commentToStringMapper.getUnderLine());
			commentService.listComments().forEach(bpm -> outputHandler.println(commentToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the comment with the passed id.", key = { "remove-comment", "rc" })
	public String removeComment(@ShellOption(help = "The id of the comment to remove.", value = "id") String id) {
		try {
			commentService.deleteComment(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
