package de.ollie.healthtracker.print.jasper.po;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/** One row of the daily health report: all data of a single day, already formatted for rendering. */
@Accessors(chain = true)
@Data
@Generated
public class DailyHealthReportPO {

	private String date;
	private String weight;
	private String bodyTemperature;
	private String bloodPressure;
	private List<MedicationItemPO> medications = new ArrayList<>();
	private String symptoms;
	private String nutrition;
	private String alcohol;
}
