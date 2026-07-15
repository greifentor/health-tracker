package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JComponent;

/**
 * A Swing component that displays the daily body temperature measurements of a configurable number of days (ending on a
 * window end date) as a single line graph. The x-axis covers those days, the y-axis the temperature in °C over a
 * configurable value range (default {@value #DEFAULT_MIN_CELSIUS}-{@value #DEFAULT_MAX_CELSIUS}). All drawing is
 * delegated to a {@link BodyTemperatureChartRenderer}.
 */
public class BodyTemperatureChartJComponent extends JComponent {

	private static final int DEFAULT_DAYS = 31;
	private static final double DEFAULT_MIN_CELSIUS = 35.0;
	private static final double DEFAULT_MAX_CELSIUS = 42.0;

	private final BodyTemperatureChartRenderer renderer = new BodyTemperatureChartRenderer();
	private List<BodyTemperatureChartData> data = new ArrayList<>();
	private int days = DEFAULT_DAYS;
	private LocalDate windowEndDate = LocalDate.now();
	private double minCelsius = DEFAULT_MIN_CELSIUS;
	private double maxCelsius = DEFAULT_MAX_CELSIUS;

	public BodyTemperatureChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data (copied and sorted by day so the line is drawn left to right), the number of days
	 * shown on the x-axis and the window end date (the calendar date of the last, right-most day position) used to
	 * derive the day-of-month axis labels, then repaints. The passed list is not modified.
	 */
	public void setData(List<BodyTemperatureChartData> data, int days, LocalDate windowEndDate) {
		List<BodyTemperatureChartData> entries = data == null ? new ArrayList<>() : new ArrayList<>(data);
		entries.sort(Comparator.comparingInt(BodyTemperatureChartData::day));
		this.data = entries;
		this.days = Math.max(1, days);
		this.windowEndDate = windowEndDate == null ? LocalDate.now() : windowEndDate;
		repaint();
	}

	/** Configures the value range (°C) shown on the y-axis and repaints. */
	public void setValueRange(double minCelsius, double maxCelsius) {
		this.minCelsius = minCelsius;
		this.maxCelsius = maxCelsius;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		try {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (isOpaque()) {
				g2.setColor(getBackground());
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
			renderer.render(g2, getWidth(), getHeight(), data, days, windowEndDate, minCelsius, maxCelsius);
		} finally {
			g2.dispose();
		}
	}
}
