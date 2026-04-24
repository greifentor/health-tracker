package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.WhoBloodPressureClassificationService;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import jakarta.inject.Named;

@Named
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
		} else if ((sysMmHg >= 130) && (sysMmHg <= 139)) {
			return WhoBloodPressureClassification.HIGH_NORMAL;
		} else if ((sysMmHg >= 140) && (sysMmHg <= 159)) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_1;
		} else if ((sysMmHg >= 160) && (sysMmHg <= 179)) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_2;
		} else if (sysMmHg >= 180) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_3;
		}
		return WhoBloodPressureClassification.OPTIMAL;
	}

	private WhoBloodPressureClassification getDiaClassification(int diaMmHg) {
		if ((diaMmHg >= 80) && (diaMmHg <= 84)) {
			return WhoBloodPressureClassification.NORMAL;
		} else if ((diaMmHg >= 85) && (diaMmHg <= 89)) {
			return WhoBloodPressureClassification.HIGH_NORMAL;
		} else if ((diaMmHg >= 90) && (diaMmHg <= 99)) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_1;
		} else if ((diaMmHg >= 100) && (diaMmHg <= 109)) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_2;
		} else if (diaMmHg >= 110) {
			return WhoBloodPressureClassification.HYPERTENSION_GRADE_3;
		}
		return WhoBloodPressureClassification.OPTIMAL;
	}
}
