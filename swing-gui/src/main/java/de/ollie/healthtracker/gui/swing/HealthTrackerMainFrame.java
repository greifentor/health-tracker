package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement.BloodPressureMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.comment.CommentSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctor.DoctorSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctorconsultation.DoctorConsultationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationlog.MedicationLogSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.symptom.SymptomSelectJInternalFrame;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class HealthTrackerMainFrame extends JFrame implements ActionListener {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final CommentService commentService;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;
	private final MedicationLogService medicationLogService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;
	private final SymptomService symptomService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private JDesktopPane desktopPane;
	private JMenuItem menuItemFileQuit;
	private JMenuItem menuItemEditBloodPressureMeasurement;
	private JMenuItem menuItemEditComment;
	private JMenuItem menuItemEditDoctor;
	private JMenuItem menuItemEditDoctorConsultation;
	private JMenuItem menuItemEditMedicationLog;
	private JMenuItem menuItemEditSymptom;

	@PostConstruct
	void postConstruct() {
		desktopPane = new JDesktopPane();
		desktopPane.setMinimumSize(new Dimension(200, 100));
		JPanel mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(createJMenuBar());
		setContentPane(mainPanel);
		setBounds(100, 100, 800, 600);
	}

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Health-Tracker");
		setVisible(true);
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuItemFileQuit = createMenuItem("Quit", this);
		menu.add(menuItemFileQuit);
		menuBar.add(menu);
		menu = new JMenu("Edit");
		menuItemEditBloodPressureMeasurement = createMenuItem("Blood Pressure Measurement", this);
		menu.add(menuItemEditBloodPressureMeasurement);
		menuItemEditComment = createMenuItem("Comment", this);
		menu.add(menuItemEditComment);
		menuItemEditDoctor = createMenuItem("Doctor", this);
		menu.add(menuItemEditDoctor);
		menuItemEditDoctorConsultation = createMenuItem("Doctor Consultation", this);
		menu.add(menuItemEditDoctorConsultation);
		menuItemEditMedicationLog = createMenuItem("Medication Log", this);
		menu.add(menuItemEditMedicationLog);
		menuItemEditSymptom = createMenuItem("Symptom", this);
		menu.add(menuItemEditSymptom);
		menuBar.add(menu);
		return menuBar;
	}

	private JMenuItem createMenuItem(String text, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItemEditBloodPressureMeasurement) {
			new BloodPressureMeasurementSelectJInternalFrame(
				bloodPressureMeasurementService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditComment) {
			new CommentSelectJInternalFrame(commentService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditDoctor) {
			new DoctorSelectJInternalFrame(doctorService, doctorTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditDoctorConsultation) {
			new DoctorConsultationSelectJInternalFrame(
				doctorConsultationService,
				doctorService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditMedicationLog) {
			new MedicationLogSelectJInternalFrame(
				medicationService,
				medicationLogService,
				medicationUnitService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditSymptom) {
			new SymptomSelectJInternalFrame(symptomService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemFileQuit) {
			System.out.println("Cancel");
			System.exit(0);
		}
	}
}
