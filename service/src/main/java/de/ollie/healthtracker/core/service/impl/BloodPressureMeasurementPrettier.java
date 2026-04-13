package de.ollie.healthtracker.core.service.impl;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Named
class BloodPressureMeasurementPrettier {

	@Data
	private class BloodPressureMeasurementPerDay {

		private List<BloodPressureMeasurement> bpms = new ArrayList<>();

		BloodPressureMeasurementPerDay add(BloodPressureMeasurement bpm) {
			bpms.add(bpm);
			return this;
		}

		BloodPressureMeasurement getAveragedBloodPressureMeasurement() {
			BloodPressureMeasurement abpm = new BloodPressureMeasurement().setDiaMmHg(0).setPulsePerMinute(0).setSysMmHg(0);
			abpm.setStatus(BloodPressureMeasurementStatus.GREEN);
			abpm.setIrregularHeartbeat(false);
			for (BloodPressureMeasurement bpm : bpms) {
				abpm.setDateOfRecording(bpm.getDateOfRecording());
				abpm.setDiaMmHg(abpm.getDiaMmHg() + bpm.getDiaMmHg());
				abpm.setIrregularHeartbeat(abpm.isIrregularHeartbeat() || bpm.isIrregularHeartbeat());
				abpm.setPulsePerMinute(abpm.getPulsePerMinute() + bpm.getPulsePerMinute());
				if (bpm.getStatus().ordinal() > abpm.getStatus().ordinal()) {
					abpm.setStatus(bpm.getStatus());
				}
				abpm.setSysMmHg(abpm.getSysMmHg() + bpm.getSysMmHg());
				abpm.setTimeOfRecording(bpm.getTimeOfRecording());
			}
			abpm.setDiaMmHg(abpm.getDiaMmHg() / bpms.size());
			abpm.setPulsePerMinute(abpm.getPulsePerMinute() / bpms.size());
			abpm.setSysMmHg(abpm.getSysMmHg() / bpms.size());
			return abpm;
		}
	}

	List<BloodPressureMeasurement> prettify(List<BloodPressureMeasurement> bpmsToPrettify) {
		ensure(bpmsToPrettify != null, "bpms to prettify cannot be null!");
		Map<LocalDateTime, BloodPressureMeasurementPerDay> bpmspd = createMapBloodPressureMeasurementsPerDay(
			bpmsToPrettify
		);
		return getAveragedBloodPressureMeasurements(bpmspd);
	}

	private LocalDateTime getKey(BloodPressureMeasurement bpm) {
		return LocalDateTime.of(bpm.getDateOfRecording(), bpm.getTimeOfRecording());
	}

	private Map<LocalDateTime, BloodPressureMeasurementPerDay> createMapBloodPressureMeasurementsPerDay(
		List<BloodPressureMeasurement> bpms
	) {
		Map<LocalDateTime, BloodPressureMeasurementPerDay> bpmspd = new HashMap<>();
		for (BloodPressureMeasurement bpm : bpms) {
			LocalDateTime key = getKey(bpm);
			if (bpmspd.containsKey(key)) {
				bpmspd.get(key).add(bpm);
			} else {
				bpmspd.put(key, new BloodPressureMeasurementPerDay().add(bpm));
			}
		}
		return bpmspd;
	}

	private List<BloodPressureMeasurement> getAveragedBloodPressureMeasurements(
		Map<LocalDateTime, BloodPressureMeasurementPerDay> bpmspd
	) {
		return bpmspd
			.values()
			.stream()
			.map(BloodPressureMeasurementPerDay::getAveragedBloodPressureMeasurement)
			.sorted((bpm0, bpm1) -> getKey(bpm0).compareTo(getKey(bpm1)))
			.toList();
	}
}
