package de.ollie.healthtracker.gui.swing.chart.weight;

import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link WeightChartData} for the weight chart from the measurement history: one data point per day of the
 * window, holding the average weight of that day.
 */
@Named
@RequiredArgsConstructor
public class WeightChartDataProvider {

	private final WeightMeasurementHistoryService weightMeasurementHistoryService;

	/** Builds the chart data for the last {@code windowDays} days (ending today): the daily average weight in kg. */
	public List<WeightChartData> create(int windowDays) {
		LocalDate today = LocalDate.now();
		// Average the weight per day over the window. Index: [kgSum, count].
		Map<LocalDate, double[]> perDay = new HashMap<>();
		weightMeasurementHistoryService
			.findAllOfLastDays(windowDays - 1)
			.forEach(wm -> {
				if (wm.getKg() == null) {
					return;
				}
				double[] sums = perDay.computeIfAbsent(wm.getDateOfRecording(), d -> new double[2]);
				sums[0] += wm.getKg().doubleValue();
				sums[1]++;
			});
		List<WeightChartData> result = new ArrayList<>();
		perDay.forEach((date, sums) ->
			result.add(new WeightChartData(windowPosition(date, today, windowDays), sums[0] / sums[1]))
		);
		return result;
	}

	/** Maps a date to its 1-based position within the {@code windowDays}-day window ending today (today = last). */
	private int windowPosition(LocalDate date, LocalDate today, int windowDays) {
		return windowDays - (int) (today.toEpochDay() - date.toEpochDay());
	}
}
