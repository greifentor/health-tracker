package de.ollie.healthtracker.gui.swing.edit.medicationproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class MedicationProductEditJPanel extends AbstractEditPanel<MedicationProduct> {

	public static final String MEDICATION_ITEM_PROVIDER_ID = "medication-item-provider";
	public static final String MEDICATION_UNIT_ITEM_PROVIDER_ID = "medication-unit-item-provider";

	private JTextField textFieldExpirationDate;
	private JComboBox<Medication> comboBoxMedication;
	private JSpinner spinnerPackageSize;
	private JComboBox<MedicationUnit> comboBoxMedicationUnit;

	public MedicationProductEditJPanel(MedicationProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Expiration Date:", "Medication:", "Package Size:", "Medication Unit:");
	}

	@Override
	protected JPanel createComponentPanel(MedicationProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		textFieldExpirationDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getExpirationDate()), 40);
		p.add(textFieldExpirationDate);
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
		spinnerPackageSize = createDecimalSpinner(toEdit.getPackageSize(), 0, 1000000, 0.1, 1);
		p.add(spinnerPackageSize);
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
		return p;
	}

	@Override
	public MedicationProduct getCurrentContent() {
		return new MedicationProduct()
			.setId(toEdit.getId())
			.setExpirationDate(DateTimeUtil.dateFromString(textFieldExpirationDate.getText()))
			.setMedication(((Medication) comboBoxMedication.getSelectedItem()))
			.setPackageSize(decimalValueOf(spinnerPackageSize))
			.setMedicationUnit(((MedicationUnit) comboBoxMedicationUnit.getSelectedItem()));
	}
}
