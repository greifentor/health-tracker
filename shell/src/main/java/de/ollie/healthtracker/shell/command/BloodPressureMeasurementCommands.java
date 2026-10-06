package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.exception.RecordAlreadyExistingException;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BloodPressureMeasurementCommands {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final DateStringToLocalDateConverter dateStringToLocalDateConverter;
	private final TimeStringToLocalDateConverter timeStringToLocalDateConverter;

	@ShellMethod(value = "Adds a blood pressure measurement entry.", key = { "BLOOD_PRESSURE", "BP" })
	public String addMedicationLogEntry(
		@ShellOption(help = "The date of measurement (DD.MM.JJJJ).", value = "date") String dateStr,
		@ShellOption(help = "The time of measurement (HH:MM).", value = "time") String timeStr,
		@ShellOption(help = "The value for systolic pressure.", value = "systolic") int systolic,
		@ShellOption(help = "The value for diastolic pressure.", value = "diastolic") int diastolic,
		@ShellOption(help = "The value pulse.", value = "pulse") int pulse,
		@ShellOption(help = "The blood pressure classification.", value = "status") WhoBloodPressureClassification status
	) {
		try {
			ensure(dateStr != null, "Date is not set!");
			ensure(timeStr != null, "Time is not set!");
			LocalDate date = dateStringToLocalDateConverter.convert(dateStr);
			LocalTime time = timeStringToLocalDateConverter.convert(timeStr);
			ensure(
				!bloodPressureMeasurementService.isDuplicate(date, diastolic, pulse, systolic, time, status),
				() -> new RecordAlreadyExistingException("Medication log entry is already existing!")
			);
			bloodPressureMeasurementService.createBloodPressureMeasurement(
				"",
				date,
				diastolic,
				pulse,
				systolic,
				time,
				status,
				false
			);
			return (
				"OK: BLOOD_PRESSURE " + dateStr + " " + timeStr + " " + systolic + " " + diastolic + " " + pulse + " " + status
			);
		} catch (DateTimeParseException dtpe) {
			return (
				"ERROR in line: BLOOD_PRESSURE " +
				dateStr +
				" " +
				timeStr +
				" " +
				systolic +
				" " +
				diastolic +
				" " +
				pulse +
				" " +
				status +
				" > Date string does not contain a valid date!"
			);
		} catch (RecordAlreadyExistingException raee) {
			return (
				"ALREADY EXISTING: BLOOD_PRESSURE " +
				dateStr +
				" " +
				timeStr +
				" " +
				systolic +
				" " +
				diastolic +
				" " +
				pulse +
				" " +
				status +
				" > " +
				raee.getMessage()
			);
		} catch (Exception e) {
			return (
				"ERROR in line: BLOOD_PRESSURE " +
				dateStr +
				" " +
				timeStr +
				" " +
				systolic +
				" " +
				diastolic +
				" " +
				pulse +
				" " +
				status +
				" > " +
				e.getMessage()
			);
		}
	}
}
