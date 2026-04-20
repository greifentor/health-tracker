package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;

public class WhoBloodPressureClassificationServiceImpl implements WhoBloodPressureClassificationService {

	@Override
	public WhoBloodPressureClassification calculateClassification(int sysMmHg, int diaMmHg) {
		return max(getSysClassification(sysMmHg), getDiaClassification(diaMmHg));
	}

	private WhoBloodPressureClassification max(
		WhoBloodPressureClassification sysClass,
		WhoBloodPressureClassification diaClass
	) {
		return sysClass.ordinal() > diaClass.ordinal() ? sysClass : diaClass;
	}

	private WhoBloodPressureClassification getSysClassification(int sysMmHg) {
		if ((sysMmHg >= 120) && (sysMmHg <= 129)) {
			return WhoBloodPressureClassification.NORMAL;
		}
		return WhoBloodPressureClassification.OPTIMAL;
	}

	private WhoBloodPressureClassification getDiaClassification(int diaMmHg) {
		if ((diaMmHg >= 80) && (diaMmHg <= 84)) {
			return WhoBloodPressureClassification.NORMAL;
		}
		return WhoBloodPressureClassification.OPTIMAL;
	}
}
