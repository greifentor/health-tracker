package de.ollie.healthtracker.gui.swing.chart.nutrition;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.gui.swing.chart.PlotArea;
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

/**
 * Draws the nutrition chart (grid, axes, month labels, the three series and the legend) onto a {@link Graphics2D}. Holds
 * all layout and styling constants and keeps no mutable state, so a single instance can be reused for every repaint.
 */
class NutritionChartRenderer {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 31;
	private static final int VALUE_GRID_STEP = 5;
	private static final int TARGET_MONTH_LABELS = 12;

	private static final int PADDING_LEFT = 40;
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
		new Series("Meat", new Color(0xC0392B), NutritionChartData::meat),
		new Series("Pescetarian", new Color(0x2980B9), NutritionChartData::pescetarian),
		new Series("Veggie", new Color(0x27AE60), NutritionChartData::veggie)
	);

	/** The labels of the selectable series, in legend order, for building the visibility toggles. */
	static final List<String> SERIES_LABELS = SERIES.stream().map(Series::label).toList();

	void render(Graphics2D g2, int width, int height, List<NutritionChartData> data, Set<String> hiddenSeries) {
		PlotArea area = new PlotArea(
			PADDING_LEFT,
			width - PADDING_RIGHT,
			PADDING_TOP,
			height - PADDING_BOTTOM - LEGEND_HEIGHT,
			MIN_VALUE,
			MAX_VALUE
		);
		if (!area.isValid()) {
			return;
		}
		drawGridAndAxes(g2, area, data);
		SERIES.forEach(series -> {
			if (!hiddenSeries.contains(series.label())) {
				drawSeries(g2, area, data, series);
				drawTrend(g2, area, data, series);
			}
		});
		drawLegend(g2, area.left(), height - LEGEND_HEIGHT, hiddenSeries);
	}

	private void drawGridAndAxes(Graphics2D g2, PlotArea area, List<NutritionChartData> data) {
		drawValueGridAndLabels(g2, area);
		drawMonthGridAndLabels(g2, area, data);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(area.left(), area.top(), area.left(), area.bottom());
		g2.drawLine(area.left(), area.bottom(), area.right(), area.bottom());
	}

	/** Horizontal grid lines and the right-aligned y-axis value labels (0..31). */
	private void drawValueGridAndLabels(Graphics2D g2, PlotArea area) {
		FontMetrics fm = g2.getFontMetrics();
		for (int value = MIN_VALUE; value <= MAX_VALUE; value += VALUE_GRID_STEP) {
			int y = area.yFor(value);
			g2.setColor(COLOR_GRID);
			g2.drawLine(area.left(), y, area.right(), y);
			g2.setColor(COLOR_AXIS);
			String label = Integer.toString(value);
			g2.drawString(label, area.left() - HGAP - fm.stringWidth(label), y + fm.getAscent() / 2 - 1);
		}
	}

	/**
	 * One vertical grid line per data point and a centered month label at a step that keeps at most
	 * {@value #TARGET_MONTH_LABELS} labels - so the labels do not overlap when many months are shown. The last month is
	 * always labelled.
	 */
	private void drawMonthGridAndLabels(Graphics2D g2, PlotArea area, List<NutritionChartData> data) {
		FontMetrics fm = g2.getFontMetrics();
		int labelStep = Math.max(1, (int) Math.ceil((double) data.size() / TARGET_MONTH_LABELS));
		for (int i = 0; i < data.size(); i++) {
			int x = area.xFor(i, data.size());
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, area.top(), x, area.bottom());
			if (i % labelStep != 0 && i != data.size() - 1) {
				continue;
			}
			g2.setColor(COLOR_AXIS);
			String monthText = monthLabel(data.get(i).month());
			String yearText = yearLabel(data.get(i).year());
			int monthY = area.bottom() + fm.getAscent() + LABEL_GAP;
			g2.drawString(monthText, x - fm.stringWidth(monthText) / 2, monthY);
			g2.drawString(yearText, x - fm.stringWidth(yearText) / 2, monthY + fm.getHeight());
		}
	}

	private void drawSeries(Graphics2D g2, PlotArea area, List<NutritionChartData> data, Series series) {
		List<Point> points = computePoints(area, data, series);
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
	private void drawTrend(Graphics2D g2, PlotArea area, List<NutritionChartData> data, Series series) {
		if (data.size() < 2) {
			return;
		}
		double[] xs = new double[data.size()];
		double[] ys = new double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			xs[i] = i;
			ys[i] = series.valueAccessor().applyAsInt(data.get(i));
		}
		Optional<TrendLine> trend = TrendLine.fit(xs, ys);
		if (trend.isEmpty()) {
			return;
		}
		TrendLine line = trend.get();
		int lastIndex = data.size() - 1;
		int x1 = area.xFor(0, data.size());
		int y1 = area.yFor(clampValue((int) Math.round(line.valueAt(0))));
		int x2 = area.xFor(lastIndex, data.size());
		int y2 = area.yFor(clampValue((int) Math.round(line.valueAt(lastIndex))));
		g2.setColor(series.color());
		g2.setStroke(TREND_STROKE);
		g2.drawLine(x1, y1, x2, y2);
	}

	/** Maps each data point of the series to its pixel position within the plot area. */
	private List<Point> computePoints(PlotArea area, List<NutritionChartData> data, Series series) {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			int x = area.xFor(i, data.size());
			int y = area.yFor(clampValue(series.valueAccessor().applyAsInt(data.get(i))));
			points.add(new Point(x, y));
		}
		return points;
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
		g2.drawString(series.label(), x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(series.label()) + 3 * HGAP;
	}

	private static int clampValue(int value) {
		return Math.max(MIN_VALUE, Math.min(MAX_VALUE, value));
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
}
