package de.ollie.healthtracker.core.service.model.report;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.Symptom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class DataPerDay {

	private LocalDate date;
	private List<BloodPressureMeasurement> bloodPressureMeasurementsOrderedByTime = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();
	private List<MedicationLog> medicationLogsOrderedByTime = new ArrayList<>();
	private Symptom symptom;
}
