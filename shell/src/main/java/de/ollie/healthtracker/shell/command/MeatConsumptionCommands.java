package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.exception.RecordAlreadyExistingException;
import de.ollie.healthtracker.core.service.model.MeatProduct;
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
public class MeatConsumptionCommands {

	private final BigDecimalFactory bigDecimalFactory;
	private final DateStringToLocalDateConverter dateStringToLocalDateConverter;
	private final MeatConsumptionService meatConsumptionService;
	private final MeatProductService meatProductService;

	@ShellMethod(value = "Adds a meat consumption entry.", key = { "MEAT_CONSUMPTION", "MC" })
	public String addMedicationLogEntry(
		@ShellOption(help = "The date of meat consumption (DD.MM.JJJJ).", value = "date") String dateStr,
		@ShellOption(
			help = "A string which should appear in the name of logged meat only.",
			value = "meat"
		) String meatProductSearchStr,
		@ShellOption(help = "The units of meat product consumed.", value = "units", defaultValue = "1.0") double unitsDouble
	) {
		BigDecimal units = bigDecimalFactory.create("" + unitsDouble);
		try {
			ensure(dateStr != null, "Date is not set!");
			ensure(meatProductSearchStr != null, "Meat product search string is not set!");
			LocalDate date = dateStringToLocalDateConverter.convert(dateStr);
			MeatProduct meatProduct = meatProductService
				.findByIdOrDescriptionParticle(meatProductSearchStr)
				.orElseThrow(() -> new NoSuchElementException("No meat product found for: " + meatProductSearchStr + "!"));
			ensure(
				!meatConsumptionService.isDuplicate(date, meatProduct, units),
				() -> new RecordAlreadyExistingException("Meat consumption entry is already existing!")
			);
			meatConsumptionService.createMeatConsumption(date, meatProduct, units);
			return ("OK: MEAT_CONSUMPTION " + dateStr + " " + meatProductSearchStr + " " + units);
		} catch (DateTimeParseException dtpe) {
			return (
				"ERROR in line: MEAT_CONSUMPTION " +
				dateStr +
				" " +
				meatProductSearchStr +
				" " +
				units +
				" > Date string does not contain a valid date!"
			);
		} catch (RecordAlreadyExistingException raee) {
			return (
				"ALREADY EXISTING: MEAT_CONSUMPTION " +
				dateStr +
				" " +
				meatProductSearchStr +
				" " +
				units +
				" > " +
				raee.getMessage()
			);
		} catch (Exception e) {
			return (
				"ERROR in line: MEAT_CONSUMPTION " + dateStr + " " + meatProductSearchStr + " " + units + " > " + e.getMessage()
			);
		}
	}
}
