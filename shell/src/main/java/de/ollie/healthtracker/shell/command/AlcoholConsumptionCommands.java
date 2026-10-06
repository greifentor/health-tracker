package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.exception.RecordAlreadyExistingException;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class AlcoholConsumptionCommands {

	private final BigDecimalFactory bigDecimalFactory;
	private final DateStringToLocalDateConverter dateStringToLocalDateConverter;
	private final AlcoholConsumptionService alcoholConsumptionService;
	private final AlcoholProductService alcoholProductService;

	@ShellMethod(value = "Adds a alcohol consumption entry.", key = { "ALCOHOL_CONSUMPTION", "AC" })
	public String addMedicationLogEntry(
		@ShellOption(help = "The date of medication (DD.MM.JJJJ).", value = "date") String dateStr,
		@ShellOption(
			help = "A string which should appear in the name of logged alcohol only.",
			value = "alcohol"
		) String alcoholProductSearchStr,
		@ShellOption(help = "The liter of alcohol consumed.", value = "liter", defaultValue = "0.3") double literDouble
	) {
		BigDecimal liter = bigDecimalFactory.create("" + literDouble);
		try {
			ensure(dateStr != null, "Date is not set!");
			ensure(alcoholProductSearchStr != null, "Alcohol search string is not set!");
			LocalDate date = dateStringToLocalDateConverter.convert(dateStr);
			AlcoholProduct alcoholProduct = alcoholProductService
				.findByIdOrNameParticle(alcoholProductSearchStr)
				.orElseThrow(() -> new NoSuchElementException("No alcohol product found for: " + alcoholProductSearchStr + "!")
				);
			ensure(
				!alcoholConsumptionService.isDuplicate(date, alcoholProduct, liter),
				() -> new RecordAlreadyExistingException("Alcohol consumption entry is already existing!")
			);
			alcoholConsumptionService.createAlcoholConsumption(date, alcoholProduct, "", liter);
			return ("OK: ALCOHOL_CONSUMPTION " + dateStr + " " + alcoholProductSearchStr + " " + liter);
		} catch (DateTimeParseException dtpe) {
			return (
				"ERROR in line: ALCOHOL_CONSUMPTION " +
				dateStr +
				" " +
				alcoholProductSearchStr +
				" " +
				liter +
				" > Date string does not contain a valid date!"
			);
		} catch (RecordAlreadyExistingException raee) {
			return (
				"ALREADY EXISTING: ALCOHOL_CONSUMPTION " +
				dateStr +
				" " +
				alcoholProductSearchStr +
				" " +
				liter +
				" > " +
				raee.getMessage()
			);
		} catch (Exception e) {
			return (
				"ERROR in line: ALCOHOL_CONSUMPTION " +
				dateStr +
				" " +
				alcoholProductSearchStr +
				" " +
				liter +
				" > " +
				e.getMessage()
			);
		}
	}
}
