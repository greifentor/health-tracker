package de.ollie.healthtracker.gui.swing.event;

import jakarta.inject.Named;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Notifies registered listeners whenever medication product data changed (created / updated / deleted). The expired
 * medication product overview registers to refresh itself. Listeners are invoked synchronously on the calling thread,
 * which for GUI-triggered changes is the event dispatch thread.
 */
@Named
public class MedicationProductChangeNotifier {

	private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

	public void addListener(Runnable listener) {
		listeners.add(listener);
	}

	public void removeListener(Runnable listener) {
		listeners.remove(listener);
	}

	public void notifyChanged() {
		listeners.forEach(Runnable::run);
	}
}
