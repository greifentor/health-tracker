package de.ollie.healthtracker.gui.swing.select;

import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public abstract class AbstractSelectJInternalFrame<T> extends JInternalFrame implements SelectionPanelObserver {

	protected final JDesktopPane desktopPane;
	protected final EditDialogComponentFactory editDialogComponentFactory;

	public AbstractSelectJInternalFrame(
		JDesktopPane desktopPane,
		String className,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super("Select " + className + "s", true, true, true, true);
		this.desktopPane = desktopPane;
		this.editDialogComponentFactory = editDialogComponentFactory;
	}

	/** Pixels each newly opened dialog is shifted relative to the previous one (cascade). */
	private static final int CASCADE_STEP = 25;

	/** Number of dialogs after which the cascade offset wraps back to the base position, so it stays on screen. */
	private static final int CASCADE_WRAP = 8;

	protected void finishConstruct() {
		int offset = (countOpenSelectFrames() % CASCADE_WRAP) * CASCADE_STEP;
		setBounds(10 + offset, 10 + offset, 600, 400);
		setContentPane(createSelectPanel());
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	/** Counts the select dialogs currently open on the desktop pane (excluding this one), used for the cascade offset. */
	private int countOpenSelectFrames() {
		int count = 0;
		for (JInternalFrame frame : desktopPane.getAllFrames()) {
			if (frame != this && frame instanceof AbstractSelectJInternalFrame) {
				count++;
			}
		}
		return count;
	}

	protected abstract AbstractSelectJPanel<T> createSelectPanel();

	@Override
	public void onCancel() {
		setVisible(false);
		desktopPane.remove(this);
	}
}
