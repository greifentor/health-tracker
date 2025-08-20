package de.ollie.healthtracker.gui.swing.select;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import de.ollie.healthtracker.gui.swing.edit.DoctorConsultationEditJInternalFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DoctorConsultationSelectPanel
	extends JPanel
	implements BaseEditInternalFrame.Observer<DoctorConsultation> {

	public interface Observer {
		void onCancel();
	}

	private final JDesktopPane desktopPane;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private List<DoctorConsultation> doctorConsultations;
	private JTable tableSelection;
	private Observer observer;

	public DoctorConsultationSelectPanel(
		DoctorConsultationService doctorConsultationService,
		DoctorService doctorService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer observer
	) {
		super(new BorderLayout(HGAP, VGAP));
		this.desktopPane = desktopPane;
		this.doctorService = doctorService;
		this.doctorConsultationService = doctorConsultationService;
		this.editDialogComponentFactory = editDialogComponentFactory;
		this.observer = observer;
		doctorConsultations = getDoctorConsultation();
		add(createSelectionPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
	}

	private void updateTableSelection() {
		doctorConsultations = getDoctorConsultation();
		tableSelection.setModel(new DoctorConsultationSelectionTableModel(doctorConsultations));
	}

	private List<DoctorConsultation> getDoctorConsultation() {
		return doctorConsultationService
			.listDoctorConsultations()
			.stream()
			.sorted((dc0, dc1) -> compare(dc0, dc1))
			.toList();
	}

	private int compare(DoctorConsultation dc0, DoctorConsultation dc1) {
		int r = dc0.getDate().compareTo(dc1.getDate());
		if (r == 0) {
			r = dc0.getTime().compareTo(dc1.getTime());
			if (r == 0) {
				r = dc0.getDoctor().getName().compareTo(dc1.getDoctor().getName());
			}
		}
		return r;
	}

	private JPanel createButtonPanel() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		p.add(createCancelButton(() -> cancel()));
		p.add(new JLabel("     "));
		p.add(createSelectButton(() -> select()));
		return p;
	}

	private JPanel createSelectionPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		tableSelection = new JTable(new DoctorConsultationSelectionTableModel(doctorConsultations));
		p.add(tableSelection, BorderLayout.CENTER);
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
			DoctorConsultation dc = doctorConsultations.get(selectedIndices[i]);
			new DoctorConsultationEditJInternalFrame(
				dc,
				() -> doctorService.listDoctors().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList(),
				editDialogComponentFactory,
				this,
				desktopPane
			);
		}
	}

	private void cancel() {
		if (observer != null) {
			observer.onCancel();
		}
	}

	@Override
	public void onCancel() {}

	@Override
	public void onDelete(DoctorConsultation toDelete) {
		if (
			JOptionPane.showConfirmDialog(
				this,
				"Delete Doctor Consultation",
				"Do you really want to delete this doctor consultation?",
				JOptionPane.YES_NO_OPTION
			) ==
			JOptionPane.YES_OPTION
		) {
			doctorConsultationService.deleteDoctorConsultation(toDelete.getId());
			updateTableSelection();
		}
	}

	@Override
	public void onSave(DoctorConsultation toSave) {
		doctorConsultationService.updateDoctorConsultation(toSave);
		updateTableSelection();
	}
}
