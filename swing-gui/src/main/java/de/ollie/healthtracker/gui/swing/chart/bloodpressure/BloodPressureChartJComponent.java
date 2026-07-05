package de.ollie.healthtracker.gui.swing.chart.bloodpressure;

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
 * A Swing component that displays the systolic / diastolic / pulse line graphs for a list of
 * {@link BloodPressureChartData} (the daily values of a configurable number of days ending on a window end date). All
 * drawing is delegated to a {@link BloodPressureChartRenderer}.
 */
public class BloodPressureChartJComponent extends JComponent {

	private static final int DEFAULT_DAYS = 31;

	private final BloodPressureChartRenderer renderer = new BloodPressureChartRenderer();
	private List<BloodPressureChartData> data = new ArrayList<>();
	private int days = DEFAULT_DAYS;
	private LocalDate windowEndDate = LocalDate.now();

	public BloodPressureChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data (copied and sorted by day so the line is drawn left to right), the number of days
	 * shown on the x-axis and the window end date (the calendar date of the last, right-most day position) used to
	 * derive the day-of-month axis labels, then repaints. The passed list is not modified.
	 */
	public void setData(List<BloodPressureChartData> data, int days, LocalDate windowEndDate) {
		List<BloodPressureChartData> entries = data == null ? new ArrayList<>() : new ArrayList<>(data);
		entries.sort(Comparator.comparingInt(BloodPressureChartData::day));
		this.data = entries;
		this.days = Math.max(1, days);
		this.windowEndDate = windowEndDate == null ? LocalDate.now() : windowEndDate;
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
			renderer.render(g2, getWidth(), getHeight(), data, days, windowEndDate);
		} finally {
			g2.dispose();
		}
	}
}
