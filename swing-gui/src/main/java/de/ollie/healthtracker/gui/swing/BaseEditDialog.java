package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;

public class BaseEditDialog<T> extends JDialog {

	public interface Observer<T> {
		void onCancel();

		void onDelete(T toDelete);

		void onSave(T toSave);
	}

	@Getter
	private T toEdit;

	private EditDialogComponentFactory componentFactory;

	public BaseEditDialog(String title, T toEdit, EditDialogComponentFactory componentFactory, Observer<T> observer) {
		super((JDialog) null, title);
		this.componentFactory = componentFactory;
		setContentPane(createContentPanel(observer));
		pack();
		setVisible(true);
	}

	private JPanel createContentPanel(Observer<T> observer) {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		p.add(createButtonPanel(observer), BorderLayout.SOUTH);
		return p;
	}

	private JPanel createButtonPanel(Observer<T> observer) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		p.add(createCancelButton(observer));
		p.add(new JLabel("     "));
		p.add(createDeleteButton(observer));
		p.add(new JLabel("     "));
		p.add(componentFactory.createSaveButton(observer, this));
		return p;
	}

	private JButton createCancelButton(Observer<T> observer) {
		JButton b = new JButton("Cancel");
		if (observer != null) {
			b.addActionListener(e -> {
				observer.onCancel();
				closeDialog();
			});
		}
		return b;
	}

	public void closeDialog() {
		setVisible(false);
		dispose();
	}

	private JButton createDeleteButton(Observer<T> observer) {
		JButton b = new JButton("Delete");
		if (observer != null) {
			b.addActionListener(e -> {
				observer.onDelete(toEdit);
				closeDialog();
			});
		}
		return b;
	}
}
