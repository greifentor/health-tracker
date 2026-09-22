package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class MedicationLogCommands {

	private final DateStringToLocalDateConverter dateStringToLocalDateConverter;
	private final MedicationService medicationService;
	private final MedicationLogService medicationLogService;
	private final MedicationUnitService medicationUnitService;
	private final TimeStringToLocalDateConverter timeStringToLocalDateConverter;

	@ShellMethod(value = "Adds a medication log entry.", key = { "MEDICATION_LOG", "ML" })
	public String addMedicationLogEntry(
		@ShellOption(help = "The date of medication (DD.MM.JJJJ).", value = "date") String dateStr,
		@ShellOption(help = "The time of medication (HH:MM).", value = "time", defaultValue = "08:30") String timeStr,
		@ShellOption(
			help = "A string which should appear in the name of logged medication only.",
			value = "medication"
		) String medicationSearchStr,
		@ShellOption(
			help = "The count of medication units intaken.",
			value = "unitCount",
			defaultValue = "1"
		) String unitCountStr,
		@ShellOption(help = "The medication unit intaken.", value = "unit", defaultValue = "TBL") String unitSearchStr
	) {
		try {
			ensure(dateStr != null, "Date is not set!");
			ensure(medicationSearchStr != null, "Medication search string is not set!");
			ensure(timeStr != null, "Time is not set!");
			ensure(unitCountStr != null, "Unit count is not set!");
			LocalDate date = dateStringToLocalDateConverter.convert(dateStr);
			LocalTime time = timeStringToLocalDateConverter.convert(timeStr);
			Medication medication = medicationService
				.findByIdOrNameParticle(medicationSearchStr)
				.orElseThrow(() -> new NoSuchElementException("No medication found for: " + medicationSearchStr + "!"));
			MedicationUnit medicationUnit = medicationUnitService
				.findByIdOrNameParticle(unitSearchStr)
				.orElseThrow(() -> new NoSuchElementException("No medication unit found for: " + unitSearchStr + "!"));
			// Check for already existing data record.
			return null;
		} catch (DateTimeParseException dtpe) {
			return (
				"ERROR in line: MEDICATION_LOG " +
				dateStr +
				" " +
				timeStr +
				" " +
				medicationSearchStr +
				" " +
				unitCountStr +
				" " +
				unitSearchStr +
				" > Date string does not contain a valid date!"
			);
		} catch (IllegalArgumentException | NoSuchElementException | TooManyElementsException e) {
			return (
				"ERROR in line: MEDICATION_LOG " +
				dateStr +
				" " +
				timeStr +
				" " +
				medicationSearchStr +
				" " +
				unitCountStr +
				" " +
				unitSearchStr +
				" > " +
				e.getMessage()
			);
		}
	}
}
