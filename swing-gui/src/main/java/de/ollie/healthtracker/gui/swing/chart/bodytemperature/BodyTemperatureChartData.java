package de.ollie.healthtracker.gui.swing.chart.bodytemperature;

/**
 * A single data point for the {@link BodyTemperatureChartJComponent}: the (averaged) body temperature of one day of the
 * month plus the resulting {@link BodyTemperatureStatus}. The {@code day} (1-31) is plotted on the x-axis, the
 * {@code celsius} value on the (configurable) y-axis, and {@code status} drives the color of the status bar at the top
 * of the chart.
 */
public record BodyTemperatureChartData(int day, double celsius, BodyTemperatureStatus status) {}
