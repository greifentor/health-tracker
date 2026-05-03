package de.ollie.healthtracker.print.jasper.po;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class BloodPressureMeasurementPO {

	private String date;
	private String diaMmHg;
	private String pulsePerMinute;
	private String sysMmHg;
	private boolean irregularHeartbeat;
	private String classificationWho;
}
