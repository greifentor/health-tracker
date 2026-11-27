package de.ollie.healthtracker.print.jasper.po;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class DoctorConsultationTreeItemPO {

	private String tree;
	private String time;
	private String doctor;
	private int level;
}
