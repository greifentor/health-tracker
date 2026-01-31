package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.ReportPrintService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.gui.swing.external.viewer.pdf.ExternalPdfViewerStarter;
import de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement.BloodPressureMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.bodypart.BodyPartSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.comment.CommentSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.commenttype.CommentTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctor.DoctorSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctorconsultation.DoctorConsultationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctortype.DoctorTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.exercise.ExerciseSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.generalbodypart.GeneralBodyPartSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.manufacturer.ManufacturerSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meatconsumption.MeatConsumptionSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meattype.MeatTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medication.MedicationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationlog.MedicationLogSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationplan.MedicationPlanSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationunit.MedicationUnitSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.symptom.SymptomSelectJInternalFrame;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class HealthTrackerMainFrame extends JFrame implements ActionListener {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BodyPartService bodyPartService;
	private final CommentService commentService;
	private final CommentTypeService commentTypeService;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;
	private final ExerciseService exerciseService;
	private final ExternalPdfViewerStarter externalPdfViewerStarter;
	private final GeneralBodyPartService generalBodyPartService;
	private final ManufacturerService manufacturerService;
	private final MeatConsumptionService meatConsumptionService;
	private final MeatTypeService meatTypeService;
	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;
	private final ReportPrintService reportPrintService;
	private final SymptomService symptomService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private JDesktopPane desktopPane;
	private JMenuItem menuItemDuplicateLastSymtoms;
	private JMenuItem menuItemEditBloodPressureMeasurement;
	private JMenuItem menuItemEditBodyPart;
	private JMenuItem menuItemEditComment;
	private JMenuItem menuItemEditCommentType;
	private JMenuItem menuItemEditDoctor;
	private JMenuItem menuItemEditDoctorConsultation;
	private JMenuItem menuItemEditDoctorType;
	private JMenuItem menuItemEditExercise;
	private JMenuItem menuItemEditGeneralBodyPart;
	private JMenuItem menuItemEditManufacturer;
	private JMenuItem menuItemEditMeatConsumption;
	private JMenuItem menuItemEditMeatType;
	private JMenuItem menuItemEditMedication;
	private JMenuItem menuItemEditMedicationLog;
	private JMenuItem menuItemEditMedicationPlan;
	private JMenuItem menuItemEditMedicationUnit;
	private JMenuItem menuItemEditSymptom;
	private JMenuItem menuItemFilePrint;
	private JMenuItem menuItemFileQuit;

	@PostConstruct
	void postConstruct() {
		desktopPane = new JDesktopPane();
		desktopPane.setMinimumSize(new Dimension(200, 100));
		JPanel mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(createJMenuBar());
		setContentPane(mainPanel);
		setBounds(100, 100, 800, 800);
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
		menuItemFilePrint = createMenuItem("Print", this);
		menu.add(menuItemFilePrint);
		menu.add(new JSeparator());
		menuItemFileQuit = createMenuItem("Quit", this);
		menu.add(menuItemFileQuit);
		menuBar.add(menu);
		menu = new JMenu("Edit");
		menuItemEditBloodPressureMeasurement = createMenuItem("Blood Pressure Measurement", this);
		menu.add(menuItemEditBloodPressureMeasurement);
		menuItemEditBodyPart = createMenuItem("Body Part", this);
		menu.add(menuItemEditBodyPart);
		menuItemEditComment = createMenuItem("Comment", this);
		menu.add(menuItemEditComment);
		menuItemEditCommentType = createMenuItem("Comment Type", this);
		menu.add(menuItemEditCommentType);
		menuItemEditDoctor = createMenuItem("Doctor", this);
		menu.add(menuItemEditDoctor);
		menuItemEditDoctorType = createMenuItem("Doctor Type", this);
		menu.add(menuItemEditDoctorType);
		menuItemEditDoctorConsultation = createMenuItem("Doctor Consultation", this);
		menu.add(menuItemEditDoctorConsultation);
		menuItemEditGeneralBodyPart = createMenuItem("General Body Part", this);
		menu.add(menuItemEditGeneralBodyPart);
		menuItemEditExercise = createMenuItem("Exercise", this);
		menu.add(menuItemEditExercise);
		menuItemEditManufacturer = createMenuItem("Manufacturer", this);
		menu.add(menuItemEditManufacturer);
		menuItemEditMedication = createMenuItem("Medication", this);
		menu.add(menuItemEditMedication);
		menuItemEditMedicationLog = createMenuItem("Medication Log", this);
		menu.add(menuItemEditMedicationLog);
		menuItemEditMedicationPlan = createMenuItem("Medication Plan", this);
		menu.add(menuItemEditMedicationPlan);
		menuItemEditMedicationUnit = createMenuItem("Medication Unit", this);
		menu.add(menuItemEditMedicationUnit);
		menuItemEditMeatConsumption = createMenuItem("Meat Consumption", this);
		menu.add(menuItemEditMeatConsumption);
		menuItemEditMeatType = createMenuItem("Meat Type", this);
		menu.add(menuItemEditMeatType);
		menuItemEditSymptom = createMenuItem("Symptom", this);
		menu.add(menuItemEditSymptom);
		menuItemDuplicateLastSymtoms = createMenuItem("Duplicate Last Symptoms", this);
		menu.add(menuItemDuplicateLastSymtoms);
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
		if (e.getSource() == menuItemDuplicateLastSymtoms) {
			symptomService.duplicateNewestSymptomEntries();
			new SymptomSelectJInternalFrame(symptomService, bodyPartService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditBloodPressureMeasurement) {
			new BloodPressureMeasurementSelectJInternalFrame(
				bloodPressureMeasurementService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditBodyPart) {
			new BodyPartSelectJInternalFrame(
				bodyPartService,
				generalBodyPartService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditComment) {
			new CommentSelectJInternalFrame(commentService, commentTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditCommentType) {
			new CommentTypeSelectJInternalFrame(commentTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditDoctor) {
			new DoctorSelectJInternalFrame(doctorService, doctorTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditDoctorConsultation) {
			new DoctorConsultationSelectJInternalFrame(
				doctorConsultationService,
				doctorService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditDoctorType) {
			new DoctorTypeSelectJInternalFrame(doctorTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditExercise) {
			new ExerciseSelectJInternalFrame(exerciseService, bodyPartService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditGeneralBodyPart) {
			new GeneralBodyPartSelectJInternalFrame(generalBodyPartService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditManufacturer) {
			new ManufacturerSelectJInternalFrame(manufacturerService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditMeatConsumption) {
			new MeatConsumptionSelectJInternalFrame(
				meatConsumptionService,
				meatTypeService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditMeatType) {
			new MeatTypeSelectJInternalFrame(meatTypeService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditMedication) {
			new MedicationSelectJInternalFrame(
				medicationService,
				manufacturerService,
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
		} else if (e.getSource() == menuItemEditMedicationPlan) {
			new MedicationPlanSelectJInternalFrame(
				medicationPlanService,
				medicationService,
				medicationUnitService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditMedicationUnit) {
			new MedicationUnitSelectJInternalFrame(medicationUnitService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditSymptom) {
			new SymptomSelectJInternalFrame(symptomService, bodyPartService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemFilePrint) {
			LocalDate now = LocalDate.now();
			byte[] pdf = reportPrintService.printForTimeInterval(
				now.withDayOfMonth(1),
				now.withDayOfMonth(now.lengthOfMonth()),
				"jasper",
				new HashMap<>()
			);
			try {
				externalPdfViewerStarter.show(pdf);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == menuItemFileQuit) {
			System.exit(0);
		}
	}
}
