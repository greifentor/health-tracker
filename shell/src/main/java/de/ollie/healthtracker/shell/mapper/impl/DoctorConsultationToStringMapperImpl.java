package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.shell.mapper.DoctorConsultationToStringMapper;
import jakarta.inject.Named;

@Named
class DoctorConsultationToStringMapperImpl implements DoctorConsultationToStringMapper {

	private static final String LINE_FORMAT = "(%36s) %-8s %-5s %-30s %s\n%s\n%s";

	@Override
	public String getHeadLine() {
		return "(ID)                                   Date     Time  Doctor                         Doctor Type\nReason\nResult";
	}

	@Override
	public String getUnderLine() {
		return "--------------------------------------------------------------------------------------------------------------";
	}

	@Override
	public String map(DoctorConsultation model) {
		return model == null
			? null
			: String.format(
				LINE_FORMAT,
				(model.getId() != null ? model.getId().toString() : "-"),
				dateToString(model.getDate()),
				timeToString(model.getTime()),
				getDoctor(model),
				getDoctorType(model),
				model.getReason(),
				model.getResult()
			);
	}

	private String getDoctor(DoctorConsultation doctorConsultation) {
		return doctorConsultation.getDoctor() != null ? doctorConsultation.getDoctor().getName() : "-";
	}

	private String getDoctorType(DoctorConsultation doctorConsultation) {
		return (doctorConsultation.getDoctor() != null) && (doctorConsultation.getDoctor().getDoctorType() != null)
			? doctorConsultation.getDoctor().getDoctorType().getName()
			: "-";
	}
}
