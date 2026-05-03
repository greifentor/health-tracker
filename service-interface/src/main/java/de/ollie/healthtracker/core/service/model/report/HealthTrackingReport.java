package de.ollie.healthtracker.core.service.model.report;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class HealthTrackingReport {

	private LocalDate from;
	private LocalDate to;
	private List<DataPerDay> dataPerDayOrderedByDate = new ArrayList<>();
	private List<BloodPressureMeasurement> bloodPressureMeasurements = new ArrayList<>();
}
