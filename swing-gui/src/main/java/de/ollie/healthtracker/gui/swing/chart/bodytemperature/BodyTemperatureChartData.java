package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

/**
 * A single data point for the {@link BodyTemperatureChartJComponent}: the (averaged) body temperature of one day of the
 * month. The {@code day} (1-31) is plotted on the x-axis, the {@code celsius} value on the (configurable) y-axis.
 */
public record BodyTemperatureChartData(int day, double celsius) {}
