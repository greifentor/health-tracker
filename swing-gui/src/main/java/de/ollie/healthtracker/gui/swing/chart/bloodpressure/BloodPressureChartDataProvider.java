package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link BloodPressureChartData} for the blood pressure chart from the measurement history: one data point
 * per day of the window, holding the average systolic / diastolic / pulse values. The status is the WHO classification
 * of the averaged systolic / diastolic value, computed by the {@link WhoBloodPressureClassificationService}.
 */
@Named
@RequiredArgsConstructor
public class BloodPressureChartDataProvider {

	private final BloodPressureMeasurementHistoryService bloodPressureMeasurementHistoryService;
	private final WhoBloodPressureClassificationService whoBloodPressureClassificationService;

	/** Builds the chart data for the last {@code windowDays} days (ending today): daily averages plus WHO status. */
	public List<BloodPressureChartData> create(int windowDays) {
		LocalDate today = LocalDate.now();
		// Average sys / dia / pulse per day over the window. Index: [sysSum, diaSum, pulseSum, count].
		Map<LocalDate, int[]> perDay = new HashMap<>();
		bloodPressureMeasurementHistoryService
			.findAllOfLastDays(windowDays - 1)
			.forEach(bpm -> {
				int[] sums = perDay.computeIfAbsent(bpm.getDateOfRecording(), d -> new int[4]);
				sums[0] += bpm.getSysMmHg();
				sums[1] += bpm.getDiaMmHg();
				sums[2] += bpm.getPulsePerMinute();
				sums[3]++;
			});
		List<BloodPressureChartData> result = new ArrayList<>();
		perDay.forEach((date, sums) -> {
			int sys = Math.round((float) sums[0] / sums[3]);
			int dia = Math.round((float) sums[1] / sums[3]);
			int pulse = Math.round((float) sums[2] / sums[3]);
			result.add(
				new BloodPressureChartData(
					windowPosition(date, today, windowDays),
					sys,
					dia,
					pulse,
					whoBloodPressureClassificationService.calculateClassification(sys, dia)
				)
			);
		});
		return result;
	}

	/** Maps a date to its 1-based position within the {@code windowDays}-day window ending today (today = last). */
	private int windowPosition(LocalDate date, LocalDate today, int windowDays) {
		return windowDays - (int) (today.toEpochDay() - date.toEpochDay());
	}
}
