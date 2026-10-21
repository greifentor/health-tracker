package de.ollie.healthtracker.gui.swing.chart.alcohol;

import de.ollie.healthtracker.core.service.AlcoholConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link AlcoholChartData} for the alcohol chart from the alcohol consumption history: one data point per day
 * of the window that had any consumption, holding the summed pure-alcohol amount of that day. The pure alcohol of a
 * consumption is the consumed drink volume multiplied by the product's alcohol content (percent by volume), so the chart
 * shows litres of pure alcohol rather than litres of drinks.
 */
@Named
@RequiredArgsConstructor
public class AlcoholChartDataProvider {

	private final AlcoholConsumptionHistoryService alcoholConsumptionHistoryService;

	/** Builds the chart data for the last {@code windowDays} days (ending today): the summed pure alcohol in litres per day. */
	public List<AlcoholChartData> create(int windowDays) {
		LocalDate today = LocalDate.now();
		Map<LocalDate, BigDecimal> perDay = new HashMap<>();
		alcoholConsumptionHistoryService
			.findAllOfLastDays(windowDays - 1)
			.forEach(consumption -> {
				if (consumption.getDate() == null) {
					return;
				}
				perDay.merge(
					consumption.getDate(),
					pureAlcoholLitres(consumption.getLiter(), consumption.getAlcoholProduct()),
					BigDecimal::add
				);
			});
		List<AlcoholChartData> result = new ArrayList<>();
		perDay.forEach((date, liters) ->
			result.add(new AlcoholChartData(windowPosition(date, today, windowDays), liters.doubleValue()))
		);
		return result;
	}

	/** The litres of pure alcohol of a consumption: drink volume times the product's percent by volume (0 if either is missing). */
	private BigDecimal pureAlcoholLitres(BigDecimal liter, AlcoholProduct product) {
		if (liter == null || product == null || product.getPercentVol() == null) {
			return BigDecimal.ZERO;
		}
		return liter.multiply(product.getPercentVol()).movePointLeft(2);
	}

	/** Maps a date to its 1-based position within the {@code windowDays}-day window ending today (today = last). */
	private int windowPosition(LocalDate date, LocalDate today, int windowDays) {
		return windowDays - (int) (today.toEpochDay() - date.toEpochDay());
	}
}
