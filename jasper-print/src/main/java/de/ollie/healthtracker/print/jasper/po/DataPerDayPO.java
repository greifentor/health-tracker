package de.ollie.healthtracker.print.jasper.po;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class DataPerDayPO {

	private Map<CommentType, List<Comment>> comment;
	private String date;
}
