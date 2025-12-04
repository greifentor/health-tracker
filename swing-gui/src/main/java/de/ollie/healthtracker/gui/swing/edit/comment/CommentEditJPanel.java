package de.ollie.healthtracker.gui.swing.edit.comment;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class CommentEditJPanel extends AbstractEditPanel<Comment> {

	public static final String COMMENT_TYPE_ITEM_PROVIDER_ID = "comment-type-item-provider";

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldContent;
	private JComboBox<CommentType> comboBoxCommentType;

	public CommentEditJPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Content:", "Comment Type:");
	}

	@Override
	protected JPanel createComponentPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldContent = new JTextField(toEdit.getContent(), 40);
		p.add(textFieldContent);
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
			.setContent(textFieldContent.getText())
			.setCommentType(((CommentType) comboBoxCommentType.getSelectedItem()));
	}
}
