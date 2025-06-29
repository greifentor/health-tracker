package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BloodPressureMeasurementCommands {

	private static final DateTimeFormatter DE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
	private static final DateTimeFormatter DE_TIME_FORMAT = new DateTimeFormatterBuilder()
		.appendValue(ChronoField.HOUR_OF_DAY, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
		.appendLiteral(':')
		.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
		.toFormatter();

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BloodPressureMeasurementToStringMapper bloodPressureMeasurementToStringMapper;
	private final LocalDateFactory localDateFactory;
	private final LocalTimeFactory localTimeFactory;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a blood preasure measurement.", key = { "add-blood-pressure-measurement", "abpm" })
	public String add(
		@ShellOption(help = "The SYS mmHg value.", value = "sys") int sysMmHg,
		@ShellOption(help = "The DIA mmHg value.", value = "dia") int diaMmHg,
		@ShellOption(help = "The pulse per minute value.", value = "ppm") int pulsePerMinute,
		@ShellOption(help = "The status of the measurement.", value = "status") String statusStr,
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
			BloodPressureMeasurementStatus status = getStatusFromParameter(statusStr);
			LocalDate dateOfMeasurement = getDateFromParameter(dateOfMeasurementStr);
			LocalTime timeOfMeasurement = getTimeFromParameter(timeOfMeasurementStr);
			bloodPressureMeasurementService.createRecording(
				sysMmHg,
				diaMmHg,
				pulsePerMinute,
				status,
				dateOfMeasurement,
				timeOfMeasurement
			);
			return Constants.OK;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.ERROR + e.getMessage();
		}
	}

	private BloodPressureMeasurementStatus getStatusFromParameter(String statusStr) {
		try {
			int value = Integer.parseInt(statusStr);
			return BloodPressureMeasurementStatus.ofValue(value);
		} catch (Exception e) {}
		return BloodPressureMeasurementStatus.valueOf(statusStr);
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

	@ShellMethod(value = "Lists blood preasure measurements.", key = { "list-blood-pressure-measurements", "lbpm" })
	public String list() {
		try {
			outputHandler.println(bloodPressureMeasurementToStringMapper.getHeadLine());
			bloodPressureMeasurementService
				.list()
				.forEach(bpm -> outputHandler.println(bloodPressureMeasurementToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
