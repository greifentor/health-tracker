package de.ollie.healthtracker.print.jasper.po;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class HealthTrackingReportPO {

	private String from;
	private String to;
	private List<DataPerDayPO> dataPerDayOrderedByDate = new ArrayList<>();
}
