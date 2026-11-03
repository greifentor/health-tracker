package de.ollie.healthtracker.gui.swing.chart.meatfishmass;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;

/**
 * A Swing component that displays the monthly meat and fish mass (in kilograms) for a list of {@link MeatFishMassChartData}
 * (one entry per month, oldest first) as two line graphs. The y-axis starts at zero and is scaled to the largest displayed
 * mass, but to at least {@value #MIN_MAX_KILOGRAMS} kg (which also covers the case of no data). All drawing is delegated to
 * a {@link MeatFishMassChartRenderer}.
 */
public class MeatFishMassChartJComponent extends JComponent {

	private static final double MIN_MAX_KILOGRAMS = 0.1;

	private final MeatFishMassChartRenderer renderer = new MeatFishMassChartRenderer();
	private List<MeatFishMassChartData> data = new ArrayList<>();
	private final Set<String> hiddenSeries = new HashSet<>();

	public MeatFishMassChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/** Replaces the displayed data (in the given order, so the last entry stays rightmost) and repaints. The passed list is not modified. */
	public void setData(List<MeatFishMassChartData> data) {
		this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
		repaint();
	}

	/** Shows or hides the series with the given label (as listed in {@link MeatFishMassChartRenderer#SERIES_LABELS}) and repaints. */
	public void setSeriesHidden(String label, boolean hidden) {
		if (hidden) {
			hiddenSeries.add(label);
		} else {
			hiddenSeries.remove(label);
		}
		repaint();
	}

	private double maxKilograms() {
		double max = data
			.stream()
			.mapToDouble(entry -> Math.max(entry.meatKilograms(), entry.fishKilograms()))
			.max()
			.orElse(0);
		return Math.max(MIN_MAX_KILOGRAMS, max);
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
			renderer.render(g2, getWidth(), getHeight(), data, hiddenSeries, maxKilograms());
		} finally {
			g2.dispose();
		}
	}
}
