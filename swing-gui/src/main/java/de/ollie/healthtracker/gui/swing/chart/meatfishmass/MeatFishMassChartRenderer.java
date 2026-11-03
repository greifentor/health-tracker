package de.ollie.healthtracker.gui.swing.chart.meatfishmass;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.gui.swing.chart.TrendLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.ToDoubleFunction;

/**
 * Draws the meat and fish mass chart (grid, axes, month labels, the two kilogram series and the legend) onto a
 * {@link Graphics2D}. The y-axis is scaled to a given maximum (kilograms). Holds no mutable state, so a single instance
 * can be reused for every repaint.
 */
class MeatFishMassChartRenderer {

	private static final int TARGET_MONTH_LABELS = 12;
	private static final int TARGET_VALUE_GRID_LINES = 6;

	private static final int PADDING_LEFT = 48;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 44;
	private static final int LEGEND_HEIGHT = 24;
	private static final int MARKER_RADIUS = 3;
	private static final int LABEL_GAP = 2;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);
	private static final Color COLOR_DISABLED = new Color(0xB0B0B0);
	private static final Stroke SERIES_STROKE = new BasicStroke(2f);
	private static final Stroke TREND_STROKE = new BasicStroke(
		1.5f,
		BasicStroke.CAP_BUTT,
		BasicStroke.JOIN_MITER,
		1f,
		new float[] { 2f, 3f },
		0f
	);

	private static final List<Series> SERIES = List.of(
		new Series("Meat", new Color(0xC0392B), MeatFishMassChartData::meatKilograms),
		new Series("Fish", new Color(0x2980B9), MeatFishMassChartData::fishKilograms)
	);

	/** The labels of the selectable series, in legend order, for building the visibility toggles. */
	static final List<String> SERIES_LABELS = SERIES.stream().map(Series::label).toList();

	void render(
		Graphics2D g2,
		int width,
		int height,
		List<MeatFishMassChartData> data,
		Set<String> hiddenSeries,
		double maxKilograms
	) {
		int plotLeft = PADDING_LEFT;
		int plotRight = width - PADDING_RIGHT;
		int plotTop = PADDING_TOP;
		int plotBottom = height - PADDING_BOTTOM - LEGEND_HEIGHT;
		if (plotRight <= plotLeft || plotBottom <= plotTop || maxKilograms <= 0) {
			return;
		}
		drawValueGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, maxKilograms);
		drawMonthGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, data);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(plotLeft, plotTop, plotLeft, plotBottom);
		g2.drawLine(plotLeft, plotBottom, plotRight, plotBottom);
		SERIES.forEach(series -> {
			if (!hiddenSeries.contains(series.label())) {
				drawSeries(g2, plotLeft, plotRight, plotTop, plotBottom, data, maxKilograms, series);
				drawTrend(g2, plotLeft, plotRight, plotTop, plotBottom, data, maxKilograms, series);
			}
		});
		drawLegend(g2, plotLeft, height - LEGEND_HEIGHT, hiddenSeries);
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
			int y = yForKilograms(value, plotTop, plotBottom, max);
			g2.setColor(COLOR_GRID);
			g2.drawLine(plotLeft, y, plotRight, y);
			g2.setColor(COLOR_AXIS);
			String label = formatValue(value);
			g2.drawString(label, plotLeft - HGAP - fm.stringWidth(label), y + fm.getAscent() / 2 - 1);
		}
	}

	/**
	 * One vertical grid line per data point and a centered two-line month/year label at a step that keeps at most
	 * {@value #TARGET_MONTH_LABELS} labels - so the labels do not overlap when many months are shown. The last month is
	 * always labelled.
	 */
	private void drawMonthGridAndLabels(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<MeatFishMassChartData> data
	) {
		FontMetrics fm = g2.getFontMetrics();
		int labelStep = Math.max(1, (int) Math.ceil((double) data.size() / TARGET_MONTH_LABELS));
		for (int i = 0; i < data.size(); i++) {
			int x = xForIndex(plotLeft, plotRight, i, data.size());
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, plotTop, x, plotBottom);
			if (i % labelStep != 0 && i != data.size() - 1) {
				continue;
			}
			g2.setColor(COLOR_AXIS);
			String monthText = monthLabel(data.get(i).month());
			String yearText = yearLabel(data.get(i).year());
			int monthY = plotBottom + fm.getAscent() + LABEL_GAP;
			g2.drawString(monthText, x - fm.stringWidth(monthText) / 2, monthY);
			g2.drawString(yearText, x - fm.stringWidth(yearText) / 2, monthY + fm.getHeight());
		}
	}

	private void drawSeries(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<MeatFishMassChartData> data,
		double max,
		Series series
	) {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			int x = xForIndex(plotLeft, plotRight, i, data.size());
			int y = yForKilograms(series.valueAccessor().applyAsDouble(data.get(i)), plotTop, plotBottom, max);
			points.add(new Point(x, y));
		}
		if (points.isEmpty()) {
			return;
		}
		g2.setColor(series.color());
		g2.setStroke(SERIES_STROKE);
		for (int i = 1; i < points.size(); i++) {
			Point from = points.get(i - 1);
			Point to = points.get(i);
			g2.drawLine(from.x, from.y, to.x, to.y);
		}
		for (Point point : points) {
			g2.fillOval(point.x - MARKER_RADIUS, point.y - MARKER_RADIUS, 2 * MARKER_RADIUS, 2 * MARKER_RADIUS);
		}
	}

	/** Draws the dotted linear trend line of the series (in its color) across the months. */
	private void drawTrend(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<MeatFishMassChartData> data,
		double max,
		Series series
	) {
		if (data.size() < 2) {
			return;
		}
		double[] xs = new double[data.size()];
		double[] ys = new double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			xs[i] = i;
			ys[i] = series.valueAccessor().applyAsDouble(data.get(i));
		}
		Optional<TrendLine> trend = TrendLine.fit(xs, ys);
		if (trend.isEmpty()) {
			return;
		}
		TrendLine line = trend.get();
		int lastIndex = data.size() - 1;
		int x1 = xForIndex(plotLeft, plotRight, 0, data.size());
		int y1 = yForKilograms(line.valueAt(0), plotTop, plotBottom, max);
		int x2 = xForIndex(plotLeft, plotRight, lastIndex, data.size());
		int y2 = yForKilograms(line.valueAt(lastIndex), plotTop, plotBottom, max);
		g2.setColor(series.color());
		g2.setStroke(TREND_STROKE);
		g2.drawLine(x1, y1, x2, y2);
	}

	private void drawLegend(Graphics2D g2, int x, int y, Set<String> hiddenSeries) {
		for (Series series : SERIES) {
			x = drawLegendEntry(g2, x, y, series, hiddenSeries.contains(series.label()));
		}
	}

	/** Draws a single legend swatch with its label (greyed out when the series is hidden) and returns the x position for the next entry. */
	private int drawLegendEntry(Graphics2D g2, int x, int y, Series series, boolean hidden) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(hidden ? COLOR_DISABLED : series.color());
		g2.fillRect(x, y + (LEGEND_HEIGHT - swatch) / 2, swatch, swatch);
		g2.setColor(hidden ? COLOR_DISABLED : COLOR_AXIS);
		g2.drawString(series.label() + " (kg)", x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(series.label() + " (kg)") + 3 * HGAP;
	}

	/** Evenly spaces {@code count} columns across the width; a single column is centered. */
	private int xForIndex(int plotLeft, int plotRight, int index, int count) {
		if (count <= 1) {
			return (plotLeft + plotRight) / 2;
		}
		return plotLeft + (int) Math.round((double) index / (count - 1) * (plotRight - plotLeft));
	}

	private int yForKilograms(double value, int plotTop, int plotBottom, double max) {
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

	/** Short month name, e.g. {@code "Oct"}; shown on the upper of the two x-axis label lines. */
	private static String monthLabel(int month) {
		if (month < 1 || month > 12) {
			return Integer.toString(month);
		}
		return Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
	}

	/** Two-digit year, e.g. {@code "25"}; shown below the month so months of different years stay distinguishable. */
	private static String yearLabel(int year) {
		return String.format(Locale.ENGLISH, "%02d", Math.floorMod(year, 100));
	}

	private record Series(String label, Color color, ToDoubleFunction<MeatFishMassChartData> valueAccessor) {}
}
