package de.ollie.healthtracker.gui.swing.chart.alcohol;

import de.ollie.healthtracker.core.service.AlcoholConsumptionHistoryService;
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
 * of the window that had any consumption, holding the summed liters of that day.
 */
@Named
@RequiredArgsConstructor
public class AlcoholChartDataProvider {

	private final AlcoholConsumptionHistoryService alcoholConsumptionHistoryService;

	/** Builds the chart data for the last {@code windowDays} days (ending today): the summed alcohol consumption in liters per day. */
	public List<AlcoholChartData> create(int windowDays) {
		LocalDate today = LocalDate.now();
		Map<LocalDate, BigDecimal> perDay = new HashMap<>();
		alcoholConsumptionHistoryService
			.findAllOfLastDays(windowDays - 1)
			.forEach(consumption -> {
				if (consumption.getDate() == null) {
					return;
				}
				BigDecimal liter = consumption.getLiter() == null ? BigDecimal.ZERO : consumption.getLiter();
				perDay.merge(consumption.getDate(), liter, BigDecimal::add);
			});
		List<AlcoholChartData> result = new ArrayList<>();
		perDay.forEach((date, liters) ->
			result.add(new AlcoholChartData(windowPosition(date, today, windowDays), liters.doubleValue()))
		);
		return result;
	}

	/** Maps a date to its 1-based position within the {@code windowDays}-day window ending today (today = last). */
	private int windowPosition(LocalDate date, LocalDate today, int windowDays) {
		return windowDays - (int) (today.toEpochDay() - date.toEpochDay());
	}
}
