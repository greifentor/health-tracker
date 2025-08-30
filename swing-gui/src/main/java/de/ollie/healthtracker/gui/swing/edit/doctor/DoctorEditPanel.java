package de.ollie.healthtracker.gui.swing.edit.doctor;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DoctorEditPanel extends AbstractEditPanel<Doctor> {

	public static final String DOCTOR_TYPES_ITEM_PROVIDER_ID = "doctor-types-provider";

	private JComboBox<DoctorType> comboBoxDoctorType;
	private JTextField textFieldName;

	public DoctorEditPanel(Doctor toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Name:", "Doctor Type:"));
		return p;
	}

	@Override
	protected JPanel createComponentPanel(Doctor toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		ItemProvider<DoctorType> doctorTypes = (ItemProvider<DoctorType>) itemProviders.get(DOCTOR_TYPES_ITEM_PROVIDER_ID);
		textFieldName = new JTextField(toEdit.getName(), 40);
		List<DoctorType> doctorTypesList = doctorTypes.getItem();
		comboBoxDoctorType = new JComboBox<>(doctorTypesList.toArray(new DoctorType[doctorTypesList.size()]));
		comboBoxDoctorType.setSelectedItem(toEdit.getDoctorType());
		comboBoxDoctorType.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName() + " (" + value.getName() + ")");
			}
			return new JLabel("-");
		});
		p.add(textFieldName);
		p.add(comboBoxDoctorType);
		return p;
	}

	@Override
	public Doctor getCurrentContent() {
		return new Doctor()
			.setName(textFieldName.getText())
			.setDoctorType((DoctorType) comboBoxDoctorType.getSelectedItem());
	}
}
