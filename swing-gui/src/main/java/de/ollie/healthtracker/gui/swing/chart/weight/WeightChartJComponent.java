package de.ollie.healthtracker.gui.swing.chart.weight;

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
 * A Swing component that displays the daily weight measurements of a configurable number of days (ending on a window end
 * date) as a single line graph. The x-axis covers those days, the y-axis the weight in kg over a configurable value
 * range (default {@value #DEFAULT_MIN_WEIGHT}-{@value #DEFAULT_MAX_WEIGHT}). All drawing is delegated to a
 * {@link WeightChartRenderer}.
 */
public class WeightChartJComponent extends JComponent {

	private static final int DEFAULT_DAYS = 31;
	private static final double DEFAULT_MIN_WEIGHT = 70.0;
	private static final double DEFAULT_MAX_WEIGHT = 100.0;

	private final WeightChartRenderer renderer = new WeightChartRenderer();
	private List<WeightChartData> data = new ArrayList<>();
	private int days = DEFAULT_DAYS;
	private LocalDate windowEndDate = LocalDate.now();
	private double minWeight = DEFAULT_MIN_WEIGHT;
	private double maxWeight = DEFAULT_MAX_WEIGHT;

	public WeightChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data (copied and sorted by day so the line is drawn left to right), the number of days
	 * shown on the x-axis and the window end date (the calendar date of the last, right-most day position) used to
	 * derive the day-of-month axis labels, then repaints. The passed list is not modified.
	 */
	public void setData(List<WeightChartData> data, int days, LocalDate windowEndDate) {
		List<WeightChartData> entries = data == null ? new ArrayList<>() : new ArrayList<>(data);
		entries.sort(Comparator.comparingInt(WeightChartData::day));
		this.data = entries;
		this.days = Math.max(1, days);
		this.windowEndDate = windowEndDate == null ? LocalDate.now() : windowEndDate;
		repaint();
	}

	/** Configures the value range (kg) shown on the y-axis and repaints. */
	public void setValueRange(double minWeight, double maxWeight) {
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
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
			renderer.render(g2, getWidth(), getHeight(), data, days, windowEndDate, minWeight, maxWeight);
		} finally {
			g2.dispose();
		}
	}
}
