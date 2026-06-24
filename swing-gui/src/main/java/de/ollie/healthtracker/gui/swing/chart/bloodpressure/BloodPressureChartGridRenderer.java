package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import de.ollie.healthtracker.gui.swing.chart.PlotArea;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Draws the static raster of the blood pressure chart: the horizontal value grid with its y-axis labels, the vertical
 * day grid with its x-axis labels and the two axis lines. Holds no mutable state.
 */
class BloodPressureChartGridRenderer {

	private static final int VALUE_GRID_STEP = 20;
	private static final int DAY_LABEL_STEP = 5;
	private static final int LABEL_GAP = 2;

	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);

	void draw(Graphics2D g2, PlotArea area, int daysInMonth) {
		drawValueGridAndLabels(g2, area);
		drawDayGridAndLabels(g2, area, daysInMonth);
		g2.setColor(COLOR_AXIS);
		g2.drawLine(area.left(), area.top(), area.left(), area.bottom());
		g2.drawLine(area.left(), area.bottom(), area.right(), area.bottom());
	}

	/** Horizontal grid lines and the right-aligned y-axis value labels. */
	private void drawValueGridAndLabels(Graphics2D g2, PlotArea area) {
		FontMetrics fm = g2.getFontMetrics();
		for (int value = area.minValue(); value <= area.maxValue(); value += VALUE_GRID_STEP) {
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
			int x = area.xFor(day - 1, daysInMonth);
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, area.top(), x, area.bottom());
			g2.setColor(COLOR_AXIS);
			String label = Integer.toString(day);
			g2.drawString(label, x - fm.stringWidth(label) / 2, area.bottom() + fm.getAscent() + LABEL_GAP);
		}
	}
}
