package de.ollie.healthtracker.gui.swing.chart.nutrition;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link NutritionChartData} for the nutrition chart from the meat consumption history of the last
 * {@value #HISTORY_DAYS} days: one data point per month, classifying each recording day as a meat day (any meat
 * consumption), a fish day (fish but no meat) or otherwise a veggie day, and counting those per month.
 */
@Named
@RequiredArgsConstructor
public class NutritionChartDataProvider {

	private static final int HISTORY_DAYS = 366;

	private final MeatConsumptionHistoryService meatConsumptionHistoryService;

	public List<NutritionChartData> create() {
		// Per recording day: [fishConsumptions, meatConsumptions].
		Map<LocalDate, int[]> perDay = new HashMap<>();
		meatConsumptionHistoryService
			.findAllOfLastDays(HISTORY_DAYS)
			.forEach(mc -> {
				int[] counts = perDay.computeIfAbsent(mc.getDateOfRecording(), d -> new int[2]);
				MeatCategory category = mc.getMeatProduct().getMeatType().getCategory();
				if (category == MeatCategory.FISH) {
					counts[0]++;
				} else if (category == MeatCategory.MEAT) {
					counts[1]++;
				}
			});
		// Per month: [fishDays, meatDays] - a day is a fish day if it had fish but no meat, a meat day if it had meat.
		Map<YearMonth, int[]> perMonth = new HashMap<>();
		perDay.forEach((day, counts) -> {
			int[] monthCounts = perMonth.computeIfAbsent(YearMonth.of(day.getYear(), day.getMonthValue()), ym -> new int[2]);
			if (counts[0] > 0 && counts[1] == 0) {
				monthCounts[0]++;
			}
			if (counts[1] > 0) {
				monthCounts[1]++;
			}
		});
		// One chart point per month, sorted chronologically; veggie = days in month - meat - fish.
		List<NutritionChartData> result = new ArrayList<>();
		perMonth
			.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByKey())
			.forEach(entry -> {
				int fish = entry.getValue()[0];
				int meat = entry.getValue()[1];
				int veggie = entry.getKey().getMonth().maxLength() - meat - fish;
				result.add(new NutritionChartData(entry.getKey().getMonthValue(), meat, fish, veggie));
			});
		return result;
	}
}
