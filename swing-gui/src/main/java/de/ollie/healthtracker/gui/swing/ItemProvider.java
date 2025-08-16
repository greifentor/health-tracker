package de.ollie.healthtracker.gui.swing;

import java.util.List;

@FunctionalInterface
public interface ItemProvider<T> {
	List<T> getItem();
}
