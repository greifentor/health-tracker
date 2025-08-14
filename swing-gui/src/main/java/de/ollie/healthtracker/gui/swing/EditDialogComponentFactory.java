package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.gui.swing.BaseEditDialog.Observer;
import javax.swing.JButton;

public interface EditDialogComponentFactory {
	<T> JButton createSaveButton(Observer<T> observer, BaseEditDialog<T> dialog);
}
