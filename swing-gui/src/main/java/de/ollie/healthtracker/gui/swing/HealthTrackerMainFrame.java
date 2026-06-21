package de.ollie.healthtracker.gui.swing;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.AlcoholProductService;
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
import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.NutritionClassCalculationService;
import de.ollie.healthtracker.core.service.ReportPrintService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.NutritionCalculationData;
import de.ollie.healthtracker.gui.swing.external.viewer.pdf.ExternalPdfViewerStarter;
import de.ollie.healthtracker.gui.swing.select.alcoholconsumption.AlcoholConsumptionSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.alcoholproduct.AlcoholProductSelectJInternalFrame;
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
import de.ollie.healthtracker.gui.swing.select.meatproduct.MeatProductSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.meattype.MeatTypeSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medication.MedicationSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationlog.MedicationLogSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationplan.MedicationPlanSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.medicationunit.MedicationUnitSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.symptom.SymptomSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.weightmeasurement.WeightMeasurementSelectJInternalFrame;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Named
@RequiredArgsConstructor
public class HealthTrackerMainFrame extends JFrame implements ActionListener {

	private final AlcoholConsumptionService alcoholConsumptionService;
	private final AlcoholProductService alcoholProductService;
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
	private final MeatProductService meatProductService;
	private final MeatTypeService meatTypeService;
	private final MedicationLogService medicationLogService;
	private final MedicationPlanService medicationPlanService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;
	private final NutritionClassCalculationService nutritionClassCalculationService;
	private final ReportPrintService reportPrintService;
	private final SymptomService symptomService;
	private final WeightMeasurementService weightMeasurementService;
	private final WhoBloodPressureClassificationService whoBloodPressureClassificationService;
	private final EditDialogComponentFactory editDialogComponentFactory;

	private JDesktopPane desktopPane;
	private JMenuItem menuItemDuplicateLastSymtoms;
	private JMenuItem menuItemEditAlcoholConsumption;
	private JMenuItem menuItemEditAlcoholProduct;
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
	private JMenuItem menuItemEditMeatProduct;
	private JMenuItem menuItemEditMeatType;
	private JMenuItem menuItemEditMedication;
	private JMenuItem menuItemEditMedicationLog;
	private JMenuItem menuItemEditMedicationPlan;
	private JMenuItem menuItemEditMedicationUnit;
	private JMenuItem menuItemEditWeightMeasurement;
	private JMenuItem menuItemEditSymptom;
	private JMenuItem menuItemFilePrintBPM;
	private JMenuItem menuItemFilePrintHealthReportCurrentMonth;
	private JMenuItem menuItemFilePrintHealthReportPreviousMonth;
	private JMenuItem menuItemFilePrintMeatConsumptionStatistic;
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
		} else if (e.getSource() == menuItemEditWeightMeasurement) {
			new WeightMeasurementSelectJInternalFrame(weightMeasurementService, desktopPane, editDialogComponentFactory);
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
		} else if (e.getSource() == menuItemFileQuit) {
			System.exit(0);
		}
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
