package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.CommentToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

	private static final DateTimeFormatter DE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
	private static final DateTimeFormatter DE_TIME_FORMAT = new DateTimeFormatterBuilder()
		.appendValue(ChronoField.HOUR_OF_DAY, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
		.appendLiteral(':')
		.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
		.toFormatter();

	private final CommentService commentService;
	private final CommentToStringMapper commentToStringMapper;
	private final LocalDateFactory localDateFactory;
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

	private LocalDate getDateFromParameter(String dateOfMeasurementStr) {
		if (
			(dateOfMeasurementStr == null) ||
			"TODAY".equalsIgnoreCase(dateOfMeasurementStr) ||
			"TD".equalsIgnoreCase(dateOfMeasurementStr)
		) {
			return localDateFactory.now();
		}
		return LocalDate.parse(dateOfMeasurementStr, DE_DATE_FORMAT);
	}

	private LocalTime getTimeFromParameter(String timeOfMeasurementStr) {
		if ((timeOfMeasurementStr == null) || "NOW".equalsIgnoreCase(timeOfMeasurementStr)) {
			return localTimeFactory.now();
		}
		return LocalTime.parse(timeOfMeasurementStr, DE_TIME_FORMAT);
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
