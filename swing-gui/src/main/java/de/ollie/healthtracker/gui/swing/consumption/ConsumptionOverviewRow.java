package de.ollie.healthtracker.gui.swing.consumption;

/**
 * A single line of the consumption overview: a {@code label} (a category like "Fleisch" or, for a sub-position, the name
 * of a meat type) and its already formatted {@code amount}. {@code subPosition} tells whether the line is a sub-position
 * of the category above it (and should be shown indented).
 */
public record ConsumptionOverviewRow(String label, String amount, boolean subPosition) {}
