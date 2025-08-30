package de.ollie.healthtracker.gui.swing.edit.medicationlog;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class MedicationLogEditPanel extends AbstractEditPanel<MedicationLog> {

	public static final String MEDICATIONS_PROVIDER_ID = "medications-item-provider";
	public static final String MEDICATION_UNITS_PROVIDER_ID = "medication-units-item-provider";

	private JComboBox<Medication> comboBoxMedication;
	private JComboBox<MedicationUnit> comboBoxMedicationUnit;
	private JSpinner spinnerUnitCount;
	private JTextField textFieldDateOfIntake;
	private JTextField textFieldTimeOfIntake;

	public MedicationLogEditPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date of Intake:", "Time of Intake:", "Medication:", "Medication Units:", "Unit Count:");
	}

	@Override
	protected JPanel createComponentPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(5, 1, HGAP, VGAP));
		textFieldDateOfIntake = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfIntake()), 50);
		textFieldTimeOfIntake = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfIntake()), 50);
		List<Medication> medications = ((ItemProvider<Medication>) itemProviders.get(MEDICATIONS_PROVIDER_ID)).getItem();
		comboBoxMedication = new JComboBox<>(medications.toArray(new Medication[medications.size()]));
		comboBoxMedication.setSelectedItem(toEdit.getMedication());
		comboBoxMedication.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName() + " (" + value.getName() + ")");
			}
			return new JLabel("-");
		});
		List<MedicationUnit> medicationUnits =
			((ItemProvider<MedicationUnit>) itemProviders.get(MEDICATION_UNITS_PROVIDER_ID)).getItem();
		comboBoxMedicationUnit = new JComboBox<>(medicationUnits.toArray(new MedicationUnit[medicationUnits.size()]));
		comboBoxMedicationUnit.setSelectedItem(toEdit.getMedicationUnit());
		comboBoxMedicationUnit.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName() + " (" + value.getName() + ")");
			}
			return new JLabel("-");
		});
		spinnerUnitCount = new JSpinner(new SpinnerNumberModel(toEdit.getUnitCount().doubleValue(), 0, 100, 0.1));
		p.add(textFieldDateOfIntake);
		p.add(textFieldTimeOfIntake);
		p.add(comboBoxMedication);
		p.add(comboBoxMedicationUnit);
		p.add(spinnerUnitCount);
		return p;
	}

	@Override
	public MedicationLog getCurrentContent() {
		return new MedicationLog()
			.setDateOfIntake(DateTimeUtil.dateFromString(textFieldDateOfIntake.getText()))
			.setId(toEdit.getId())
			.setMedication((Medication) comboBoxMedication.getSelectedItem())
			.setMedicationUnit((MedicationUnit) comboBoxMedicationUnit.getSelectedItem())
			.setTimeOfIntake(DateTimeUtil.timeFromString(textFieldTimeOfIntake.getText()))
			.setUnitCount(new BigDecimal(((Number) spinnerUnitCount.getValue()).doubleValue()));
	}
}
