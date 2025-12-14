package de.ollie.healthtracker.gui.swing.edit.comment;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CommentEditJPanel extends AbstractEditPanel<Comment> {

	public static final String COMMENT_TYPE_ITEM_PROVIDER_ID = "comment-type-item-provider";

	private JTextField textFieldDateOfRecording;
	private JTextArea textAreaContent;
	private JComboBox<CommentType> comboBoxCommentType;

	public CommentEditJPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Comment Type:");
	}

	@Override
	protected JPanel createComponentPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(2, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		List<CommentType> listCommentType =
			((ItemProvider<CommentType>) itemProviders.get(COMMENT_TYPE_ITEM_PROVIDER_ID)).getItem();
		comboBoxCommentType = new JComboBox<>(listCommentType.toArray(new CommentType[listCommentType.size()]));
		comboBoxCommentType.setSelectedItem(toEdit.getCommentType());
		comboBoxCommentType.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxCommentType);
		return p;
	}

	@Override
	public Comment getCurrentContent() {
		return new Comment()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setContent(textAreaContent.getText())
			.setCommentType(((CommentType) comboBoxCommentType.getSelectedItem()));
	}

	@Override
	protected JPanel createAdditionalPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		JPanel pLabel = new JPanel(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
		pLabel.add(new JLabel("Content:                   "));
		textAreaContent = new JTextArea(toEdit.getContent(), 5, 40);
		p.add(pLabel, BorderLayout.WEST);
		p.add(textAreaContent, BorderLayout.CENTER);
		return p;
	}
}
