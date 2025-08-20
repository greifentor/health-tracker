package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
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
public class BloodPressureMeasurementCommands implements CommandsWithTimeOrDate {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BloodPressureMeasurementToStringMapper bloodPressureMeasurementToStringMapper;

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a blood preasure measurement.", key = { "add-blood-pressure-measurement", "abpm" })
	public String addBloodPressureMeasurement(
		@ShellOption(help = "The SYS mmHg value.", value = "sys") int sysMmHg,
		@ShellOption(help = "The DIA mmHg value.", value = "dia") int diaMmHg,
		@ShellOption(help = "The pulse per minute value.", value = "ppm") int pulsePerMinute,
		@ShellOption(help = "Set flag for irregular heartbeat ('y' or 'yes').", value = "n") String irregularHeartbeatStr,
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
				getBooleanFromString(irregularHeartbeatStr),
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

	boolean getBooleanFromString(String s) {
		s = s != null ? s.toLowerCase() : null;
		return "y".equals(s) || "yes".equals(s);
	}

	private BloodPressureMeasurementStatus getStatusFromParameter(String statusStr) {
		try {
			int value = Integer.parseInt(statusStr);
			return BloodPressureMeasurementStatus.ofValue(value);
		} catch (Exception e) {}
		return BloodPressureMeasurementStatus.valueOf(statusStr);
	}

	@ShellMethod(value = "Lists blood preasure measurements.", key = { "list-blood-pressure-measurements", "lbpm" })
	public String listBloodPressureMeasurements() {
		try {
			outputHandler.println(bloodPressureMeasurementToStringMapper.getHeadLine());
			outputHandler.println(bloodPressureMeasurementToStringMapper.getUnderLine());
			bloodPressureMeasurementService
				.listRecordings()
				.forEach(bpm -> outputHandler.println(bloodPressureMeasurementToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(
		value = "Removes the blood preasure measurement with the passed id.",
		key = { "remove-blood-pressure-measurement", "rbpm" }
	)
	public String removeBloodPressureMeasurement(
		@ShellOption(help = "The id of the blood pressure measurement to remove.", value = "id") String id
	) {
		try {
			bloodPressureMeasurementService.deleteRecording(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
