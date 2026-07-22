package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.NutritionClassCalculationService;
import de.ollie.healthtracker.core.service.OpenTaskService;
import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.ReportPrintService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.gui.swing.chart.bloodpressure.BloodPressureChartData;
import de.ollie.healthtracker.gui.swing.chart.bloodpressure.BloodPressureChartDataProvider;
import de.ollie.healthtracker.gui.swing.chart.bloodpressure.BloodPressureChartJInternalFrame;
import de.ollie.healthtracker.gui.swing.chart.bodytemperature.BodyTemperatureChartData;
import de.ollie.healthtracker.gui.swing.chart.bodytemperature.BodyTemperatureChartDataProvider;
import de.ollie.healthtracker.gui.swing.chart.bodytemperature.BodyTemperatureChartJInternalFrame;
import de.ollie.healthtracker.gui.swing.chart.nutrition.NutritionChartData;
import de.ollie.healthtracker.gui.swing.chart.nutrition.NutritionChartDataProvider;
import de.ollie.healthtracker.gui.swing.chart.nutrition.NutritionChartJInternalFrame;
import de.ollie.healthtracker.gui.swing.chart.weight.WeightChartData;
import de.ollie.healthtracker.gui.swing.chart.weight.WeightChartDataProvider;
import de.ollie.healthtracker.gui.swing.chart.weight.WeightChartJInternalFrame;
import de.ollie.healthtracker.gui.swing.event.BloodPressureMeasurementChangeNotifier;
import de.ollie.healthtracker.gui.swing.event.BodyTemperatureMeasurementChangeNotifier;
import de.ollie.healthtracker.gui.swing.event.MeatConsumptionChangeNotifier;
import de.ollie.healthtracker.gui.swing.event.NotifyingBloodPressureMeasurementService;
import de.ollie.healthtracker.gui.swing.event.NotifyingBodyTemperatureMeasurementService;
import de.ollie.healthtracker.gui.swing.event.NotifyingMeatConsumptionService;
import de.ollie.healthtracker.gui.swing.event.NotifyingWeightMeasurementService;
import de.ollie.healthtracker.gui.swing.event.WeightMeasurementChangeNotifier;
import de.ollie.healthtracker.gui.swing.external.viewer.pdf.ExternalPdfViewerStarter;
import de.ollie.healthtracker.gui.swing.frame.BoundsTrackingDesktopPane;
import de.ollie.healthtracker.gui.swing.frame.InternalFrameBoundsManager;
import de.ollie.healthtracker.gui.swing.opentask.OpenTaskJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.alcoholconsumption.AlcoholConsumptionSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.alcoholproduct.AlcoholProductSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement.BloodPressureMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.bodypart.BodyPartSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.bodytemperaturemeasurement.BodyTemperatureMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.comment.CommentSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.commenttype.CommentTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctor.DoctorSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctorconsultation.DoctorConsultationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.doctortype.DoctorTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.exercise.ExerciseSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.generalbodypart.GeneralBodyPartSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.manufacturer.ManufacturerSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meatconsumption.MeatConsumptionSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meatproduct.MeatProductSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meattype.MeatTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medication.MedicationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationlog.MedicationLogSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationplan.MedicationPlanSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationunit.MedicationUnitSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.pointofmeasurement.PointOfMeasurementSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.symptom.SymptomSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.weightmeasurement.WeightMeasurementSelectJInternalFrame;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

@Named
@RequiredArgsConstructor
public class HealthTrackerMainFrame extends JFrame implements ActionListener {

	/** Number of days shown on the x-axis of the blood pressure and weight charts (ending today), configurable via the {@code chart.window.days} property. */
	@Value("${chart.window.days:31}")
	private int chartWindowDays;

	/** Application version, injected from the (Maven-filtered) {@code app.version} property, i.e. the pom.xml project version. */
	@Value("${app.version}")
	private String appVersion;

	private final AlcoholConsumptionService alcoholConsumptionService;
	private final AlcoholProductService alcoholProductService;
	private final BloodPressureChartDataProvider bloodPressureChartDataProvider;
	private final BloodPressureMeasurementChangeNotifier bloodPressureMeasurementChangeNotifier;
	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final BodyPartService bodyPartService;
	private final BodyTemperatureChartDataProvider bodyTemperatureChartDataProvider;
	private final BodyTemperatureMeasurementChangeNotifier bodyTemperatureMeasurementChangeNotifier;
	private final BodyTemperatureMeasurementService bodyTemperatureMeasurementService;
	private final CommentService commentService;
	private final CommentTypeService commentTypeService;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;
	private final ExerciseService exerciseService;
	private final ExternalPdfViewerStarter externalPdfViewerStarter;
	private final GeneralBodyPartService generalBodyPartService;
	private final InternalFrameBoundsManager internalFrameBoundsManager;
	private final ManufacturerService manufacturerService;
	private final MeatConsumptionChangeNotifier meatConsumptionChangeNotifier;
	private final MeatConsumptionService meatConsumptionService;
	private final MeatProductService meatProductService;
	private final MeatTypeService meatTypeService;
	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;
	private final NutritionChartDataProvider nutritionChartDataProvider;
	private final NutritionClassCalculationService nutritionClassCalculationService;
	private final OpenTaskService openTaskService;
	private final PointOfMeasurementService pointOfMeasurementService;
	private final ReportPrintService reportPrintService;
	private final SymptomService symptomService;
	private final WeightMeasurementChangeNotifier weightMeasurementChangeNotifier;
	private final WeightChartDataProvider weightChartDataProvider;
	private final WeightMeasurementService weightMeasurementService;
	private final WhoBloodPressureClassificationService whoBloodPressureClassificationService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private JDesktopPane desktopPane;
	private JMenuItem menuItemDuplicateLastSymtoms;
	private JMenuItem menuItemEditAlcoholConsumption;
	private JMenuItem menuItemEditAlcoholProduct;
	private JMenuItem menuItemEditBloodPressureMeasurement;
	private JMenuItem menuItemEditBodyPart;
	private JMenuItem menuItemEditBodyTemperatureMeasurement;
	private JMenuItem menuItemEditComment;
	private JMenuItem menuItemEditCommentType;
	private JMenuItem menuItemEditDoctor;
	private JMenuItem menuItemEditDoctorConsultation;
	private JMenuItem menuItemEditDoctorType;
	private JMenuItem menuItemEditExercise;
	private JMenuItem menuItemEditGeneralBodyPart;
	private JMenuItem menuItemEditManufacturer;
	private JMenuItem menuItemEditMeatConsumption;
	private JMenuItem menuItemEditMeatProduct;
	private JMenuItem menuItemEditMeatType;
	private JMenuItem menuItemEditMedication;
	private JMenuItem menuItemEditMedicationLog;
	private JMenuItem menuItemEditMedicationPlan;
	private JMenuItem menuItemEditMedicationUnit;
	private JMenuItem menuItemEditWeightMeasurement;
	private JMenuItem menuItemEditPointOfMeasurement;
	private JMenuItem menuItemEditSymptom;
	private JMenuItem menuItemFilePrintBPM;
	private JMenuItem menuItemFilePrintHealthReportCurrentMonth;
	private JMenuItem menuItemFilePrintHealthReportPreviousMonth;
	private JMenuItem menuItemFilePrintMeatConsumptionStatistic;
	private JMenuItem menuItemFileQuit;
	private JMenuItem menuItemFileShowBloodPressureChart;
	private JMenuItem menuItemFileShowBodyTemperatureChart;
	private JMenuItem menuItemFileShowNutritionChart;
	private JMenuItem menuItemFileShowOpenTasks;
	private JMenuItem menuItemFileShowWeightChart;

	@PostConstruct
	void postConstruct() {
		desktopPane = new BoundsTrackingDesktopPane(internalFrameBoundsManager);
		desktopPane.setMinimumSize(new Dimension(200, 100));
		JPanel mainPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		mainPanel.add(desktopPane, BorderLayout.CENTER);
		setJMenuBar(createJMenuBar());
		setContentPane(mainPanel);
		// Route the title bar's close button through onExit() instead of a hard EXIT_ON_CLOSE.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					closeApplication();
				}
			}
		);
		// Restore bounds when the window is un-maximized ...
		setBounds(100, 100, 1400, 800);
		// ... but start maximized to fill the whole screen.
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

	void closeApplication() {
		internalFrameBoundsManager.save();
		System.exit(0);
	}

	public void showFrame() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Health-Tracker (Version %app.version%)".replace("%app.version%", appVersion));
		setVisible(true);
		// Once the maximized layout is applied and the desktop pane has its real size, open the three
		// standard charts and stack them on top of each other.
		SwingUtilities.invokeLater(this::openInitialCharts);
	}

	private void openInitialCharts() {
		JInternalFrame[] charts = {
			new WeightChartJInternalFrame(
				desktopPane,
				this::createWeightChartData,
				chartWindowDays,
				70.0,
				100.0,
				weightMeasurementChangeNotifier
			),
			new BloodPressureChartJInternalFrame(
				desktopPane,
				this::createBloodPressureChartData,
				chartWindowDays,
				bloodPressureMeasurementChangeNotifier
			),
			new BodyTemperatureChartJInternalFrame(
				desktopPane,
				this::createBodyTemperatureChartData,
				chartWindowDays,
				34.0,
				43.0,
				bodyTemperatureMeasurementChangeNotifier
			),
			new NutritionChartJInternalFrame(desktopPane, this::createNutritionChartData, meatConsumptionChangeNotifier),
		};
		int width = desktopPane.getWidth() / charts.length;
		int height = 600;
		for (int i = 0; i < charts.length; i++) {
			// Keep the default side-by-side layout only for charts without a remembered position from a previous run.
			if (!internalFrameBoundsManager.hasStoredBounds(charts[i].getClass().getSimpleName())) {
				charts[i].setBounds(i * width, 0, width, height);
			}
		}
		// Only pop up the open task dialog when there actually are open tasks to show.
		if (!openTaskService.findAllOpenTasks().isEmpty()) {
			new OpenTaskJInternalFrame(
				desktopPane,
				openTaskService,
				bloodPressureMeasurementChangeNotifier,
				bodyTemperatureMeasurementChangeNotifier,
				weightMeasurementChangeNotifier
			);
		}
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuItemFilePrintHealthReportPreviousMonth = createMenuItem("Health Report (Previous Month)", this);
		menu.add(menuItemFilePrintHealthReportPreviousMonth);
		menuItemFilePrintHealthReportCurrentMonth = createMenuItem("Health Report (Current Month)", this);
		menu.add(menuItemFilePrintHealthReportCurrentMonth);
		menu.add(new JSeparator());
		menuItemFilePrintBPM = createMenuItem("Print Blood Pressure Measurement", this);
		menu.add(menuItemFilePrintBPM);
		menuItemFilePrintMeatConsumptionStatistic = createMenuItem("Print Meat Consumption", this);
		menu.add(menuItemFilePrintMeatConsumptionStatistic);
		menu.add(new JSeparator());
		menuItemFileQuit = createMenuItem("Quit", this);
		menu.add(menuItemFileQuit);
		menuBar.add(menu);
		menu = new JMenu("Show");
		menuItemFileShowBloodPressureChart = createMenuItem("Show Blood Pressure Chart", this);
		menu.add(menuItemFileShowBloodPressureChart);
		menuItemFileShowBodyTemperatureChart = createMenuItem("Show Body Temperature Chart", this);
		menu.add(menuItemFileShowBodyTemperatureChart);
		menuItemFileShowNutritionChart = createMenuItem("Show Nutrition Chart", this);
		menu.add(menuItemFileShowNutritionChart);
		menuItemFileShowWeightChart = createMenuItem("Show Weight Chart", this);
		menu.add(menuItemFileShowWeightChart);
		menu.add(new JSeparator());
		menuItemFileShowOpenTasks = createMenuItem("Show Open Tasks", this);
		menu.add(menuItemFileShowOpenTasks);
		menuBar.add(menu);
		menu = new JMenu("Edit");
		menuItemEditBloodPressureMeasurement = createMenuItem("Blood Pressure Measurement", this);
		menu.add(menuItemEditBloodPressureMeasurement);
		menuItemEditComment = createMenuItem("Comment", this);
		menu.add(menuItemEditComment);
		menuItemEditCommentType = createMenuItem("Comment Type", this);
		menu.add(menuItemEditCommentType);
		menuItemEditSymptom = createMenuItem("Symptom", this);
		menu.add(menuItemEditSymptom);
		menuItemEditWeightMeasurement = createMenuItem("Weight Measurement", this);
		menu.add(menuItemEditWeightMeasurement);
		menu.add(new JSeparator());
		JMenu subMenu = new JMenu("Alcohol");
		menuItemEditAlcoholConsumption = createMenuItem("Alcohol Consumption", this);
		subMenu.add(menuItemEditAlcoholConsumption);
		menuItemEditAlcoholProduct = createMenuItem("Alcohol Product", this);
		subMenu.add(menuItemEditAlcoholProduct);
		menu.add(subMenu);
		subMenu = new JMenu("Body & Exercise");
		menuItemEditBodyPart = createMenuItem("Body Part", this);
		subMenu.add(menuItemEditBodyPart);
		menuItemEditExercise = createMenuItem("Exercise", this);
		subMenu.add(menuItemEditExercise);
		menuItemEditGeneralBodyPart = createMenuItem("General Body Part", this);
		subMenu.add(menuItemEditGeneralBodyPart);
		menu.add(subMenu);
		subMenu = new JMenu("Body Temperature");
		menuItemEditBodyTemperatureMeasurement = createMenuItem("Body Temperature Measurement", this);
		subMenu.add(menuItemEditBodyTemperatureMeasurement);
		menuItemEditPointOfMeasurement = createMenuItem("Point Of Measurement", this);
		subMenu.add(menuItemEditPointOfMeasurement);
		menu.add(subMenu);
		subMenu = new JMenu("Doctor");
		menuItemEditDoctor = createMenuItem("Doctor", this);
		subMenu.add(menuItemEditDoctor);
		menuItemEditDoctorConsultation = createMenuItem("Doctor Consultation", this);
		subMenu.add(menuItemEditDoctorConsultation);
		menuItemEditDoctorType = createMenuItem("Doctor Type", this);
		subMenu.add(menuItemEditDoctorType);
		menu.add(subMenu);
		subMenu = new JMenu("Meat");
		menuItemEditMeatConsumption = createMenuItem("Meat Consumption", this);
		subMenu.add(menuItemEditMeatConsumption);
		menuItemEditMeatProduct = createMenuItem("Meat Product", this);
		subMenu.add(menuItemEditMeatProduct);
		menuItemEditMeatType = createMenuItem("Meat Type", this);
		subMenu.add(menuItemEditMeatType);
		menu.add(subMenu);
		subMenu = new JMenu("Medication");
		menuItemEditManufacturer = createMenuItem("Manufacturer", this);
		subMenu.add(menuItemEditManufacturer);
		menuItemEditMedication = createMenuItem("Medication", this);
		subMenu.add(menuItemEditMedication);
		menuItemEditMedicationLog = createMenuItem("Medication Log", this);
		subMenu.add(menuItemEditMedicationLog);
		menuItemEditMedicationPlan = createMenuItem("Medication Plan", this);
		subMenu.add(menuItemEditMedicationPlan);
		menuItemEditMedicationUnit = createMenuItem("Medication Unit", this);
		subMenu.add(menuItemEditMedicationUnit);
		menu.add(subMenu);
		menu.add(new JSeparator());
		menuItemDuplicateLastSymtoms = createMenuItem("Duplicate Last Symptoms", this);
		menu.add(menuItemDuplicateLastSymtoms);
		menuBar.add(menu);
		menuBar.add(createWindowMenu());
		return menuBar;
	}

	/**
	 * Creates the "Window" menu. It is (re)populated with the internal frames currently open on the desktop pane every
	 * time it is selected, so it always reflects the current state. Selecting an entry brings the corresponding frame to
	 * the front.
	 */
	private JMenu createWindowMenu() {
		JMenu windowMenu = new JMenu("Window");
		windowMenu.addMenuListener(
			new MenuListener() {
				@Override
				public void menuSelected(MenuEvent e) {
					windowMenu.removeAll();
					JInternalFrame[] frames = desktopPane.getAllFrames();
					if (frames.length == 0) {
						JMenuItem emptyItem = new JMenuItem("(no windows open)");
						emptyItem.setEnabled(false);
						windowMenu.add(emptyItem);
						return;
					}
					Arrays.sort(frames, Comparator.comparing(JInternalFrame::getTitle, String.CASE_INSENSITIVE_ORDER));
					for (JInternalFrame frame : frames) {
						JMenuItem item = new JMenuItem(frame.getTitle());
						item.addActionListener(ev -> bringToFront(frame));
						windowMenu.add(item);
					}
				}

				@Override
				public void menuDeselected(MenuEvent e) {
					// nothing to do
				}

				@Override
				public void menuCanceled(MenuEvent e) {
					// nothing to do
				}
			}
		);
		return windowMenu;
	}

	/** Brings the given internal frame to the front, de-iconifying and selecting it. */
	private void bringToFront(JInternalFrame frame) {
		try {
			if (frame.isIcon()) {
				frame.setIcon(false);
			}
			frame.moveToFront();
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
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
		} else if (e.getSource() == menuItemEditAlcoholConsumption) {
			new AlcoholConsumptionSelectJInternalFrame(
				alcoholConsumptionService,
				alcoholProductService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditAlcoholProduct) {
			new AlcoholProductSelectJInternalFrame(alcoholProductService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditBloodPressureMeasurement) {
			new BloodPressureMeasurementSelectJInternalFrame(
				new NotifyingBloodPressureMeasurementService(
					bloodPressureMeasurementService,
					bloodPressureMeasurementChangeNotifier
				),
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditBodyTemperatureMeasurement) {
			new BodyTemperatureMeasurementSelectJInternalFrame(
				new NotifyingBodyTemperatureMeasurementService(
					bodyTemperatureMeasurementService,
					bodyTemperatureMeasurementChangeNotifier
				),
				pointOfMeasurementService,
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
				new NotifyingMeatConsumptionService(meatConsumptionService, meatConsumptionChangeNotifier),
				meatProductService,
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemEditMeatProduct) {
			new MeatProductSelectJInternalFrame(meatProductService, meatTypeService, desktopPane, editDialogComponentFactory);
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
				medicationLogService,
				medicationService,
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
		} else if (e.getSource() == menuItemEditPointOfMeasurement) {
			new PointOfMeasurementSelectJInternalFrame(pointOfMeasurementService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditSymptom) {
			new SymptomSelectJInternalFrame(symptomService, bodyPartService, desktopPane, editDialogComponentFactory);
		} else if (e.getSource() == menuItemEditWeightMeasurement) {
			new WeightMeasurementSelectJInternalFrame(
				new NotifyingWeightMeasurementService(weightMeasurementService, weightMeasurementChangeNotifier),
				desktopPane,
				editDialogComponentFactory
			);
		} else if (e.getSource() == menuItemFilePrintBPM) {
			//			LocalDate now = LocalDate.now();
			//			byte[] pdf = reportPrintService.printForTimeInterval(
			//				now.withDayOfMonth(1),
			//				now.withDayOfMonth(now.lengthOfMonth()),
			//				"jasper",
			//				new HashMap<>()
			//			);
			//			try {
			//				externalPdfViewerStarter.show(pdf);
			//			} catch (Exception ex) {
			//				ex.printStackTrace();
			//			}
			System.out.println("Datum      Uhrzeit Dia Sys PPM IHB Status");
			System.out.println("-----------------------------------------");
			//JJJJ-MM-DD HH:MM   XXX XXX XXX X
			bloodPressureMeasurementService
				.findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
					LocalDate.now().minusMonths(2),
					LocalDate.now().plusDays(1)
				)
				.forEach(bpm ->
					System.out.println(
						String.format(
							"%s %s   %3d %3d %3d %s %s",
							bpm.getDateOfRecording().toString(),
							bpm.getTimeOfRecording().toString(),
							bpm.getDiaMmHg(),
							bpm.getSysMmHg(),
							bpm.getPulsePerMinute(),
							(bpm.isIrregularHeartbeat() ? " X " : "   "),
							whoBloodPressureClassificationService.calculateClassification(bpm.getSysMmHg(), bpm.getDiaMmHg()).name()
						)
					)
				);
		} else if (e.getSource() == menuItemFilePrintHealthReportCurrentMonth) {
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
		} else if (e.getSource() == menuItemFilePrintHealthReportPreviousMonth) {
			LocalDate now = LocalDate.now().minusMonths(1);
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
		} else if (e.getSource() == menuItemFilePrintMeatConsumptionStatistic) {
			Map<LocalDate, MeatConsumptionStaticRecord> dataPerDay = new HashMap<>();
			meatConsumptionService
				.listMeatConsumptions()
				.forEach(mc -> {
					if (!dataPerDay.containsKey(mc.getDateOfRecording())) {
						dataPerDay.put(mc.getDateOfRecording(), new MeatConsumptionStaticRecord());
					}
					dataPerDay
						.get(mc.getDateOfRecording())
						.addTotalDays(VGAP)
						.addFish(mc.getMeatProduct().getMeatType().getCategory() == MeatCategory.FISH ? 1 : 0)
						.addMeat(mc.getMeatProduct().getMeatType().getCategory() == MeatCategory.MEAT ? 1 : 0);
				});
			Map<YearMonth, MeatConsumptionStaticRecord> dataPerMonth = new HashMap<>();
			FirstDayProvider firstDay = new FirstDayProvider();
			dataPerDay
				.entrySet()
				.stream()
				.sorted((dpd0, dpd1) -> dpd0.getKey().compareTo(dpd1.getKey()))
				.forEach(dpd -> {
					firstDay.setIfNotSet(dpd.getKey());
					YearMonth key = YearMonth.of(dpd.getKey().getYear(), dpd.getKey().getMonthValue());
					if (!dataPerMonth.containsKey(key)) {
						dataPerMonth.put(key, new MeatConsumptionStaticRecord());
					}
					dataPerMonth
						.get(key)
						.addFish(dpd.getValue().getFish() > 0 && !(dpd.getValue().getMeat() > 0) ? 1 : 0)
						.addMeat((dpd.getValue().getMeat() > 0) ? 1 : 0);
				});
			LocalDate currentDay = firstDay.getFirstDay();
			LocalDate now = LocalDate.now().plusDays(1);
			while (now.isAfter(currentDay)) {
				System.out.println(
					currentDay +
					" - " +
					nutritionClassCalculationService.calculate(createNutritionCalculationData(dataPerDay.get(currentDay)))
				);
				currentDay = currentDay.plusDays(1);
			}
			dataPerMonth
				.entrySet()
				.stream()
				.sorted((dpm0, dpm1) -> compareYearMonth(dpm0.getKey(), dpm1.getKey()))
				.forEach(dpm -> {
					System.out.println("\n" + dpm.getKey());
					System.out.println("- Total:   " + dpm.getKey().getMonth().maxLength());
					System.out.println("- Meat:    " + dpm.getValue().getMeat());
					System.out.println("- Fish:    " + dpm.getValue().getFish());
					System.out.println(
						"- VEGGIE:  " + (dpm.getKey().getMonth().maxLength() - dpm.getValue().getMeat() - dpm.getValue().getFish())
					);
				});
		} else if (e.getSource() == menuItemFileShowBloodPressureChart) {
			new BloodPressureChartJInternalFrame(
				desktopPane,
				this::createBloodPressureChartData,
				chartWindowDays,
				bloodPressureMeasurementChangeNotifier
			);
		} else if (e.getSource() == menuItemFileShowBodyTemperatureChart) {
			new BodyTemperatureChartJInternalFrame(
				desktopPane,
				this::createBodyTemperatureChartData,
				chartWindowDays,
				34.0,
				43.0,
				bodyTemperatureMeasurementChangeNotifier
			);
		} else if (e.getSource() == menuItemFileShowOpenTasks) {
			new OpenTaskJInternalFrame(
				desktopPane,
				openTaskService,
				bloodPressureMeasurementChangeNotifier,
				bodyTemperatureMeasurementChangeNotifier,
				weightMeasurementChangeNotifier
			);
		} else if (e.getSource() == menuItemFileShowNutritionChart) {
			new NutritionChartJInternalFrame(desktopPane, this::createNutritionChartData, meatConsumptionChangeNotifier);
		} else if (e.getSource() == menuItemFileShowWeightChart) {
			new WeightChartJInternalFrame(
				desktopPane,
				this::createWeightChartData,
				chartWindowDays,
				70.0,
				100.0,
				weightMeasurementChangeNotifier
			);
		} else if (e.getSource() == menuItemFileQuit) {
			closeApplication();
		}
	}

	private List<WeightChartData> createWeightChartData() {
		return weightChartDataProvider.create(chartWindowDays);
	}

	private List<BodyTemperatureChartData> createBodyTemperatureChartData() {
		return bodyTemperatureChartDataProvider.create(chartWindowDays);
	}

	private List<BloodPressureChartData> createBloodPressureChartData() {
		return bloodPressureChartDataProvider.create(chartWindowDays);
	}

	private List<NutritionChartData> createNutritionChartData() {
		return nutritionChartDataProvider.create();
	}

	private NutritionCalculationData createNutritionCalculationData(MeatConsumptionStaticRecord mcsr) {
		return mcsr == null
			? null
			: new NutritionCalculationData().setFishConsumptionDays(mcsr.getFish()).setMeatConsumptionDays(mcsr.getMeat());
	}

	@Accessors(chain = true)
	@Data
	private static class MeatConsumptionStaticRecord {

		private int totalDays = 1;
		private int fish = 0;
		private int meat = 0;

		MeatConsumptionStaticRecord addTotalDays(int toAdd) {
			totalDays += toAdd;
			return this;
		}

		MeatConsumptionStaticRecord addFish(int toAdd) {
			fish += toAdd;
			return this;
		}

		MeatConsumptionStaticRecord addMeat(int toAdd) {
			meat += toAdd;
			return this;
		}
	}

	private static class FirstDayProvider {

		@Getter
		private LocalDate firstDay;

		FirstDayProvider setIfNotSet(LocalDate firstDay) {
			if (this.firstDay == null) {
				this.firstDay = firstDay;
			}
			return this;
		}
	}

	private int compareYearMonth(YearMonth ym0, YearMonth ym1) {
		int r = ym0.getYear() - ym1.getYear();
		return r != 0 ? r : ym0.getMonthValue() - ym1.getMonthValue();
	}
}
