package de.ollie.healthtracker.gui.swing.edit.symptom;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SymptomEditJPanel extends AbstractEditPanel<Symptom> {

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldDescription;

	public SymptomEditJPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Date of Recording:", "Description:"));
		return p;
	}

	@Override
	protected JPanel createComponentPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldDescription = new JTextField(toEdit.getDescription(), 40);
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		p.add(textFieldDescription);
		return p;
	}

	@Override
	public Symptom getCurrentContent() {
		return new Symptom()
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setDescription(textFieldDescription.getText())
			.setId(toEdit.getId());
	}
}
