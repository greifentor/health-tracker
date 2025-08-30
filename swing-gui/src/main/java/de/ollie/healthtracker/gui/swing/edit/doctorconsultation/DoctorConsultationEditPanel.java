package de.ollie.healthtracker.gui.swing.edit.doctorconsultation;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class DoctorConsultationEditPanel extends AbstractEditPanel<DoctorConsultation> {

	public static final String DOCTORS_ITEM_PROVIDER_ID = "doctors-item-provider";

	private JComboBox<Doctor> comboBoxDoctor;
	private JTextArea textAreaReason;
	private JTextArea textAreaResult;
	private JTextField textFieldDate;
	private JTextField textFieldTime;

	public DoctorConsultationEditPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Date of Measurement:", "Time of Measurement:", "Doctor:"));
		p.add(createLabelSubPanel("Reason:", "", ""));
		p.add(createLabelSubPanel("Result:", "", ""));
		return p;
	}

	@Override
	protected JPanel createComponentPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createDateTimeAndDoctorPanel(toEdit, (ItemProvider<Doctor>) itemProviders.get(DOCTORS_ITEM_PROVIDER_ID)));
		p.add(createReasonPanel(toEdit));
		p.add(createResultPanel(toEdit));
		return p;
	}

	private JPanel createDateTimeAndDoctorPanel(DoctorConsultation toEdit, ItemProvider<Doctor> doctors) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDate()), 50);
		textFieldTime = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTime()), 50);
		List<Doctor> doctorList = doctors.getItem();
		comboBoxDoctor = new JComboBox<>(doctorList.toArray(new Doctor[doctorList.size()]));
		comboBoxDoctor.setSelectedItem(toEdit.getDoctor());
		comboBoxDoctor.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName() + " (" + value.getDoctorType().getName() + ")");
			}
			return new JLabel("-");
		});
		p.add(textFieldDate);
		p.add(textFieldTime);
		p.add(comboBoxDoctor);
		return p;
	}

	private JPanel createReasonPanel(DoctorConsultation toEdit) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textAreaReason = new JTextArea(toEdit.getReason(), 5, 40);
		textAreaReason.setBorder(new LineBorder(Color.GRAY));
		p.add(textAreaReason);
		return p;
	}

	private JPanel createResultPanel(DoctorConsultation toEdit) {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		textAreaResult = new JTextArea(toEdit.getResult(), 5, 40);
		textAreaResult.setBorder(new LineBorder(Color.GRAY));
		p.add(textAreaResult);
		return p;
	}

	@Override
	public DoctorConsultation getCurrentContent() {
		return new DoctorConsultation()
			.setDate(DateTimeUtil.dateFromString(textFieldDate.getText()))
			.setDoctor((Doctor) comboBoxDoctor.getSelectedItem())
			.setId(toEdit.getId())
			.setReason(textAreaReason.getText())
			.setResult(textAreaResult.getText())
			.setTime(DateTimeUtil.timeFromString(textFieldTime.getText()));
	}
}
