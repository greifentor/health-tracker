package de.ollie.healthtracker.gui.swing.chart.alcohol;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.gui.swing.chart.TrendLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Draws the alcohol chart onto a {@link Graphics2D}: the value grid (y-axis, liters), the day grid (x-axis), the axes,
 * one bar per day with consumption and the legend. Holds no mutable state, so a single instance can be reused for every
 * repaint.
 */
class AlcoholChartRenderer {

	private static final int PADDING_LEFT = 48;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 44;
	private static final int LEGEND_HEIGHT = 24;
	private static final int LABEL_GAP = 2;
	private static final int DAY_LABEL_STEP = 5;
	private static final int TARGET_VALUE_GRID_LINES = 6;
	private static final double BAR_WIDTH_FACTOR = 0.6;
	private static final int MIN_BAR_WIDTH = 3;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);
	private static final Color COLOR_ALCOHOL = new Color(0x8E44AD);
	private static final Stroke TREND_STROKE = new BasicStroke(
		1.5f,
		BasicStroke.CAP_BUTT,
		BasicStroke.JOIN_MITER,
		1f,
		new float[] { 2f, 3f },
		0f
	);

	void render(
		Graphics2D g2,
		int width,
		int height,
		List<AlcoholChartData> data,
		int days,
		LocalDate windowEndDate,
		double maxLiters
	) {
		int plotLeft = PADDING_LEFT;
		int plotRight = width - PADDING_RIGHT;
		int plotTop = PADDING_TOP;
		int plotBottom = height - PADDING_BOTTOM - LEGEND_HEIGHT;
		if (plotRight <= plotLeft || plotBottom <= plotTop || maxLiters <= 0) {
			return;
		}
		drawValueGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, maxLiters);
		drawDayGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, days, windowEndDate);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(plotLeft, plotTop, plotLeft, plotBottom);
		g2.drawLine(plotLeft, plotBottom, plotRight, plotBottom);
		drawBars(g2, plotLeft, plotRight, plotTop, plotBottom, data, days, maxLiters);
		drawTrend(g2, plotLeft, plotRight, plotTop, plotBottom, data, days, maxLiters);
		drawLegend(g2, plotLeft, height - LEGEND_HEIGHT);
	}

	/** Horizontal grid lines and the right-aligned y-axis value labels at "nice" steps within [0, max]. */
	private void drawValueGridAndLabels(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		double max
	) {
		FontMetrics fm = g2.getFontMetrics();
		double step = niceStep(max / TARGET_VALUE_GRID_LINES);
		for (double value = 0; value <= max + 1e-9; value += step) {
			int y = yForLiters(value, plotTop, plotBottom, max);
			g2.setColor(COLOR_GRID);
			g2.drawLine(plotLeft, y, plotRight, y);
			g2.setColor(COLOR_AXIS);
			String label = formatValue(value);
			g2.drawString(label, plotLeft - HGAP - fm.stringWidth(label), y + fm.getAscent() / 2 - 1);
		}
	}

	/**
	 * A vertical grid line and a centered day-of-month label for the first day position, every {@value #DAY_LABEL_STEP}th
	 * position and the last. The label is derived from {@code windowEndDate}, which is the calendar date of the last
	 * (right-most) position.
	 */
	private void drawDayGridAndLabels(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		int days,
		LocalDate windowEndDate
	) {
		FontMetrics fm = g2.getFontMetrics();
		for (int position = 1; position <= days; position++) {
			if (position != 1 && position != days && position % DAY_LABEL_STEP != 0) {
				continue;
			}
			int x = xForDay(plotLeft, plotRight, position, days);
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, plotTop, x, plotBottom);
			g2.setColor(COLOR_AXIS);
			LocalDate date = windowEndDate.minusDays((long) days - position);
			String dayText = Integer.toString(date.getDayOfMonth());
			String monthText = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
			int dayY = plotBottom + fm.getAscent() + LABEL_GAP;
			g2.drawString(dayText, x - fm.stringWidth(dayText) / 2, dayY);
			g2.drawString(monthText, x - fm.stringWidth(monthText) / 2, dayY + fm.getHeight());
		}
	}

	private void drawBars(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<AlcoholChartData> data,
		int days,
		double max
	) {
		int barWidth = Math.max(
			MIN_BAR_WIDTH,
			(int) ((double) (plotRight - plotLeft) / Math.max(1, days) * BAR_WIDTH_FACTOR)
		);
		g2.setColor(COLOR_ALCOHOL);
		for (AlcoholChartData entry : data) {
			if (entry.liters() <= 0) {
				continue;
			}
			int x = xForDay(plotLeft, plotRight, entry.day(), days);
			int y = yForLiters(entry.liters(), plotTop, plotBottom, max);
			g2.fillRect(x - barWidth / 2, y, barWidth, plotBottom - y);
		}
	}

	/** Draws the dotted linear trend line of the pure-alcohol amount across the plotted day range. */
	private void drawTrend(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<AlcoholChartData> data,
		int days,
		double max
	) {
		if (data.size() < 2) {
			return;
		}
		double[] xs = new double[data.size()];
		double[] ys = new double[data.size()];
		int minDay = Integer.MAX_VALUE;
		int maxDay = Integer.MIN_VALUE;
		for (int i = 0; i < data.size(); i++) {
			int day = data.get(i).day();
			xs[i] = day;
			ys[i] = data.get(i).liters();
			minDay = Math.min(minDay, day);
			maxDay = Math.max(maxDay, day);
		}
		if (minDay == maxDay) {
			return;
		}
		Optional<TrendLine> trend = TrendLine.fit(xs, ys);
		if (trend.isEmpty()) {
			return;
		}
		TrendLine line = trend.get();
		int x1 = xForDay(plotLeft, plotRight, minDay, days);
		int y1 = yForLiters(line.valueAt(minDay), plotTop, plotBottom, max);
		int x2 = xForDay(plotLeft, plotRight, maxDay, days);
		int y2 = yForLiters(line.valueAt(maxDay), plotTop, plotBottom, max);
		g2.setColor(COLOR_ALCOHOL);
		g2.setStroke(TREND_STROKE);
		g2.drawLine(x1, y1, x2, y2);
	}

	private void drawLegend(Graphics2D g2, int x, int y) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(COLOR_ALCOHOL);
		g2.fillRect(x, y + (LEGEND_HEIGHT - swatch) / 2, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString("Pure alcohol (l)", x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
	}

	/** Evenly spaces the day positions 1..{@code days} across the width; a single position is centered. */
	private int xForDay(int plotLeft, int plotRight, int day, int days) {
		if (days <= 1) {
			return (plotLeft + plotRight) / 2;
		}
		int clampedDay = Math.max(1, Math.min(days, day));
		return plotLeft + (int) Math.round((double) (clampedDay - 1) / (days - 1) * (plotRight - plotLeft));
	}

	private int yForLiters(double value, int plotTop, int plotBottom, double max) {
		double clamped = Math.max(0, Math.min(max, value));
		return plotBottom - (int) Math.round(clamped / max * (plotBottom - plotTop));
	}

	/** Rounds a raw step up to the next "nice" value (1, 2, 5 x 10^n) for readable grid lines. */
	private static double niceStep(double rawStep) {
		if (rawStep <= 0) {
			return 1;
		}
		double magnitude = Math.pow(10, Math.floor(Math.log10(rawStep)));
		double normalized = rawStep / magnitude;
		double niceNormalized = normalized <= 1 ? 1 : normalized <= 2 ? 2 : normalized <= 5 ? 5 : 10;
		return niceNormalized * magnitude;
	}

	private static String formatValue(double value) {
		return String.format(Locale.ENGLISH, "%.2f", value);
	}
}
