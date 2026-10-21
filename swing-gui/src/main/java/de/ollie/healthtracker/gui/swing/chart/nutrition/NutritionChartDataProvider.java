package de.ollie.healthtracker.gui.swing.chart.nutrition;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link NutritionChartData} for the nutrition chart from the meat consumption history: one contiguous data
 * point per calendar month of the window (the last {@code months} months ending with the current month), oldest first.
 * Each recording day is classified as a meat day (any meat consumption), a fish day (fish but no meat) or otherwise a
 * veggie day, and those are counted per month; months without any consumption are filled as full-veggie months. As the
 * months are addressed year-aware, the window may span more than twelve months.
 */
@Named
@RequiredArgsConstructor
public class NutritionChartDataProvider {

	private final MeatConsumptionHistoryService meatConsumptionHistoryService;

	public List<NutritionChartData> create(int months) {
		int windowMonths = Math.max(1, months);
		YearMonth currentMonth = YearMonth.now();
		YearMonth startMonth = currentMonth.minusMonths(windowMonths - 1L);
		LocalDate windowStart = startMonth.atDay(1);
		int historyDays = (int) ChronoUnit.DAYS.between(windowStart, LocalDate.now());
		// Per recording day within the window: [fishConsumptions, meatConsumptions].
		Map<LocalDate, int[]> perDay = new HashMap<>();
		meatConsumptionHistoryService
			.findAllOfLastDays(historyDays)
			.forEach(mc -> {
				LocalDate date = mc.getDateOfRecording();
				if (date == null || date.isBefore(windowStart)) {
					return;
				}
				int[] counts = perDay.computeIfAbsent(date, d -> new int[2]);
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
			int[] monthCounts = perMonth.computeIfAbsent(YearMonth.from(day), ym -> new int[2]);
			if (counts[0] > 0 && counts[1] == 0) {
				monthCounts[0]++;
			}
			if (counts[1] > 0) {
				monthCounts[1]++;
			}
		});
		// One chart point per month of the window, oldest first; veggie = days in month - meat - fish.
		List<NutritionChartData> result = new ArrayList<>();
		for (YearMonth month = startMonth; !month.isAfter(currentMonth); month = month.plusMonths(1)) {
			int[] counts = perMonth.getOrDefault(month, new int[2]);
			int fish = counts[0];
			int meat = counts[1];
			int veggie = month.lengthOfMonth() - meat - fish;
			result.add(new NutritionChartData(month.getYear(), month.getMonthValue(), meat, fish, veggie));
		}
		return result;
	}
}
