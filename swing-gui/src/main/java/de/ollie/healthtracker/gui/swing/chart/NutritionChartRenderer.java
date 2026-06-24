package de.ollie.healthtracker.gui.swing.chart;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

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

/**
 * Draws the nutrition chart (grid, axes, month labels, the three series and the legend) onto a {@link Graphics2D}. Holds
 * all layout and styling constants and keeps no mutable state, so a single instance can be reused for every repaint.
 */
class NutritionChartRenderer {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 31;
	private static final int VALUE_GRID_STEP = 5;

	private static final int PADDING_LEFT = 40;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 30;
	private static final int LEGEND_HEIGHT = 24;
	private static final int MARKER_RADIUS = 3;
	private static final int LABEL_GAP = 2;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);
	private static final Stroke SERIES_STROKE = new BasicStroke(2f);

	private static final List<Series> SERIES = List.of(
		new Series("Meat", new Color(0xC0392B), NutritionChartData::meat),
		new Series("Pescetarian", new Color(0x2980B9), NutritionChartData::pescetarian),
		new Series("Veggie", new Color(0x27AE60), NutritionChartData::veggie)
	);

	void render(Graphics2D g2, int width, int height, List<NutritionChartData> data) {
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
		SERIES.forEach(series -> drawSeries(g2, area, data, series));
		drawLegend(g2, area.left(), height - LEGEND_HEIGHT);
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

	/** One vertical grid line and a centered month label per data point. */
	private void drawMonthGridAndLabels(Graphics2D g2, PlotArea area, List<NutritionChartData> data) {
		FontMetrics fm = g2.getFontMetrics();
		for (int i = 0; i < data.size(); i++) {
			int x = area.xFor(i, data.size());
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, area.top(), x, area.bottom());
			g2.setColor(COLOR_AXIS);
			String label = monthLabel(data.get(i).month());
			g2.drawString(label, x - fm.stringWidth(label) / 2, area.bottom() + fm.getAscent() + LABEL_GAP);
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

	private void drawLegend(Graphics2D g2, int x, int y) {
		for (Series series : SERIES) {
			x = drawLegendEntry(g2, x, y, series);
		}
	}

	/** Draws a single legend swatch with its label and returns the x position for the next entry. */
	private int drawLegendEntry(Graphics2D g2, int x, int y, Series series) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(series.color());
		g2.fillRect(x, y + (LEGEND_HEIGHT - swatch) / 2, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString(series.label(), x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(series.label()) + 3 * HGAP;
	}

	private static int clampValue(int value) {
		return Math.max(MIN_VALUE, Math.min(MAX_VALUE, value));
	}

	private static String monthLabel(int month) {
		if (month < 1 || month > 12) {
			return Integer.toString(month);
		}
		return Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
	}
}
