package de.ollie.healthtracker.gui.swing.edit.meatconsumption;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MeatConsumptionEditJPanel extends AbstractEditPanel<MeatConsumption> {

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldDescription;

	public MeatConsumptionEditJPanel(MeatConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Description:");
	}

	@Override
	protected JPanel createComponentPanel(MeatConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldDescription = new JTextField(toEdit.getDescription(), 40);
		p.add(textFieldDescription);
		return p;
	}

	@Override
	public MeatConsumption getCurrentContent() {
		return new MeatConsumption()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setDescription(textFieldDescription.getText());
	}
}
