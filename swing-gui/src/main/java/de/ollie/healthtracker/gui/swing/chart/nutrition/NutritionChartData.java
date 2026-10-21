package de.ollie.healthtracker.gui.swing.chart.nutrition;

/**
 * A single data point for the {@link NutritionChartJComponent}. The {@code year} and {@code month} (1-12) form the x-axis
 * label, the remaining values (0-31) are plotted on the y-axis.
 */
public record NutritionChartData(int year, int month, int meat, int pescetarian, int veggie) {}
