package de.ollie.healthtracker.gui.swing.chart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * A Swing component that displays the meat / pescetarian / veggie line graphs for a list of {@link NutritionChartData}.
 * It keeps at most the last {@value #MAX_ENTRIES} entries (in the given order) and delegates all drawing to a
 * {@link NutritionChartRenderer}.
 */
public class NutritionChartJComponent extends JComponent {

	private static final int MAX_ENTRIES = 12;

	private final NutritionChartRenderer renderer = new NutritionChartRenderer();
	private List<NutritionChartData> data = new ArrayList<>();

	public NutritionChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data and repaints. Only the last {@value #MAX_ENTRIES} entries of the passed list are kept,
	 * in the given order; the list is copied, so the caller keeps ownership of it.
	 */
	public void setData(List<NutritionChartData> data) {
		List<NutritionChartData> entries = data == null ? new ArrayList<>() : new ArrayList<>(data);
		if (entries.size() > MAX_ENTRIES) {
			entries = new ArrayList<>(entries.subList(entries.size() - MAX_ENTRIES, entries.size()));
		}
		this.data = entries;
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
			renderer.render(g2, getWidth(), getHeight(), data);
		} finally {
			g2.dispose();
		}
	}
}
