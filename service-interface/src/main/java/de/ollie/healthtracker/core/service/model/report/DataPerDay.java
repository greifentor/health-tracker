package de.ollie.healthtracker.core.service.model.report;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.Symptom;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Generated
@Getter
@RequiredArgsConstructor
@ToString
public class DataPerDay {

	private final List<BloodPressureMeasurement> bloodPressureMeasurementsOrderedByTime = new ArrayList<>();
	private final Comment comment;
	private final List<MedicationLog> medicationLogsOrderedByTime = new ArrayList<>();
	private final Symptom symptom;
}
