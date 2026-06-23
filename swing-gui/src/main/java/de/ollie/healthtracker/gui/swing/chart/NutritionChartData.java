package de.ollie.healthtracker.gui.swing.chart;

/**
 * A single data point for the {@link NutritionChartJComponent}. The {@code month} (1-12) is plotted on the x-axis, the
 * remaining values (0-31) on the y-axis.
 */
public record NutritionChartData(int month, int meat, int pescetarian, int veggie) {}
