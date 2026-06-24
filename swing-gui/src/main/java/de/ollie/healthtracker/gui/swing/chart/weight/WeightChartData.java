package de.ollie.healthtracker.gui.swing.chart.weight;

/**
 * A single data point for the {@link WeightChartJComponent}: the (averaged) weight of one day of the month. The
 * {@code day} (1-31) is plotted on the x-axis, the {@code kg} value on the (configurable) y-axis.
 */
public record WeightChartData(int day, double kg) {}
