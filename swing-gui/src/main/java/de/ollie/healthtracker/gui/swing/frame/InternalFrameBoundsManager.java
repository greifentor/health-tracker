package de.ollie.healthtracker.gui.swing.frame;

import jakarta.inject.Named;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Remembers position and size of internal frames across program runs. {@link #applyBounds} places a frame at its stored
 * bounds (if fully visible on the desktop, otherwise centered) and attaches listeners that keep the stored bounds
 * current while the frame is moved / resized and when it is closed (both via the close button and via a programmatic
 * {@code dispose()}). {@link #save} persists all bounds - to be called on program exit.
 */
@Named
public class InternalFrameBoundsManager {

	private static final int DEFAULT_OFFSET = 20;

	private final FrameBoundsStore store;
	private final Map<String, Rectangle> boundsByKey;
	private final Set<String> initiallyStoredKeys;
	private final int frameOffset;

	public InternalFrameBoundsManager(FrameBoundsStore store) {
		this(store, DEFAULT_OFFSET);
	}

	@Autowired
	public InternalFrameBoundsManager(FrameBoundsStore store, @Value("${app.frame-bounds.offset:20}") int frameOffset) {
		this.store = store;
		this.frameOffset = frameOffset;
		Map<String, Rectangle> loaded = store.load();
		this.boundsByKey = new HashMap<>(loaded);
		this.initiallyStoredKeys = Set.copyOf(loaded.keySet());
	}

	/** Whether bounds for the given key were already persisted when the manager was created (i.e. from a previous run). */
	public boolean hasStoredBounds(String key) {
		return initiallyStoredKeys.contains(key);
	}

	/**
	 * Places the frame at its stored bounds (if visible, otherwise centered on the default size) and starts tracking
	 * every move, resize and close so the last bounds are remembered.
	 */
	public void applyBounds(JInternalFrame frame, JDesktopPane desktopPane, String key, Rectangle defaultBounds) {
		Rectangle target = computeBounds(boundsByKey.get(key), desktopPane.getSize(), defaultBounds);
		target = avoidOverlap(target, desktopPane, frame);
		frame.setBounds(target);
		record(frame, key);
		trackChanges(frame, key);
		trackClosing(frame, key);
	}

	/**
	 * Shifts the target down/right by {@code frameOffset} pixels as long as another frame already occupies that exact
	 * position (and there is still room on the desktop), so a newly opened frame does not land exactly on top of an
	 * existing one.
	 */
	private Rectangle avoidOverlap(Rectangle desired, JDesktopPane desktopPane, JInternalFrame movingFrame) {
		if (frameOffset <= 0) {
			return desired;
		}
		Dimension desktopSize = desktopPane.getSize();
		Rectangle result = new Rectangle(desired);
		while (
			isPositionOccupied(result, desktopPane, movingFrame) &&
			result.x + frameOffset + result.width <= desktopSize.width &&
			result.y + frameOffset + result.height <= desktopSize.height
		) {
			result.x += frameOffset;
			result.y += frameOffset;
		}
		return result;
	}

	private boolean isPositionOccupied(Rectangle bounds, JDesktopPane desktopPane, JInternalFrame movingFrame) {
		for (JInternalFrame other : desktopPane.getAllFrames()) {
			if (other != movingFrame && other.isVisible() && other.getX() == bounds.x && other.getY() == bounds.y) {
				return true;
			}
		}
		return false;
	}

	/** Persists all remembered bounds. */
	public void save() {
		store.save(boundsByKey);
	}

	/** Stored bounds if fully visible on the desktop; otherwise the (size-clamped) frame centered on the desktop. */
	Rectangle computeBounds(Rectangle stored, Dimension desktopSize, Rectangle defaultBounds) {
		if (stored == null) {
			return defaultBounds;
		}
		int width = Math.min(stored.width, desktopSize.width);
		int height = Math.min(stored.height, desktopSize.height);
		Rectangle candidate = new Rectangle(stored.x, stored.y, width, height);
		if (isFullyVisible(candidate, desktopSize)) {
			return candidate;
		}
		int x = Math.max(0, (desktopSize.width - width) / 2);
		int y = Math.max(0, (desktopSize.height - height) / 2);
		return new Rectangle(x, y, width, height);
	}

	private boolean isFullyVisible(Rectangle r, Dimension desktopSize) {
		return r.x >= 0 && r.y >= 0 && r.x + r.width <= desktopSize.width && r.y + r.height <= desktopSize.height;
	}

	private void trackChanges(JInternalFrame frame, String key) {
		frame.addComponentListener(
			new ComponentAdapter() {
				@Override
				public void componentMoved(ComponentEvent e) {
					record(frame, key);
				}

				@Override
				public void componentResized(ComponentEvent e) {
					record(frame, key);
				}
			}
		);
	}

	private void trackClosing(JInternalFrame frame, String key) {
		frame.addInternalFrameListener(
			new InternalFrameAdapter() {
				@Override
				public void internalFrameClosing(InternalFrameEvent e) {
					record(frame, key);
				}

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					record(frame, key);
				}
			}
		);
	}

	private void record(JInternalFrame frame, String key) {
		boundsByKey.put(key, new Rectangle(frame.getBounds()));
	}
}
