package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame.Observer;
import javax.swing.JButton;

public interface EditDialogComponentFactory {
	<T> JButton createSaveButton(Observer<T> observer, AbstractEditInternalFrame<T> dialog);
}
