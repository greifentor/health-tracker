package de.ollie.healthtracker.gui.swing.chart;

/**
 * A single data point for the {@link BloodPressureChartJComponent}: the (averaged) blood pressure values of one day of
 * the month. The {@code day} (1-31) is plotted on the x-axis, the mmHg / pulse values on the y-axis.
 */
public record BloodPressureChartData(int day, int systolic, int diastolic, int pulse) {}
