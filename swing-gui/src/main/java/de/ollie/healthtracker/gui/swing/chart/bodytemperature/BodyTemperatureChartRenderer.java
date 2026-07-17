package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Draws the body temperature chart onto a {@link Graphics2D}: the value grid (y-axis, configurable celsius range), the
 * day grid (x-axis), the axes, the temperature line with markers and the legend. Holds no mutable state, so a single
 * instance can be reused for every repaint.
 */
class BodyTemperatureChartRenderer {

	private static final int PADDING_LEFT = 48;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 30;
	private static final int LEGEND_HEIGHT = 24;
	private static final int MARKER_RADIUS = 3;
	private static final int LABEL_GAP = 2;
	private static final int DAY_LABEL_STEP = 5;
	private static final int TARGET_VALUE_GRID_LINES = 6;
	private static final double REFERENCE_CELSIUS_LOWER = 36.5;
	private static final double REFERENCE_CELSIUS_UPPER = 37.4;
	private static final int STATUS_BAR_TOP = 5;
	private static final int STATUS_BAR_HEIGHT = 8;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);
	private static final Color COLOR_TEMPERATURE = new Color(0xC0392B);
	private static final Color COLOR_REFERENCE = new Color(0x27AE60);
	private static final Color COLOR_STATUS_BACKGROUND = new Color(0xE0E0E0);
	private static final Color COLOR_STATUS_BELOW = new Color(0x2980B9);
	private static final Color COLOR_STATUS_NORMAL = new Color(0x27AE60);
	private static final Color COLOR_STATUS_ABOVE = new Color(0xC0392B);
	private static final Stroke SERIES_STROKE = new BasicStroke(2f);
	private static final Stroke REFERENCE_STROKE = new BasicStroke(1f);

	void render(
		Graphics2D g2,
		int width,
		int height,
		List<BodyTemperatureChartData> data,
		int days,
		LocalDate windowEndDate,
		double minCelsius,
		double maxCelsius
	) {
		int plotLeft = PADDING_LEFT;
		int plotRight = width - PADDING_RIGHT;
		int plotTop = PADDING_TOP;
		int plotBottom = height - PADDING_BOTTOM - LEGEND_HEIGHT;
		if (plotRight <= plotLeft || plotBottom <= plotTop || maxCelsius <= minCelsius) {
			return;
		}
		drawValueGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, minCelsius, maxCelsius);
		drawDayGridAndLabels(g2, plotLeft, plotRight, plotTop, plotBottom, days, windowEndDate);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(plotLeft, plotTop, plotLeft, plotBottom);
		g2.drawLine(plotLeft, plotBottom, plotRight, plotBottom);
		drawReferenceLine(g2, plotLeft, plotRight, plotTop, plotBottom, minCelsius, maxCelsius);
		drawStatusBar(g2, plotLeft, plotRight, data, days);
		drawTemperatureLine(g2, plotLeft, plotRight, plotTop, plotBottom, data, days, minCelsius, maxCelsius);
		drawLegend(g2, plotLeft, height - LEGEND_HEIGHT);
	}

	/**
	 * Draws the thin status bar above the plot: a per-day segment colored by the {@link BodyTemperatureStatus} of that
	 * day - green when the (averaged) temperature is within the regular range, blue when it is below the minimum, red
	 * when it is above the maximum. Days without data stay on the gray background.
	 */
	private void drawStatusBar(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		List<BodyTemperatureChartData> data,
		int days
	) {
		g2.setColor(COLOR_STATUS_BACKGROUND);
		g2.fillRect(plotLeft, STATUS_BAR_TOP, plotRight - plotLeft, STATUS_BAR_HEIGHT);
		double spacing = days > 1 ? (double) (plotRight - plotLeft) / (days - 1) : (plotRight - plotLeft);
		int half = Math.max(1, (int) Math.round(spacing / 2));
		for (BodyTemperatureChartData entry : data) {
			int center = xForDay(plotLeft, plotRight, entry.day(), days);
			int left = Math.max(plotLeft, center - half);
			int right = Math.min(plotRight, center + half);
			g2.setColor(statusColor(entry.status()));
			g2.fillRect(left, STATUS_BAR_TOP, right - left, STATUS_BAR_HEIGHT);
		}
	}

	private static Color statusColor(BodyTemperatureStatus status) {
		if (status == null) {
			return COLOR_STATUS_NORMAL;
		}
		return switch (status) {
			case BELOW -> COLOR_STATUS_BELOW;
			case ABOVE -> COLOR_STATUS_ABOVE;
			default -> COLOR_STATUS_NORMAL;
		};
	}

	/** Horizontal grid lines and the right-aligned y-axis value labels at "nice" steps within [min, max]. */
	private void drawValueGridAndLabels(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		double min,
		double max
	) {
		FontMetrics fm = g2.getFontMetrics();
		double step = niceStep((max - min) / TARGET_VALUE_GRID_LINES);
		for (double value = Math.ceil(min / step) * step; value <= max + 1e-9; value += step) {
			int y = yForTemperature(value, plotTop, plotBottom, min, max);
			g2.setColor(COLOR_GRID);
			g2.drawLine(plotLeft, y, plotRight, y);
			g2.setColor(COLOR_AXIS);
			String label = formatValue(value);
			g2.drawString(label, plotLeft - HGAP - fm.stringWidth(label), y + fm.getAscent() / 2 - 1);
		}
	}

	/**
	 * A vertical grid line and a centered day-of-month label for the first day position, every
	 * {@value #DAY_LABEL_STEP}th position and the last. The label is derived from {@code windowEndDate}, which is the
	 * calendar date of the last (right-most) position.
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
			String label = Integer.toString(windowEndDate.minusDays((long) days - position).getDayOfMonth());
			g2.drawString(label, x - fm.stringWidth(label) / 2, plotBottom + fm.getAscent() + LABEL_GAP);
		}
	}

	private void drawTemperatureLine(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		List<BodyTemperatureChartData> data,
		int daysInMonth,
		double min,
		double max
	) {
		List<Point> points = new ArrayList<>();
		for (BodyTemperatureChartData entry : data) {
			int x = xForDay(plotLeft, plotRight, entry.day(), daysInMonth);
			int y = yForTemperature(entry.celsius(), plotTop, plotBottom, min, max);
			points.add(new Point(x, y));
		}
		if (points.isEmpty()) {
			return;
		}
		g2.setColor(COLOR_TEMPERATURE);
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

	/**
	 * Two thin horizontal green reference lines marking the normal body temperature band at
	 * {@value #REFERENCE_CELSIUS_LOWER} °C and {@value #REFERENCE_CELSIUS_UPPER} °C. Each line is only drawn when it falls
	 * within [min, max].
	 */
	private void drawReferenceLine(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		double min,
		double max
	) {
		g2.setColor(COLOR_REFERENCE);
		g2.setStroke(REFERENCE_STROKE);
		drawReferenceValue(g2, plotLeft, plotRight, plotTop, plotBottom, min, max, REFERENCE_CELSIUS_LOWER);
		drawReferenceValue(g2, plotLeft, plotRight, plotTop, plotBottom, min, max, REFERENCE_CELSIUS_UPPER);
	}

	private void drawReferenceValue(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		double min,
		double max,
		double value
	) {
		if (value < min || value > max) {
			return;
		}
		int y = yForTemperature(value, plotTop, plotBottom, min, max);
		g2.drawLine(plotLeft, y, plotRight, y);
	}

	private void drawLegend(Graphics2D g2, int x, int y) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		g2.setColor(COLOR_TEMPERATURE);
		g2.fillRect(x, y + (LEGEND_HEIGHT - swatch) / 2, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString("Body Temperature (°C)", x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
	}

	/** Evenly spaces the days 1..{@code daysInMonth} across the width; a single day is centered. */
	private int xForDay(int plotLeft, int plotRight, int day, int daysInMonth) {
		if (daysInMonth <= 1) {
			return (plotLeft + plotRight) / 2;
		}
		int clampedDay = Math.max(1, Math.min(daysInMonth, day));
		return plotLeft + (int) Math.round((double) (clampedDay - 1) / (daysInMonth - 1) * (plotRight - plotLeft));
	}

	private int yForTemperature(double value, int plotTop, int plotBottom, double min, double max) {
		double clamped = Math.max(min, Math.min(max, value));
		return plotBottom - (int) Math.round((clamped - min) / (max - min) * (plotBottom - plotTop));
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
		if (value == Math.rint(value)) {
			return Integer.toString((int) Math.rint(value));
		}
		return String.format(Locale.ENGLISH, "%.1f", value);
	}
}
