package de.ollie.healthtracker.core.service.model.report;

import java.time.LocalDate;
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
public class HealthTrackingReport {

	private final LocalDate from;
	private final LocalDate to;
	private final List<DataPerDay> dataPerDayOrderedByDate = new ArrayList<>();
}
