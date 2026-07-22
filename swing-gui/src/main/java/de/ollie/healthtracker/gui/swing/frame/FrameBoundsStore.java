package de.ollie.healthtracker.gui.swing.frame;

import java.awt.Rectangle;
import java.util.Map;

/** Persists the last known bounds of internal frames, keyed by a stable per-frame-type key. */
public interface FrameBoundsStore {
	Map<String, Rectangle> load();

	void save(Map<String, Rectangle> boundsByKey);
}
