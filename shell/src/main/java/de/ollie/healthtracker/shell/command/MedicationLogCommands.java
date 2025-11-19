package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.MedicationLogToStringMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class MedicationLogCommands implements CommandsWithTimeOrDate {

	static final String MSG_NO_SUCH_MEDICATION_FOUND = "No medication found for: ";
	static final String MSG_NO_SUCH_MEDICATION_UNIT_FOUND = "No medication unit found for: ";
	static final String MSG_INVALID_NUMBER_VALUE = "Invalid number value: ";

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	private final MedicationService medicationService;
	private final MedicationLogService medicationLogService;
	private final MedicationLogToStringMapper medicationLogToStringMapper;
	private final MedicationUnitService medicationUnitService;
	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a medication log.", key = { "add-medication-log", "aml" })
	public String addMedicationLog(
		@ShellOption(help = "The medication name part or id.", value = "medication") String medicationNamePartOrId,
		@ShellOption(
			help = "The medication unit name part or id.",
			value = "medicationUnit"
		) String medicationUnitNamePartOrId,
		@ShellOption(
			help = "The count of medication units intaken.",
			value = "unitCount",
			defaultValue = "1"
		) String unitCountStr,
		@ShellOption(
			help = "The time of intake (HH:MM or NOW).",
			value = "time",
			defaultValue = "NOW"
		) String timeOfIntakeStr,
		@ShellOption(
			help = "The date of intake (DD.MM.JJJJ or TODAY or TD).",
			value = "date",
			defaultValue = "TODAY"
		) String dateOfIntakeStr,
		@ShellOption(help = "Self medication flag.", value = "selfMedication", defaultValue = "false") String selfMedication
	) {
		try {
			LocalDate dateOfIntake = getDateFromParameter(dateOfIntakeStr);
			LocalTime timeOfIntake = getTimeFromParameter(timeOfIntakeStr);
			BigDecimal unitCount = (unitCountStr != null ? getBigDecimal(unitCountStr) : new BigDecimal(1));
			Medication medication = medicationService
				.findByIdOrNameParticle(medicationNamePartOrId)
				.orElseThrow(() -> new NoSuchElementException(MSG_NO_SUCH_MEDICATION_FOUND + medicationNamePartOrId));
			medicationUnitService
				.findByIdOrNameParticle(medicationUnitNamePartOrId)
				.ifPresentOrElse(
					medicationUnit ->
						medicationLogService.createMedicationLog(
							medication,
							medicationUnit,
							dateOfIntake,
							Boolean.valueOf(selfMedication),
							timeOfIntake,
							unitCount
						),
					() -> {
						throw new NoSuchElementException(MSG_NO_SUCH_MEDICATION_UNIT_FOUND + medicationUnitNamePartOrId);
					}
				);
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	private BigDecimal getBigDecimal(String s) {
		try {
			return new BigDecimal(s);
		} catch (Exception e) {
			throw new IllegalArgumentException(MSG_INVALID_NUMBER_VALUE + s);
		}
	}

	@ShellMethod(value = "Lists medication logs.", key = { "list-medication-logs", "lml" })
	public String listMedicationLogs() {
		try {
			outputHandler.println(medicationLogToStringMapper.getHeadLine());
			outputHandler.println(medicationLogToStringMapper.getUnderLine());
			medicationLogService
				.listMedicationLogs()
				.forEach(model -> outputHandler.println(medicationLogToStringMapper.map(model)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(value = "Removes the medication log with the passed id.", key = { "remove-medication-log", "rml - Do " })
	public String removeMedicationLog(
		@ShellOption(help = "The id of the medication log to remove.", value = "id") String id
	) {
		try {
			medicationLogService.deleteMedicationLog(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
