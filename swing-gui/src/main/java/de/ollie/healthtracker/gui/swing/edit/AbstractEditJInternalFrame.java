package de.ollie.healthtracker.gui.swing.edit;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;

public abstract class AbstractEditJInternalFrame<T> extends JInternalFrame {

	public interface Observer<T> {
		void onCancel();

		void onDelete(T toDelete);

		void onSave(T toSave);
	}

	@Getter
	private T toEdit;

	@Getter
	protected AbstractEditPanel<T> editPanel;

	private EditDialogComponentFactory componentFactory;

	public AbstractEditJInternalFrame(
		JDesktopPane desktopPane,
		String title,
		T toEdit,
		EditDialogComponentFactory componentFactory,
		Observer<T> observer,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(title, true, true, true, true);
		this.componentFactory = componentFactory;
		this.toEdit = toEdit;
		setBounds(20, 20, 600, 400);
		setContentPane(createContentPanel(toEdit, observer, itemProviders));
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		pack();
		setVisible(true);
	}

	private JPanel createContentPanel(T toEdit, Observer<T> observer, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		p.add(createEditorPanel(toEdit, itemProviders), BorderLayout.CENTER);
		p.add(createButtonPanel(observer), BorderLayout.SOUTH);
		return p;
	}

	protected abstract JPanel createEditorPanel(T toEdit, Map<String, ItemProvider<?>> itemProviders);

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

	public T getCurrentContent() {
		return editPanel.getCurrentContent();
	}
}
