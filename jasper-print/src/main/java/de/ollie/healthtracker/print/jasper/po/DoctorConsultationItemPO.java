package de.ollie.healthtracker.print.jasper.po;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class DoctorConsultationItemPO {

	private String date;
	private String time;
	private String doctor;
	private String reason;
	private String result;
	private String status;
	private boolean followUp;
}
