package de.ollie.healthtracker.gui.swing.edit.comment;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CommentEditPanel extends AbstractEditPanel<Comment> {

	private JTextField textFieldContent;
	private JTextField textFieldDateOfRecording;

	public CommentEditPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		JPanel p = new JPanel(new GridLayout(1, 1, HGAP, VGAP));
		p.add(createLabelSubPanel("Date of Recording:", "Content:"));
		return p;
	}

	@Override
	protected JPanel createComponentPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldContent = new JTextField(toEdit.getContent(), 40);
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		p.add(textFieldContent);
		return p;
	}

	@Override
	public Comment getCurrentContent() {
		return new Comment()
			.setContent(textFieldContent.getText())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setId(toEdit.getId());
	}
}
