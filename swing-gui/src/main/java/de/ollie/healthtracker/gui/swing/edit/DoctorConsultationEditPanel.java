package de.ollie.healthtracker.gui.swing.edit;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.Editor;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DoctorConsultationEditPanel extends JPanel implements Editor<DoctorConsultation> {

	public static final String DOCTORS_ITEM_PROVIDER_ID = "doctors-item-provider";

	private JComboBox<Doctor> comboBoxDoctor;
	private JTextArea textAreaReason;
	private JTextArea textAreaResult;
	private JTextField textFieldDate;
	private JTextField textFieldTime;

	private DoctorConsultation toEdit;

	public DoctorConsultationEditPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(new BorderLayout(HGAP, VGAP));
		this.toEdit = toEdit;
		setBorder(new EmptyBorder(VGAP, HGAP, VGAP, HGAP));
		JPanel psub = new JPanel(new BorderLayout(HGAP, VGAP));
		psub.add(createLabelPanel(), BorderLayout.WEST);
		psub.add(
			createComponentPanel(toEdit, (ItemProvider<Doctor>) itemProviders.get(DOCTORS_ITEM_PROVIDER_ID)),
			BorderLayout.CENTER
		);
		add(psub, BorderLayout.NORTH);
	}

	private JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Date of Measurement:", "Time of Measurement:", "Doctor:"));
		return p;
	}

	private JPanel createLabelSubPanel(String... labels) {
		JPanel p = new JPanel(new GridLayout(labels.length, 1, HGAP, VGAP));
		for (String label : labels) {
			p.add(new JLabel(label));
		}
		return p;
	}

	private JPanel createComponentPanel(DoctorConsultation toEdit, ItemProvider<Doctor> doctors) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		p.add(createDateTimeAndDoctorPanel(toEdit, doctors));
		p.add(createReasonPanel(toEdit));
		p.add(createResultPanel(toEdit));
		return p;
	}

	private JPanel createDateTimeAndDoctorPanel(DoctorConsultation toEdit, ItemProvider<Doctor> doctors) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldDate = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDate()));
		textFieldTime = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTime()));
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
