package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame.Observer;
import javax.swing.JButton;

public interface EditDialogComponentFactory {
	<T> JButton createSaveButton(Observer<T> observer, AbstractEditJInternalFrame<T> dialog);
}
