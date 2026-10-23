package de.ollie.healthtracker.gui.swing.consumption;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link ConsumptionOverviewRow}s of the consumption overview: the total meat and fish consumption (in
 * kilograms, each broken down into its meat types as sub-positions) and the total alcohol consumption (in litres of pure
 * alcohol).
 */
@Named
@RequiredArgsConstructor
public class ConsumptionOverviewProvider {

	private static final String UNKNOWN_TYPE = "Unbekannt";

	private final MeatConsumptionService meatConsumptionService;
	private final AlcoholConsumptionService alcoholConsumptionService;

	public List<ConsumptionOverviewRow> create() {
		List<MeatConsumption> meatConsumptions = meatConsumptionService.listMeatConsumptions();
		List<ConsumptionOverviewRow> rows = new ArrayList<>();
		rows.addAll(categoryRows("Fleisch", MeatCategory.MEAT, meatConsumptions));
		rows.addAll(categoryRows("Fisch", MeatCategory.FISH, meatConsumptions));
		rows.addAll(alcoholRows());
		return rows;
	}

	/** The alcohol total row plus one sub-position row per alcohol type (ordered by type name), in litres of pure alcohol. */
	private List<ConsumptionOverviewRow> alcoholRows() {
		Map<String, Double> litresPerType = new TreeMap<>();
		double total = 0;
		for (AlcoholConsumption consumption : alcoholConsumptionService.listAlcoholConsumptions()) {
			AlcoholProduct product = consumption.getAlcoholProduct();
			double litres = pureAlcoholLitres(consumption.getLiter(), product);
			total += litres;
			String typeName = product == null ||
				product.getAlcoholType() == null ||
				product.getAlcoholType().getName() == null
				? UNKNOWN_TYPE
				: product.getAlcoholType().getName();
			litresPerType.merge(typeName, litres, Double::sum);
		}
		List<ConsumptionOverviewRow> rows = new ArrayList<>();
		rows.add(new ConsumptionOverviewRow("Alkohol", formatLitres(total), false));
		litresPerType.forEach((typeName, litres) ->
			rows.add(new ConsumptionOverviewRow(typeName, formatLitres(litres), true))
		);
		return rows;
	}

	/** The category total row plus one sub-position row per meat type of that category (ordered by type name). */
	private List<ConsumptionOverviewRow> categoryRows(
		String label,
		MeatCategory category,
		List<MeatConsumption> meatConsumptions
	) {
		Map<String, Double> gramsPerType = new TreeMap<>();
		double total = 0;
		for (MeatConsumption consumption : meatConsumptions) {
			MeatProduct product = consumption.getMeatProduct();
			if (product == null || product.getMeatType() == null || product.getMeatType().getCategory() != category) {
				continue;
			}
			double grams = gramsOf(consumption, product);
			total += grams;
			String typeName = product.getMeatType().getName() == null ? UNKNOWN_TYPE : product.getMeatType().getName();
			gramsPerType.merge(typeName, grams, Double::sum);
		}
		List<ConsumptionOverviewRow> rows = new ArrayList<>();
		rows.add(new ConsumptionOverviewRow(label, formatKilograms(total), false));
		gramsPerType.forEach((typeName, grams) ->
			rows.add(new ConsumptionOverviewRow(typeName, formatKilograms(grams), true))
		);
		return rows;
	}

	private double gramsOf(MeatConsumption consumption, MeatProduct product) {
		BigDecimal units = consumption.getUnits() == null ? BigDecimal.ZERO : consumption.getUnits();
		return units.doubleValue() * product.getAmountInGr();
	}

	private double pureAlcoholLitres(BigDecimal liter, AlcoholProduct product) {
		if (liter == null || product == null || product.getPercentVol() == null) {
			return 0;
		}
		return liter.multiply(product.getPercentVol()).movePointLeft(2).doubleValue();
	}

	private String formatKilograms(double grams) {
		return String.format(Locale.GERMANY, "%.3f kg", grams / 1000);
	}

	private String formatLitres(double litres) {
		return String.format(Locale.GERMANY, "%.2f l", litres);
	}
}
