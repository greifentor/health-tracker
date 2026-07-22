package de.ollie.healthtracker.gui.swing.frame;

import java.awt.Component;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * A {@link JDesktopPane} that hands every added {@link JInternalFrame} to an {@link InternalFrameBoundsManager}. The
 * manager restores the frame's remembered position/size (or centers it) and attaches the listeners that record moves,
 * resizes and closes - so any frame added to this desktop pane transfers its bounds to the manager without extra code
 * in the individual frame classes. The frame's key is its class name; its current bounds serve as the default.
 */
public class BoundsTrackingDesktopPane extends JDesktopPane {

	private final transient InternalFrameBoundsManager boundsManager;

	public BoundsTrackingDesktopPane(InternalFrameBoundsManager boundsManager) {
		this.boundsManager = boundsManager;
	}

	@Override
	public Component add(Component comp) {
		Component result = super.add(comp);
		if (comp instanceof JInternalFrame frame) {
			boundsManager.applyBounds(frame, this, frame.getClass().getSimpleName(), frame.getBounds());
		}
		return result;
	}
}
