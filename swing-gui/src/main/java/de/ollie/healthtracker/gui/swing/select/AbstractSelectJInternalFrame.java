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

	protected void finishConstruct() {
		setBounds(10, 10, 600, 400);
		setContentPane(createSelectPanel());
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	protected abstract AbstractSelectJPanel<T> createSelectPanel();

	@Override
	public void onCancel() {
		setVisible(false);
		desktopPane.remove(this);
	}
}
