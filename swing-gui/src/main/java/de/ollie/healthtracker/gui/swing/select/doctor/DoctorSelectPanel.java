package de.ollie.healthtracker.gui.swing.select.doctor;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.doctor.DoctorEditJInternalFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DoctorSelectPanel extends JPanel implements BaseEditInternalFrame.Observer<Doctor> {

	public interface Observer {
		void onCancel();
	}

	private final JDesktopPane desktopPane;
	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private List<Doctor> doctors;
	private JTable tableSelection;
	private Observer observer;

	public DoctorSelectPanel(
		DoctorService doctorService,
		DoctorTypeService doctorTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer observer
	) {
		super(new BorderLayout(HGAP, VGAP));
		this.desktopPane = desktopPane;
		this.doctorService = doctorService;
		this.doctorTypeService = doctorTypeService;
		this.editDialogComponentFactory = editDialogComponentFactory;
		this.observer = observer;
		doctors = getDoctors();
		add(createSelectionPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
	}

	private void updateTableSelection() {
		doctors = getDoctors();
		tableSelection.setModel(new DoctorSelectionTableModel(doctors));
	}

	private List<Doctor> getDoctors() {
		return doctorService.listDoctors().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList();
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
		tableSelection = new JTable(new DoctorSelectionTableModel(doctors));
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
			Doctor d = doctors.get(selectedIndices[i]);
			new DoctorEditJInternalFrame(
				d,
				() ->
					doctorTypeService
						.listDoctorTypes()
						.stream()
						.sorted((d0, d1) -> d0.getName().compareTo(d1.getName()))
						.toList(),
				editDialogComponentFactory,
				this,
				desktopPane
			);
		}
	}

	private void create() {
		List<Doctor> doctors = doctorService.listDoctors();
		if (
			doctors.isEmpty() &&
			(
				JOptionPane.showConfirmDialog(
					this,
					"No Doctors Found",
					"There are no doctors found in your system. Cannot create a new consultation!",
					JOptionPane.OK_OPTION
				) ==
				JOptionPane.OK_OPTION
			)
		) {
			return;
		}
		Doctor d = doctorService.createDoctor("", null);
		new DoctorEditJInternalFrame(
			d,
			() ->
				doctorTypeService.listDoctorTypes().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList(),
			editDialogComponentFactory,
			this,
			desktopPane
		);
	}

	private void cancel() {
		if (observer != null) {
			observer.onCancel();
		}
	}

	@Override
	public void onCancel() {}

	@Override
	public void onDelete(Doctor toDelete) {
		if (
			JOptionPane.showConfirmDialog(
				this,
				"Do you really want to delete this doctor?",
				"Delete Doctor",
				JOptionPane.YES_NO_OPTION
			) ==
			JOptionPane.YES_OPTION
		) {
			doctorService.deleteDoctor(toDelete.getId());
			updateTableSelection();
		}
	}

	@Override
	public void onSave(Doctor toSave) {
		doctorService.updateDoctor(toSave);
		updateTableSelection();
	}
}
