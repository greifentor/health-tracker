package de.ollie.healthtracker.gui.swing.edit.medicationplan;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import lombok.Generated;

@Generated
public class MedicationPlanEditJPanel extends AbstractEditPanel<MedicationPlan> {

	public static final String MEDICATION_ITEM_PROVIDER_ID = "medication-item-provider";
	public static final String MEDICATION_UNIT_ITEM_PROVIDER_ID = "medication-unit-item-provider";

	private JTextField textFieldStartDate;
	private JTextField textFieldEndDate;
	private JTextField textFieldNextDateOfIntake;
	private JTextField textFieldTimeOfIntake;
	private JComboBox<Medication> comboBoxMedication;
	private JComboBox<MedicationUnit> comboBoxMedicationUnit;
	private JSpinner spinnerUnitCount;
	private JCheckBox checkBoxSelfMedication;

	public MedicationPlanEditJPanel(MedicationPlan toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel(
			"Start Date:",
			"End Date:",
			"Next Date Of Intake:",
			"Time Of Intake:",
			"Medication:",
			"Medication Unit:",
			"Unit Count:",
			"Self Medication:"
		);
	}

	@Override
	protected JPanel createComponentPanel(MedicationPlan toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(8, 1, HGAP, VGAP));
		textFieldStartDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getStartDate()), 40);
		p.add(textFieldStartDate);
		textFieldEndDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getEndDate()), 40);
		p.add(textFieldEndDate);
		textFieldNextDateOfIntake = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getNextDateOfIntake()), 40);
		p.add(textFieldNextDateOfIntake);
		textFieldTimeOfIntake = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfIntake()), 40);
		p.add(textFieldTimeOfIntake);
		List<Medication> listMedication =
			((ItemProvider<Medication>) itemProviders.get(MEDICATION_ITEM_PROVIDER_ID)).getItem();
		comboBoxMedication = new JComboBox<>(listMedication.toArray(new Medication[listMedication.size()]));
		comboBoxMedication.setSelectedItem(toEdit.getMedication());
		comboBoxMedication.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxMedication);
		List<MedicationUnit> listMedicationUnit =
			((ItemProvider<MedicationUnit>) itemProviders.get(MEDICATION_UNIT_ITEM_PROVIDER_ID)).getItem();
		comboBoxMedicationUnit = new JComboBox<>(listMedicationUnit.toArray(new MedicationUnit[listMedicationUnit.size()]));
		comboBoxMedicationUnit.setSelectedItem(toEdit.getMedicationUnit());
		comboBoxMedicationUnit.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxMedicationUnit);
		spinnerUnitCount = new JSpinner(new SpinnerNumberModel(toEdit.getUnitCount().doubleValue(), -100, 100, 0.1));
		p.add(spinnerUnitCount);
		checkBoxSelfMedication = new JCheckBox();
		checkBoxSelfMedication.setSelected(toEdit.isSelfMedication());
		p.add(checkBoxSelfMedication);
		return p;
	}

	@Override
	public MedicationPlan getCurrentContent() {
		return new MedicationPlan()
			.setId(toEdit.getId())
			.setStartDate(DateTimeUtil.dateFromString(textFieldStartDate.getText()))
			.setEndDate(DateTimeUtil.dateFromString(textFieldEndDate.getText()))
			.setNextDateOfIntake(DateTimeUtil.dateFromString(textFieldNextDateOfIntake.getText()))
			.setTimeOfIntake(DateTimeUtil.timeFromString(textFieldTimeOfIntake.getText()))
			.setMedication(((Medication) comboBoxMedication.getSelectedItem()))
			.setMedicationUnit(((MedicationUnit) comboBoxMedicationUnit.getSelectedItem()))
			.setUnitCount(new BigDecimal(((Number) spinnerUnitCount.getValue()).doubleValue()))
			.setSelfMedication(checkBoxSelfMedication.isSelected());
	}
}
