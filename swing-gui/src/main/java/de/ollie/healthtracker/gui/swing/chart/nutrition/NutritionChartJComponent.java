package de.ollie.healthtracker.gui.swing.chart.nutrition;

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
 * A Swing component that displays the meat / pescetarian / veggie line graphs for a list of {@link NutritionChartData} (one
 * entry per month, oldest first) and delegates all drawing to a {@link NutritionChartRenderer}.
 */
public class NutritionChartJComponent extends JComponent {

	private final NutritionChartRenderer renderer = new NutritionChartRenderer();
	private List<NutritionChartData> data = new ArrayList<>();
	private final Set<String> hiddenSeries = new HashSet<>();

	public NutritionChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/** Replaces the displayed data (in the given order, so the last entry stays rightmost) and repaints. The passed list is not modified. */
	public void setData(List<NutritionChartData> data) {
		this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
		repaint();
	}

	/** Shows or hides the series with the given label (as listed in {@link NutritionChartRenderer#SERIES_LABELS}) and repaints. */
	public void setSeriesHidden(String label, boolean hidden) {
		if (hidden) {
			hiddenSeries.add(label);
		} else {
			hiddenSeries.remove(label);
		}
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
			renderer.render(g2, getWidth(), getHeight(), data, hiddenSeries);
		} finally {
			g2.dispose();
		}
	}
}
