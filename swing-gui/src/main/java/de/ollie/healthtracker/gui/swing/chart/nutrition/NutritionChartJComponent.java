package de.ollie.healthtracker.gui.swing.chart.nutrition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	 * Replaces the displayed data and repaints. First the last {@value #MAX_ENTRIES} entries of the passed list are
	 * taken; any calendar month missing within the 12-month window ending at the last entry is then inserted in its
	 * place as a full-veggie month (no meat/fish, veggie = the month's number of days). The order of the passed entries
	 * is preserved, so the last passed entry stays the last item on the x-axis. The passed list is not modified.
	 */
	public void setData(List<NutritionChartData> data) {
		List<NutritionChartData> entries = data == null ? new ArrayList<>() : new ArrayList<>(data);
		if (entries.size() > MAX_ENTRIES) {
			entries = new ArrayList<>(entries.subList(entries.size() - MAX_ENTRIES, entries.size()));
		}
		this.data = withFullVeggieForMissingMonths(entries);
		repaint();
	}

	/**
	 * Returns the 12-month window ending at the last entry's month: the present entries keep their (chronological)
	 * order, and every calendar month missing in between is inserted at its place as a full-veggie month - a month
	 * without meat or fish consumption counts as fully vegetarian, so its veggie value is the month's number of days.
	 */
	private static List<NutritionChartData> withFullVeggieForMissingMonths(List<NutritionChartData> data) {
		if (data.isEmpty()) {
			return new ArrayList<>(data);
		}
		Map<Integer, NutritionChartData> byMonth = new HashMap<>();
		for (NutritionChartData entry : data) {
			byMonth.put(entry.month(), entry);
		}
		int lastMonth = data.get(data.size() - 1).month();
		List<NutritionChartData> result = new ArrayList<>();
		for (int offset = 1; offset <= 12; offset++) {
			int month = (lastMonth + offset - 1) % 12 + 1;
			NutritionChartData entry = byMonth.get(month);
			result.add(entry != null ? entry : new NutritionChartData(month, 0, 0, Month.of(month).maxLength()));
		}
		return result;
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
