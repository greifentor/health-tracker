package de.ollie.healthtracker.gui.swing.chart;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;
import javax.swing.JComponent;

/**
 * Renders three line graphs (meat, pescetarian, veggie) into a single component. The x-axis covers the months 1-12, the
 * y-axis the value range 0-31. The data is provided as a list of {@link NutritionChartData} records; the {@code month}
 * value of each record positions its points on the x-axis.
 */
public class NutritionChartJComponent extends JComponent {

	private static final int MIN_MONTH = 1;
	private static final int MAX_MONTH = 12;
	private static final int MIN_VALUE = 0;
	private static final int MAX_VALUE = 31;
	private static final int VALUE_GRID_STEP = 5;

	private static final int PADDING_LEFT = 40;
	private static final int PADDING_RIGHT = 20;
	private static final int PADDING_TOP = 20;
	private static final int PADDING_BOTTOM = 30;
	private static final int LEGEND_HEIGHT = 24;
	private static final int MARKER_RADIUS = 3;

	private static final Color COLOR_MEAT = new Color(0xC0392B);
	private static final Color COLOR_PESCETARIAN = new Color(0x2980B9);
	private static final Color COLOR_VEGGIE = new Color(0x27AE60);
	private static final Color COLOR_AXIS = Color.DARK_GRAY;
	private static final Color COLOR_GRID = new Color(0xE0E0E0);

	private List<NutritionChartData> data = new ArrayList<>();

	public NutritionChartJComponent() {
		setPreferredSize(new Dimension(640, 400));
		setBackground(Color.WHITE);
		setOpaque(true);
	}

	/**
	 * Replaces the displayed data and repaints. The list is copied and sorted by month, so the caller may pass an
	 * unsorted list and keep ownership of it.
	 */
	public void setData(List<NutritionChartData> data) {
		this.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
		this.data.sort(Comparator.comparingInt(NutritionChartData::month));
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
			int plotLeft = PADDING_LEFT;
			int plotRight = getWidth() - PADDING_RIGHT;
			int plotTop = PADDING_TOP;
			int plotBottom = getHeight() - PADDING_BOTTOM - LEGEND_HEIGHT;
			if (plotRight <= plotLeft || plotBottom <= plotTop) {
				return;
			}
			drawGridAndAxes(g2, plotLeft, plotRight, plotTop, plotBottom);
			drawSeries(g2, plotLeft, plotRight, plotTop, plotBottom, COLOR_MEAT, NutritionChartData::meat);
			drawSeries(g2, plotLeft, plotRight, plotTop, plotBottom, COLOR_PESCETARIAN, NutritionChartData::pescetarian);
			drawSeries(g2, plotLeft, plotRight, plotTop, plotBottom, COLOR_VEGGIE, NutritionChartData::veggie);
			drawLegend(g2, plotLeft, getHeight() - LEGEND_HEIGHT);
		} finally {
			g2.dispose();
		}
	}

	private int xForMonth(int month, int plotLeft, int plotRight) {
		return plotLeft + (int) Math.round((double) (month - MIN_MONTH) / (MAX_MONTH - MIN_MONTH) * (plotRight - plotLeft));
	}

	private int yForValue(int value, int plotTop, int plotBottom) {
		return (
			plotBottom - (int) Math.round((double) (value - MIN_VALUE) / (MAX_VALUE - MIN_VALUE) * (plotBottom - plotTop))
		);
	}

	private void drawGridAndAxes(Graphics2D g2, int plotLeft, int plotRight, int plotTop, int plotBottom) {
		FontMetrics fm = g2.getFontMetrics();
		// Horizontal grid lines and y-axis labels (0..31).
		for (int value = MIN_VALUE; value <= MAX_VALUE; value += VALUE_GRID_STEP) {
			int y = yForValue(value, plotTop, plotBottom);
			g2.setColor(COLOR_GRID);
			g2.drawLine(plotLeft, y, plotRight, y);
			g2.setColor(COLOR_AXIS);
			String label = Integer.toString(value);
			g2.drawString(label, plotLeft - HGAP - fm.stringWidth(label), y + fm.getAscent() / 2 - 1);
		}
		// Vertical grid lines and x-axis labels (1..12).
		for (int month = MIN_MONTH; month <= MAX_MONTH; month++) {
			int x = xForMonth(month, plotLeft, plotRight);
			g2.setColor(COLOR_GRID);
			g2.drawLine(x, plotTop, x, plotBottom);
			g2.setColor(COLOR_AXIS);
			String label = Integer.toString(month);
			g2.drawString(label, x - fm.stringWidth(label) / 2, plotBottom + fm.getAscent() + 2);
		}
		// Axes.
		g2.setColor(COLOR_AXIS);
		g2.drawLine(plotLeft, plotTop, plotLeft, plotBottom);
		g2.drawLine(plotLeft, plotBottom, plotRight, plotBottom);
	}

	private void drawSeries(
		Graphics2D g2,
		int plotLeft,
		int plotRight,
		int plotTop,
		int plotBottom,
		Color color,
		ToIntFunction<NutritionChartData> valueAccessor
	) {
		if (data.isEmpty()) {
			return;
		}
		g2.setColor(color);
		g2.setStroke(new BasicStroke(2f));
		int[] xs = new int[data.size()];
		int[] ys = new int[data.size()];
		for (int i = 0; i < data.size(); i++) {
			NutritionChartData entry = data.get(i);
			xs[i] = xForMonth(clampMonth(entry.month()), plotLeft, plotRight);
			ys[i] = yForValue(clampValue(valueAccessor.applyAsInt(entry)), plotTop, plotBottom);
		}
		if (data.size() > 1) {
			g2.drawPolyline(xs, ys, data.size());
		}
		for (int i = 0; i < data.size(); i++) {
			g2.fillOval(xs[i] - MARKER_RADIUS, ys[i] - MARKER_RADIUS, 2 * MARKER_RADIUS, 2 * MARKER_RADIUS);
		}
	}

	private void drawLegend(Graphics2D g2, int x, int y) {
		x = drawLegendEntry(g2, x, y, COLOR_MEAT, "Meat");
		x = drawLegendEntry(g2, x, y, COLOR_PESCETARIAN, "Pescetarian");
		drawLegendEntry(g2, x, y, COLOR_VEGGIE, "Veggie");
	}

	private int drawLegendEntry(Graphics2D g2, int x, int y, Color color, String label) {
		FontMetrics fm = g2.getFontMetrics();
		int swatch = fm.getAscent();
		int swatchY = y + (LEGEND_HEIGHT - swatch) / 2;
		g2.setColor(color);
		g2.fillRect(x, swatchY, swatch, swatch);
		g2.setColor(COLOR_AXIS);
		g2.drawString(label, x + swatch + HGAP, y + (LEGEND_HEIGHT + fm.getAscent()) / 2 - 1);
		return x + swatch + HGAP + fm.stringWidth(label) + 3 * HGAP;
	}

	private int clampMonth(int month) {
		return Math.max(MIN_MONTH, Math.min(MAX_MONTH, month));
	}

	private int clampValue(int value) {
		return Math.max(MIN_VALUE, Math.min(MAX_VALUE, value));
	}
}
