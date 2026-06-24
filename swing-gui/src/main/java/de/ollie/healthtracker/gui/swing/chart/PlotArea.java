package de.ollie.healthtracker.gui.swing.chart;

/**
 * The rectangular plotting area and the mapping from data positions/values to pixel coordinates. The value range
 * [{@code minValue}, {@code maxValue}] is mapped onto the vertical extent between {@code bottom} and {@code top}.
 */
public record PlotArea(int left, int right, int top, int bottom, int minValue, int maxValue) {
	public boolean isValid() {
		return right > left && bottom > top;
	}

	public int width() {
		return right - left;
	}

	int height() {
		return bottom - top;
	}

	/** Evenly spaces {@code count} columns across the width; a single column is centered. */
	public int xFor(int index, int count) {
		if (count <= 1) {
			return (left + right) / 2;
		}
		return left + (int) Math.round((double) index / (count - 1) * width());
	}

	/** Maps a value in [{@link #minValue()}, {@link #maxValue()}] to a y pixel coordinate. */
	public int yFor(int value) {
		return bottom - (int) Math.round((double) (value - minValue) / (maxValue - minValue) * height());
	}
}
