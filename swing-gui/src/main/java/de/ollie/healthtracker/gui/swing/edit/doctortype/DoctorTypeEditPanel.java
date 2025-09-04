package de.ollie.healthtracker.gui.swing.edit.doctortype;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DoctorTypeEditPanel extends AbstractEditPanel<DoctorType> {

	private JTextField textFieldName;

	public DoctorTypeEditPanel(DoctorType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:");
	}

	@Override
	protected JPanel createComponentPanel(DoctorType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		return p;
	}

	@Override
	public DoctorType getCurrentContent() {
		return new DoctorType().setId(toEdit.getId()).setName(textFieldName.getText());
	}
}
