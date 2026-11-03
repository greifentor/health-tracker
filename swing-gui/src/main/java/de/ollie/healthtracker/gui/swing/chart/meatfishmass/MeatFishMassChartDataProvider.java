package de.ollie.healthtracker.gui.swing.chart.meatfishmass;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Builds the {@link MeatFishMassChartData} for the meat and fish mass chart from the meat consumption history: one
 * contiguous data point per calendar month of the window (the last {@code months} months ending with the current month),
 * oldest first. Per month the consumed mass (units times the product's amount in grams) is summed per category and
 * converted to kilograms; months without any consumption are filled with zero. As the months are addressed year-aware,
 * the window may span more than twelve months.
 */
@Named
@RequiredArgsConstructor
public class MeatFishMassChartDataProvider {

	private static final double GRAMS_PER_KILOGRAM = 1000.0;

	private final MeatConsumptionHistoryService meatConsumptionHistoryService;

	public List<MeatFishMassChartData> create(int months) {
		int windowMonths = Math.max(1, months);
		YearMonth currentMonth = YearMonth.now();
		YearMonth startMonth = currentMonth.minusMonths(windowMonths - 1L);
		LocalDate windowStart = startMonth.atDay(1);
		int historyDays = (int) ChronoUnit.DAYS.between(windowStart, LocalDate.now());
		// Per month: [meatGrams, fishGrams].
		Map<YearMonth, double[]> perMonth = new HashMap<>();
		meatConsumptionHistoryService
			.findAllOfLastDays(historyDays)
			.forEach(mc -> {
				LocalDate date = mc.getDateOfRecording();
				if (date == null || date.isBefore(windowStart)) {
					return;
				}
				MeatCategory category = categoryOf(mc);
				if (category == null) {
					return;
				}
				double[] grams = perMonth.computeIfAbsent(YearMonth.from(date), ym -> new double[2]);
				if (category == MeatCategory.FISH) {
					grams[1] += gramsOf(mc);
				} else if (category == MeatCategory.MEAT) {
					grams[0] += gramsOf(mc);
				}
			});
		List<MeatFishMassChartData> result = new ArrayList<>();
		for (YearMonth month = startMonth; !month.isAfter(currentMonth); month = month.plusMonths(1)) {
			double[] grams = perMonth.getOrDefault(month, new double[2]);
			result.add(
				new MeatFishMassChartData(
					month.getYear(),
					month.getMonthValue(),
					grams[0] / GRAMS_PER_KILOGRAM,
					grams[1] / GRAMS_PER_KILOGRAM
				)
			);
		}
		return result;
	}

	private MeatCategory categoryOf(MeatConsumption consumption) {
		MeatProduct product = consumption.getMeatProduct();
		if (product == null || product.getMeatType() == null) {
			return null;
		}
		return product.getMeatType().getCategory();
	}

	private double gramsOf(MeatConsumption consumption) {
		BigDecimal units = consumption.getUnits() == null ? BigDecimal.ZERO : consumption.getUnits();
		return units.doubleValue() * consumption.getMeatProduct().getAmountInGr();
	}
}
