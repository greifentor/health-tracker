package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.gui.swing.chart.PlotArea;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Draws the thin status bar above the plot: a per-day segment colored by the WHO classification of that day - green for
 * optimal/normal, yellow for high-normal, orange for hypertension grade 1, red otherwise. Days without data stay on the
 * gray background. Holds no mutable state.
 */
class BloodPressureChartStatusBarRenderer {

	private static final int STATUS_BAR_TOP = 5;
	private static final int STATUS_BAR_HEIGHT = 8;

	private static final Color COLOR_BACKGROUND = new Color(0xE0E0E0);
	private static final Color COLOR_STATUS_GREEN = new Color(0x27AE60);
	private static final Color COLOR_STATUS_YELLOW = new Color(0xF1C40F);
	private static final Color COLOR_STATUS_ORANGE = new Color(0xE67E22);
	private static final Color COLOR_STATUS_RED = new Color(0xC0392B);

	void draw(Graphics2D g2, PlotArea area, List<BloodPressureChartData> data, int daysInMonth) {
		g2.setColor(COLOR_BACKGROUND);
		g2.fillRect(area.left(), STATUS_BAR_TOP, area.width(), STATUS_BAR_HEIGHT);
		double spacing = daysInMonth > 1 ? (double) area.width() / (daysInMonth - 1) : area.width();
		int half = Math.max(1, (int) Math.round(spacing / 2));
		for (BloodPressureChartData entry : data) {
			int center = area.xFor(Math.max(1, Math.min(daysInMonth, entry.day())) - 1, daysInMonth);
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
}
