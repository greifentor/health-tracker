package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

import de.ollie.healthtracker.gui.swing.chart.PlotArea;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Orchestrates rendering of the blood pressure chart: it computes the plot area and delegates the raster to a
 * {@link BloodPressureChartGridRenderer}, the status bar to a {@link BloodPressureChartStatusBarRenderer} and the graphs
 * (with legend) to a {@link BloodPressureChartGraphRenderer}. The x-axis covers the days 1..{@code daysInMonth}, the
 * y-axis the value range 0..{@value #MAX_VALUE} (mmHg / pulse).
 */
class BloodPressureChartRenderer {

	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 220;

	private static final int PADDING_LEFT = 40;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 30;
	private static final int LEGEND_HEIGHT = 24;

	private final BloodPressureChartGridRenderer gridRenderer = new BloodPressureChartGridRenderer();
	private final BloodPressureChartStatusBarRenderer statusBarRenderer = new BloodPressureChartStatusBarRenderer();
	private final BloodPressureChartGraphRenderer graphRenderer = new BloodPressureChartGraphRenderer();

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
		gridRenderer.draw(g2, area, daysInMonth);
		statusBarRenderer.draw(g2, area, data, daysInMonth);
		graphRenderer.draw(g2, area, data, daysInMonth, height - LEGEND_HEIGHT, LEGEND_HEIGHT);
	}
}
