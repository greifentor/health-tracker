package de.ollie.healthtracker.gui.swing.chart.alcohol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * A Swing component that displays the daily alcohol consumption (litres of pure alcohol) of a configurable number of days
 * (ending on a window end date) as a bar chart. The x-axis covers those days, the y-axis the litres over a value range
 * starting at zero and scaled to the largest displayed amount (so the tallest bar fills the plot height). All drawing is
 * delegated to an {@link AlcoholChartRenderer}.
 */
public class AlcoholChartJComponent extends JComponent {

	private static final int DEFAULT_DAYS = 31;
	private static final double FALLBACK_MAX_LITERS = 1.0;

	private final AlcoholChartRenderer renderer = new AlcoholChartRenderer();
	private List<AlcoholChartData> data = new ArrayList<>();
	private int days = DEFAULT_DAYS;
	private LocalDate windowEndDate = LocalDate.now();

	public AlcoholChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data, the number of days shown on the x-axis and the window end date (the calendar date of
	 * the last, right-most day position) used to derive the day-of-month axis labels, then repaints. The passed list is
	 * not modified.
	 */
	public void setData(List<AlcoholChartData> data, int days, LocalDate windowEndDate) {
		this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
		this.days = Math.max(1, days);
		this.windowEndDate = windowEndDate == null ? LocalDate.now() : windowEndDate;
		repaint();
	}

	/** The y-axis maximum: the largest displayed amount, so the tallest bar fills the plot height (a fallback is used when there is no data). */
	private double maxLiters() {
		double max = data.stream().mapToDouble(AlcoholChartData::liters).max().orElse(0);
		return max > 0 ? max : FALLBACK_MAX_LITERS;
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
			renderer.render(g2, getWidth(), getHeight(), data, days, windowEndDate, maxLiters());
		} finally {
			g2.dispose();
		}
	}
}
