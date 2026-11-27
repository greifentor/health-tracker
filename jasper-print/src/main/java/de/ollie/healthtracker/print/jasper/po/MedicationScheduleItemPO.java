package de.ollie.healthtracker.print.jasper.po;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class MedicationScheduleItemPO {

	private String medication;
	private String manufacturer;
	private String period;
	private String frequency;
	private String dosage;
	private String time;
}
