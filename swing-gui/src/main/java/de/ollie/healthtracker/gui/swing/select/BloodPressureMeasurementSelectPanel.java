package de.ollie.healthtracker.gui.swing.select;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.BloodPressureMeasurementEditJInternalFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BloodPressureMeasurementSelectPanel
	extends JPanel
	implements BaseEditInternalFrame.Observer<BloodPressureMeasurement> {

	public interface Observer {
		void onCancel();
	}

	private final JDesktopPane desktopPane;
	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private List<BloodPressureMeasurement> bloodPressureMeasurements;
	private JTable tableSelection;
	private Observer observer;

	public BloodPressureMeasurementSelectPanel(
		BloodPressureMeasurementService bloodPressureMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer observer
	) {
		super(new BorderLayout(HGAP, VGAP));
		this.desktopPane = desktopPane;
		this.bloodPressureMeasurementService = bloodPressureMeasurementService;
		this.editDialogComponentFactory = editDialogComponentFactory;
		this.observer = observer;
		bloodPressureMeasurements = getBloodPressureMeasurements();
		add(createSelectionPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
	}

	private void updateTableSelection() {
		bloodPressureMeasurements = getBloodPressureMeasurements();
		tableSelection.setModel(new BloodPressureMeasurementSelectionTableModel(bloodPressureMeasurements));
	}

	private List<BloodPressureMeasurement> getBloodPressureMeasurements() {
		return bloodPressureMeasurementService
			.listBloodPressureMeasurements()
			.stream()
			.sorted((bpm0, bpm1) -> compare(bpm1, bpm0))
			.toList();
	}

	private int compare(BloodPressureMeasurement bpm0, BloodPressureMeasurement bpm1) {
		int r = bpm0.getDateOfRecording().compareTo(bpm1.getDateOfRecording());
		if (r == 0) {
			r = bpm0.getTimeOfRecording().compareTo(bpm1.getTimeOfRecording());
		}
		return r;
	}

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
		tableSelection = new JTable(new BloodPressureMeasurementSelectionTableModel(bloodPressureMeasurements));
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
			BloodPressureMeasurement bpm = bloodPressureMeasurements.get(selectedIndices[i]);
			new BloodPressureMeasurementEditJInternalFrame(bpm, editDialogComponentFactory, this, desktopPane);
		}
	}

	private void create() {
		BloodPressureMeasurement bpm = bloodPressureMeasurementService.createBloodPressureMeasurement(
			LocalDate.now(),
			80,
			60,
			130,
			LocalTime.now(),
			BloodPressureMeasurementStatus.YELLOW,
			false
		);
		new BloodPressureMeasurementEditJInternalFrame(bpm, editDialogComponentFactory, this, desktopPane);
	}

	private void cancel() {
		if (observer != null) {
			observer.onCancel();
		}
	}

	@Override
	public void onCancel() {}

	@Override
	public void onDelete(BloodPressureMeasurement toDelete) {
		if (
			JOptionPane.showConfirmDialog(
				this,
				"Delete Blood Pressure Measurement",
				"Do you really want to delete this blood pressure measurement?",
				JOptionPane.YES_NO_OPTION
			) ==
			JOptionPane.YES_OPTION
		) {
			bloodPressureMeasurementService.deleteBloodPressureMeasurement(toDelete.getId());
			updateTableSelection();
		}
	}

	@Override
	public void onSave(BloodPressureMeasurement toSave) {
		bloodPressureMeasurementService.updateBloodPressureMeasurement(toSave);
		updateTableSelection();
	}
}
