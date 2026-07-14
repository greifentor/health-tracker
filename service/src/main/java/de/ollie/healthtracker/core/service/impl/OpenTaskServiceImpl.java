package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.OpenTaskService;
import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.OpenTask;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class OpenTaskServiceImpl implements OpenTaskService {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;
	private final WeightMeasurementHistoryService weightMeasurementHistoryService;

	@Override
	public List<OpenTask> findAllOpenTasks() {
		List<OpenTask> openTasks = new ArrayList<>();
		if (hasNoBloodPressureMeasurementForToday()) {
			openTasks.add(new OpenTask().setDescription("No blood pressure measurement has been recorded for today yet."));
		}
		if (hasNoWeightMeasurementForToday()) {
			openTasks.add(new OpenTask().setDescription("No weight measurement has been recorded for today yet."));
		}
		return openTasks;
	}

	private boolean hasNoBloodPressureMeasurementForToday() {
		LocalDate today = LocalDate.now();
		return bloodPressureMeasurementService.findAllBloodPressureMeasurementsByTimeInterval(today, today).isEmpty();
	}

	private boolean hasNoWeightMeasurementForToday() {
		LocalDate today = LocalDate.now();
		return weightMeasurementHistoryService
			.findAllOfLastDays(0)
			.stream()
			.noneMatch(wm -> today.equals(wm.getDateOfRecording()));
	}
}
