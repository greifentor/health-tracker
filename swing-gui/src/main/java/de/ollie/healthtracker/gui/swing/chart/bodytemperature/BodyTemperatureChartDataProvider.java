package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.BodyTemperatureStatusService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link BodyTemperatureChartData} for the body temperature chart from the measurement history: one averaged
 * data point per day of the window, each carrying the aggregated {@link BodyTemperatureStatus} of that day. Each
 * measurement is classified individually (via {@link BodyTemperatureStatusService}) against the range of its own point
 * of measurement, so the range is never averaged.
 */
@Named
@RequiredArgsConstructor
public class BodyTemperatureChartDataProvider {

	private final BodyTemperatureMeasurementHistoryService bodyTemperatureMeasurementHistoryService;
	private final BodyTemperatureStatusService bodyTemperatureStatusService;

	/**
	 * Builds the chart data for the last {@code windowDays} days (ending today): the daily average temperature plus the
	 * day's status (ABOVE if any measurement that day was above its range, else BELOW if any was below, else NORMAL).
	 */
	public List<BodyTemperatureChartData> create(int windowDays) {
		LocalDate today = LocalDate.now();
		// Per day: [celsiusSum, count] for the averaged line.
		Map<LocalDate, double[]> perDay = new HashMap<>();
		// Per day: [anyBelow, anyAbove] over the measurements classified individually against their own range.
		Map<LocalDate, boolean[]> outOfRange = new HashMap<>();
		bodyTemperatureMeasurementHistoryService
			.findAllOfLastDays(windowDays - 1)
			.forEach(btm -> {
				if (btm.getCelsius() == null) {
					return;
				}
				double celsius = btm.getCelsius().doubleValue();
				BodyTemperatureStatus status = bodyTemperatureStatusService.calculateStatus(
					btm.getCelsius(),
					btm.getPointOfMeasurement()
				);
				double[] sums = perDay.computeIfAbsent(btm.getDateOfRecording(), d -> new double[2]);
				sums[0] += celsius;
				sums[1]++;
				boolean[] flags = outOfRange.computeIfAbsent(btm.getDateOfRecording(), d -> new boolean[2]);
				if (status == BodyTemperatureStatus.BELOW) {
					flags[0] = true;
				} else if (status == BodyTemperatureStatus.ABOVE) {
					flags[1] = true;
				}
			});
		List<BodyTemperatureChartData> result = new ArrayList<>();
		perDay.forEach((date, sums) -> {
			boolean[] flags = outOfRange.get(date);
			BodyTemperatureStatus status = flags[1]
				? BodyTemperatureStatus.ABOVE
				: flags[0] ? BodyTemperatureStatus.BELOW : BodyTemperatureStatus.NORMAL;
			result.add(new BodyTemperatureChartData(windowPosition(date, today, windowDays), sums[0] / sums[1], status));
		});
		return result;
	}

	/** Maps a date to its 1-based position within the {@code windowDays}-day window ending today (today = last). */
	private int windowPosition(LocalDate date, LocalDate today, int windowDays) {
		return windowDays - (int) (today.toEpochDay() - date.toEpochDay());
	}
}
