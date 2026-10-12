package de.ollie.healthtracker.gui.swing.chart.alcohol;

/**
 * A single data point for the {@link AlcoholChartJComponent}: the total alcohol consumption of one day of the window.
 * The {@code day} (1-based window position, right-most = today) is plotted on the x-axis, the {@code liters} value on the
 * y-axis.
 */
public record AlcoholChartData(int day, double liters) {}
