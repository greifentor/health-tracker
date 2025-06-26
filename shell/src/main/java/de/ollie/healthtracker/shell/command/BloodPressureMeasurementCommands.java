package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BloodPressureMeasurementCommands {

	private static final DateTimeFormatter DE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
	private static final DateTimeFormatter DE_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMAN);

	private final BloodPressureMeasurementService bloodPreasureMeasurement;

	@ShellMethod(value = "Adds a blood preasure measurement.", key = { "add-blood-pressure-measurement", "apbm" })
	public String add(
		@ShellOption(help = "The SYS mmHg value.", value = "sys") int sysMmHg,
		@ShellOption(help = "The DIA mmHg value.", value = "dia") int diaMmHg,
		@ShellOption(help = "The pulse per minute value.", value = "ppm") int pulsePerMinute,
		@ShellOption(help = "The status of the measurement.", value = "status") String statusStr,
		@ShellOption(
			help = "The date of measurement (DD.MM.JJJJ).",
			value = "date",
			defaultValue = "TODAY"
		) String dateOfMeasurementStr,
		@ShellOption(
			help = "The time of measurement (HH:MM).",
			value = "time",
			defaultValue = "NOW"
		) String timeOfMeasurementStr
	) {
		try {
			BloodPressureMeasurementStatus status = BloodPressureMeasurementStatus.valueOf(statusStr);
			LocalDate dateOfMeasurement = LocalDate.parse(dateOfMeasurementStr, DE_DATE_FORMAT);
			LocalTime timeOfMeasurement = LocalTime.parse(timeOfMeasurementStr, DE_TIME_FORMAT);
			bloodPreasureMeasurement.createRecording(
				sysMmHg,
				diaMmHg,
				pulsePerMinute,
				status,
				dateOfMeasurement,
				timeOfMeasurement
			);
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
