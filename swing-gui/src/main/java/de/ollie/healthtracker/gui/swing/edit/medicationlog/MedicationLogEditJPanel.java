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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationLogEditJPanel extends AbstractEditPanel<MedicationLog> {

	public static final String MEDICATION_ITEM_PROVIDER_ID = "medication-item-provider";
	public static final String MEDICATION_UNIT_ITEM_PROVIDER_ID = "medication-unit-item-provider";

	private JTextField textFieldDateOfIntake;
	private JTextField textFieldTimeOfIntake;
	private JComboBox<Medication> comboBoxMedication;
	private JComboBox<MedicationUnit> comboBoxMedicationUnit;
	private JCheckBox checkBoxSelfMedication;
	private JCheckBox checkBoxConfirmed;
	private JTextField textFieldComment;

	public MedicationLogEditJPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel(
			"Date Of Intake:",
			"Time Of Intake:",
			"Medication:",
			"Medication Unit:",
			"Self Medication:",
			"Confirmed:",
			"Comment:"
		);
	}

	@Override
	protected JPanel createComponentPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(7, 1, HGAP, VGAP));
		textFieldDateOfIntake = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfIntake()), 40);
		p.add(textFieldDateOfIntake);
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
		checkBoxSelfMedication = new JCheckBox();
		checkBoxSelfMedication.setSelected(toEdit.isSelfMedication());
		p.add(checkBoxSelfMedication);
		checkBoxConfirmed = new JCheckBox();
		checkBoxConfirmed.setSelected(toEdit.isConfirmed());
		p.add(checkBoxConfirmed);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		return p;
	}

	@Override
	public MedicationLog getCurrentContent() {
		return new MedicationLog()
			.setId(toEdit.getId())
			.setDateOfIntake(DateTimeUtil.dateFromString(textFieldDateOfIntake.getText()))
			.setTimeOfIntake(DateTimeUtil.timeFromString(textFieldTimeOfIntake.getText()))
			.setMedication(((Medication) comboBoxMedication.getSelectedItem()))
			.setMedicationUnit(((MedicationUnit) comboBoxMedicationUnit.getSelectedItem()))
			.setSelfMedication(checkBoxSelfMedication.isSelected())
			.setConfirmed(checkBoxConfirmed.isSelected())
			.setComment(textFieldComment.getText());
	}
}
