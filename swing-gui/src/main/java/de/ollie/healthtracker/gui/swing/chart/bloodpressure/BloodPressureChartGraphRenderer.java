package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.gui.swing.chart.PlotArea;
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
 * Draws the graphs that result from the chart data: one line with markers per series (systolic, diastolic, pulse) and
 * the matching legend. Holds no mutable state, so a single instance can be reused for every repaint.
 */
class BloodPressureChartGraphRenderer {

	private static final int MARKER_RADIUS = 3;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Stroke SERIES_STROKE = new BasicStroke(2f);

	private static final List<BpSeries> SERIES = List.of(
		new BpSeries("Systolic", new Color(0xC0392B), BloodPressureChartData::systolic),
		new BpSeries("Diastolic", new Color(0x2980B9), BloodPressureChartData::diastolic),
		new BpSeries("Pulse", new Color(0x8E44AD), BloodPressureChartData::pulse)
	);

	void draw(
		Graphics2D g2,
		PlotArea area,
		List<BloodPressureChartData> data,
		int daysInMonth,
		int legendTop,
		int legendHeight
	) {
		SERIES.forEach(series -> drawSeries(g2, area, data, daysInMonth, series));
		drawLegend(g2, area.left(), legendTop, legendHeight);
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
			int x = area.xFor(clampDay(entry.day(), daysInMonth) - 1, daysInMonth);
			int y = area.yFor(clampValue(area, series.valueAccessor().applyAsInt(entry)));
			points.add(new Point(x, y));
		}
		return points;
	}

	private void drawLegend(Graphics2D g2, int x, int top, int height) {
		for (BpSeries series : SERIES) {
			x = drawLegendEntry(g2, x, top, height, series);
		}
	}

	/** Draws a single legend swatch with its label and returns the x position for the next entry. */
	private int drawLegendEntry(Graphics2D g2, int x, int top, int height, BpSeries series) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(series.color());
		g2.fillRect(x, top + (height - swatch) / 2, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString(series.label(), x + swatch + HGAP, top + (height + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(series.label()) + 3 * HGAP;
	}

	private static int clampDay(int day, int daysInMonth) {
		return Math.max(1, Math.min(daysInMonth, day));
	}

	private static int clampValue(PlotArea area, int value) {
		return Math.max(area.minValue(), Math.min(area.maxValue(), value));
	}

	/** One graph: its legend label, color and the value it reads from a {@link BloodPressureChartData}. */
	private record BpSeries(String label, Color color, ToIntFunction<BloodPressureChartData> valueAccessor) {}
}
