package de.ollie.healthtracker.gui.swing.select;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractSelectJPanel<T>
	extends JPanel
	implements AbstractEditJInternalFrame.Observer<T>, MouseListener {

	@Getter(AccessLevel.PROTECTED)
	private final JDesktopPane desktopPane;

	@Getter(AccessLevel.PROTECTED)
	private final EditDialogComponentFactory editDialogComponentFactory;

	@Getter(AccessLevel.PROTECTED)
	private String className;

	private List<T> objectsToSelect;

	@Getter(AccessLevel.PROTECTED)
	private SelectionPanelObserver observer;

	private JTable tableSelection;

	public AbstractSelectJPanel(
		JDesktopPane desktopPane,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(new BorderLayout(HGAP, VGAP));
		this.className = className;
		this.desktopPane = desktopPane;
		this.editDialogComponentFactory = editDialogComponentFactory;
		this.observer = observer;
		add(createSelectionPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
	}

	protected void updateTableSelection() {
		objectsToSelect = getObjectsToSelect();
		tableSelection.setModel(createSelectionModel());
	}

	protected abstract AbstractSelectionTableModel<T> createSelectionModel();

	protected abstract List<T> getObjectsToSelect();

	private JPanel createButtonPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		p.add(createCancelButton(() -> cancel()));
		p.add(new JLabel("     "));
		p.add(createNewButton(() -> create()));
		p.add(new JLabel("     "));
		p.add(createSelectButton(() -> select()));
		return p;
	}

	private JPanel createSelectionPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		tableSelection = new JTable(createSelectionModel());
		tableSelection.addMouseListener(this);
		p.add(new JScrollPane(tableSelection), BorderLayout.CENTER);
		return p;
	}

	private JButton createCancelButton(Runnable action) {
		JButton b = new JButton("Cancel");
		if (action != null) {
			b.addActionListener(e -> {
				action.run();
			});
		}
		return b;
	}

	private JButton createNewButton(Runnable action) {
		JButton b = new JButton("New");
		if (action != null) {
			b.addActionListener(e -> {
				action.run();
			});
		}
		return b;
	}

	public void closeDialog() {
		setVisible(false);
		desktopPane.remove(this);
	}

	private JButton createSelectButton(Runnable action) {
		JButton b = new JButton("Select");
		if (action != null) {
			b.addActionListener(e -> {
				action.run();
			});
		}
		return b;
	}

	private void select() {
		int[] selectedIndices = tableSelection.getSelectedRows();
		for (int i = 0, leni = selectedIndices.length; i < leni; i++) {
			createEditInternalFrame(objectsToSelect.get(selectedIndices[i]));
		}
	}

	protected abstract void createEditInternalFrame(T selected);

	private void create() {
		createEditInternalFrame(createNewObject());
	}

	protected abstract T createNewObject();

	private void cancel() {
		if (observer != null) {
			observer.onCancel();
		}
	}

	@Override
	public void onCancel() {}

	@Override
	public void onDelete(T toDelete) {
		if (
			JOptionPane.showConfirmDialog(
				this,
				"Do you really want to delete this " + className.toLowerCase() + "?",
				"Delete " + className,
				JOptionPane.YES_NO_OPTION
			) ==
			JOptionPane.YES_OPTION
		) {
			delete(toDelete);
			updateTableSelection();
		}
	}

	protected abstract void delete(T toDelete);

	@Override
	public void onSave(T toSave) {
		save(toSave);
		updateTableSelection();
	}

	protected abstract void save(T toSave);

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			select();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
