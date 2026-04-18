package de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
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
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class BloodPressureMeasurementEditJPanel extends AbstractEditPanel<BloodPressureMeasurement> {

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;
	private JSpinner spinnerSysMmHg;
	private JSpinner spinnerDiaMmHg;
	private JSpinner spinnerPulsePerMinute;
	private JComboBox<WhoBloodPressureClassification> comboBoxStatus;
	private JCheckBox checkBoxIrregularHeartbeat;
	private JTextField textFieldComment;

	public BloodPressureMeasurementEditJPanel(
		BloodPressureMeasurement toEdit,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel(
			"Date Of Recording:",
			"Time Of Recording:",
			"Sys Mm Hg:",
			"Dia Mm Hg:",
			"Pulse Per Minute:",
			"Status:",
			"Irregular Heartbeat:",
			"Comment:"
		);
	}

	@Override
	protected JPanel createComponentPanel(BloodPressureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(8, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldTimeOfRecording = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfRecording()), 40);
		p.add(textFieldTimeOfRecording);
		SpinnerModel spinnerModelSysMmHg = new SpinnerNumberModel(toEdit.getSysMmHg(), 0, 1000, 1);
		spinnerSysMmHg = new JSpinner(spinnerModelSysMmHg);
		p.add(spinnerSysMmHg);
		SpinnerModel spinnerModelDiaMmHg = new SpinnerNumberModel(toEdit.getDiaMmHg(), 0, 1000, 1);
		spinnerDiaMmHg = new JSpinner(spinnerModelDiaMmHg);
		p.add(spinnerDiaMmHg);
		SpinnerModel spinnerModelPulsePerMinute = new SpinnerNumberModel(toEdit.getPulsePerMinute(), 0, 1000, 1);
		spinnerPulsePerMinute = new JSpinner(spinnerModelPulsePerMinute);
		p.add(spinnerPulsePerMinute);
		comboBoxStatus = new JComboBox<>(WhoBloodPressureClassification.values());
		comboBoxStatus.setSelectedItem(toEdit.getStatus());
		p.add(comboBoxStatus);
		checkBoxIrregularHeartbeat = new JCheckBox();
		checkBoxIrregularHeartbeat.setSelected(toEdit.isIrregularHeartbeat());
		p.add(checkBoxIrregularHeartbeat);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		return p;
	}

	@Override
	public BloodPressureMeasurement getCurrentContent() {
		return new BloodPressureMeasurement()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setTimeOfRecording(DateTimeUtil.timeFromString(textFieldTimeOfRecording.getText()))
			.setSysMmHg((Integer) spinnerSysMmHg.getValue())
			.setDiaMmHg((Integer) spinnerDiaMmHg.getValue())
			.setPulsePerMinute((Integer) spinnerPulsePerMinute.getValue())
			.setStatus(((WhoBloodPressureClassification) comboBoxStatus.getSelectedItem()))
			.setIrregularHeartbeat(checkBoxIrregularHeartbeat.isSelected())
			.setComment(textFieldComment.getText().isEmpty() ? null : textFieldComment.getText());
	}
}
