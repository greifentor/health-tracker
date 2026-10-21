package de.ollie.healthtracker.gui.swing.chart;

import java.util.Optional;

/**
 * A linear trend (least-squares regression) {@code value = slope * x + intercept} fitted to a set of points, used to draw
 * the dotted tendency line of a chart series.
 */
public record TrendLine(double slope, double intercept) {
	/** Fits a trend line to the given points, or returns empty when there are fewer than two distinct x values. */
	public static Optional<TrendLine> fit(double[] xs, double[] ys) {
		int n = Math.min(xs.length, ys.length);
		if (n < 2) {
			return Optional.empty();
		}
		double sumX = 0;
		double sumY = 0;
		double sumXx = 0;
		double sumXy = 0;
		for (int i = 0; i < n; i++) {
			sumX += xs[i];
			sumY += ys[i];
			sumXx += xs[i] * xs[i];
			sumXy += xs[i] * ys[i];
		}
		double denominator = n * sumXx - sumX * sumX;
		if (denominator == 0) {
			return Optional.empty();
		}
		double slope = (n * sumXy - sumX * sumY) / denominator;
		double intercept = (sumY - slope * sumX) / n;
		return Optional.of(new TrendLine(slope, intercept));
	}

	public double valueAt(double x) {
		return slope * x + intercept;
	}
}
