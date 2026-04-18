package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;

public interface WhoBloodPressureClassificationService {
	WhoBloodPressureClassification calculateClassification(int sysMmHg, int diaMmHg);
}
