package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame.Observer;
import javax.swing.JButton;

public interface EditDialogComponentFactory {
	<T> JButton createSaveButton(Observer<T> observer, BaseEditInternalFrame<T> dialog);
}
