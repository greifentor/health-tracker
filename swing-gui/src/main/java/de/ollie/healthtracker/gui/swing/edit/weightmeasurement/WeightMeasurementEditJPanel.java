package de.ollie.healthtracker.gui.swing.edit.weightmeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;
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
public class WeightMeasurementEditJPanel extends AbstractEditPanel<WeightMeasurement> {

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;
	private JSpinner spinnerKg;
	private JTextField textFieldComment;

	public WeightMeasurementEditJPanel(WeightMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Time Of Recording:", "Kg:", "Comment:");
	}

	@Override
	protected JPanel createComponentPanel(WeightMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldTimeOfRecording = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfRecording()), 40);
		p.add(textFieldTimeOfRecording);
		spinnerKg = createDecimalSpinner(toEdit.getKg(), 0, 499, 0.1, 2);
		p.add(spinnerKg);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		return p;
	}

	@Override
	public WeightMeasurement getCurrentContent() {
		return new WeightMeasurement()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setTimeOfRecording(DateTimeUtil.timeFromString(textFieldTimeOfRecording.getText()))
			.setKg(decimalValueOf(spinnerKg))
			.setComment(textFieldComment.getText().isEmpty() ? null : textFieldComment.getText());
	}
}
