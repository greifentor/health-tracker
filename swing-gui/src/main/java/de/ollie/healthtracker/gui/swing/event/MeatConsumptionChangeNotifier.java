package de.ollie.healthtracker.gui.swing.event;

import jakarta.inject.Named;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Notifies registered listeners whenever meat consumption data changed (created / updated / deleted). Views such as the
 * nutrition chart can register to refresh themselves. Listeners are invoked synchronously on the calling thread, which
 * for GUI-triggered changes is the event dispatch thread.
 */
@Named
public class MeatConsumptionChangeNotifier {

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
