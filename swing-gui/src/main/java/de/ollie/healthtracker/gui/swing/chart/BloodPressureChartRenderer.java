package de.ollie.healthtracker.gui.swing.chart;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Draws the blood pressure chart (grid, axes, day labels, the three series and the legend) onto a {@link Graphics2D}.
 * The x-axis covers the days 1..{@code daysInMonth}, the y-axis the value range 0..{@value #MAX_VALUE} (mmHg / pulse).
 * Holds all layout and styling constants and keeps no mutable state, so a single instance can be reused.
 */
class BloodPressureChartRenderer {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 220;
	private static final int VALUE_GRID_STEP = 20;
	private static final int DAY_LABEL_STEP = 5;

	private static final int PADDING_LEFT = 40;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 30;
	private static final int LEGEND_HEIGHT = 24;
	private static final int MARKER_RADIUS = 3;
	private static final int LABEL_GAP = 2;
	private static final int STATUS_BAR_TOP = 5;
	private static final int STATUS_BAR_HEIGHT = 8;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);
	private static final Stroke SERIES_STROKE = new BasicStroke(2f);

	private static final Color COLOR_STATUS_GREEN = new Color(0x27AE60);
	private static final Color COLOR_STATUS_YELLOW = new Color(0xF1C40F);
	private static final Color COLOR_STATUS_ORANGE = new Color(0xE67E22);
	private static final Color COLOR_STATUS_RED = new Color(0xC0392B);

	private static final List<BpSeries> SERIES = List.of(
		new BpSeries("Systolic", new Color(0xC0392B), BloodPressureChartData::systolic),
		new BpSeries("Diastolic", new Color(0x2980B9), BloodPressureChartData::diastolic),
		new BpSeries("Pulse", new Color(0x8E44AD), BloodPressureChartData::pulse)
	);

	void render(Graphics2D g2, int width, int height, List<BloodPressureChartData> data, int daysInMonth) {
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
		drawGridAndAxes(g2, area, daysInMonth);
		drawStatusBar(g2, area, data, daysInMonth);
		SERIES.forEach(series -> drawSeries(g2, area, data, daysInMonth, series));
		drawLegend(g2, area.left(), height - LEGEND_HEIGHT);
	}

	/**
	 * Draws a thin bar above the plot whose per-day segments are colored by the WHO classification of that day: green
	 * for optimal/normal, yellow for high-normal, orange for hypertension grade 1, red otherwise.
	 */
	private void drawStatusBar(Graphics2D g2, PlotArea area, List<BloodPressureChartData> data, int daysInMonth) {
		g2.setColor(COLOR_GRID);
		g2.fillRect(area.left(), STATUS_BAR_TOP, area.width(), STATUS_BAR_HEIGHT);
		double spacing = daysInMonth > 1 ? (double) area.width() / (daysInMonth - 1) : area.width();
		int half = Math.max(1, (int) Math.round(spacing / 2));
		for (BloodPressureChartData entry : data) {
			int center = xForDay(area, entry.day(), daysInMonth);
			int left = Math.max(area.left(), center - half);
			int right = Math.min(area.right(), center + half);
			g2.setColor(statusColor(entry.status()));
			g2.fillRect(left, STATUS_BAR_TOP, right - left, STATUS_BAR_HEIGHT);
		}
	}

	private static Color statusColor(WhoBloodPressureClassification status) {
		if (status == null) {
			return COLOR_STATUS_RED;
		}
		return switch (status) {
			case OPTIMAL, NORMAL -> COLOR_STATUS_GREEN;
			case HIGH_NORMAL -> COLOR_STATUS_YELLOW;
			case HYPERTENSION_GRADE_1 -> COLOR_STATUS_ORANGE;
			default -> COLOR_STATUS_RED;
		};
	}

	private void drawGridAndAxes(Graphics2D g2, PlotArea area, int daysInMonth) {
		drawValueGridAndLabels(g2, area);
		drawDayGridAndLabels(g2, area, daysInMonth);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(area.left(), area.top(), area.left(), area.bottom());
		g2.drawLine(area.left(), area.bottom(), area.right(), area.bottom());
	}

	/** Horizontal grid lines and the right-aligned y-axis value labels (0..{@value #MAX_VALUE}). */
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

	/** A vertical grid line and a centered day label for the first day, every {@value #DAY_LABEL_STEP}th day and the last. */
	private void drawDayGridAndLabels(Graphics2D g2, PlotArea area, int daysInMonth) {
		FontMetrics fm = g2.getFontMetrics();
		for (int day = 1; day <= daysInMonth; day++) {
			if (day != 1 && day != daysInMonth && day % DAY_LABEL_STEP != 0) {
				continue;
			}
			int x = xForDay(area, day, daysInMonth);
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, area.top(), x, area.bottom());
			g2.setColor(COLOR_AXIS);
			String label = Integer.toString(day);
			g2.drawString(label, x - fm.stringWidth(label) / 2, area.bottom() + fm.getAscent() + LABEL_GAP);
		}
	}

	private void drawSeries(
		Graphics2D g2,
		PlotArea area,
		List<BloodPressureChartData> data,
		int daysInMonth,
		BpSeries series
	) {
		List<Point> points = computePoints(area, data, daysInMonth, series);
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
	private List<Point> computePoints(
		PlotArea area,
		List<BloodPressureChartData> data,
		int daysInMonth,
		BpSeries series
	) {
		List<Point> points = new ArrayList<>();
		for (BloodPressureChartData entry : data) {
			int x = xForDay(area, entry.day(), daysInMonth);
			int y = area.yFor(clampValue(series.valueAccessor().applyAsInt(entry)));
			points.add(new Point(x, y));
		}
		return points;
	}

	private void drawLegend(Graphics2D g2, int x, int y) {
		for (BpSeries series : SERIES) {
			x = drawLegendEntry(g2, x, y, series);
		}
	}

	/** Draws a single legend swatch with its label and returns the x position for the next entry. */
	private int drawLegendEntry(Graphics2D g2, int x, int y, BpSeries series) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(series.color());
		g2.fillRect(x, y + (LEGEND_HEIGHT - swatch) / 2, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString(series.label(), x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(series.label()) + 3 * HGAP;
	}

	/** Evenly spaces the days 1..{@code daysInMonth} across the width; a single day is centered. */
	private int xForDay(PlotArea area, int day, int daysInMonth) {
		if (daysInMonth <= 1) {
			return (area.left() + area.right()) / 2;
		}
		int clampedDay = Math.max(1, Math.min(daysInMonth, day));
		return area.left() + (int) Math.round((double) (clampedDay - 1) / (daysInMonth - 1) * area.width());
	}

	private static int clampValue(int value) {
		return Math.max(MIN_VALUE, Math.min(MAX_VALUE, value));
	}

	/** One graph: its legend label, color and the value it reads from a {@link BloodPressureChartData}. */
	private record BpSeries(String label, Color color, ToIntFunction<BloodPressureChartData> valueAccessor) {}
}
