package de.ollie.healthtracker.print.jasper;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.print.jasper.po.DoctorConsultationItemPO;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DoctorConsultationReportDataCollector {

	private static final DateTimeFormatter GERMAN_DATE = DateTimeFormatter
		.ofPattern("dd.MM.yyyy")
		.withLocale(Locale.GERMANY);
	private static final DateTimeFormatter GERMAN_TIME = DateTimeFormatter.ofPattern("HH:mm");
	private static final Comparator<DoctorConsultation> BY_DATE_TIME = Comparator
		.comparing(DoctorConsultation::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
		.thenComparing(DoctorConsultation::getTime, Comparator.nullsLast(Comparator.naturalOrder()));

	private final DoctorConsultationService doctorConsultationService;

	List<DoctorConsultationItemPO> collect() {
		List<DoctorConsultation> consultations = doctorConsultationService.listDoctorConsultations();
		Map<UUID, List<DoctorConsultation>> followUpsByPredecessor = followUpsByPredecessor(consultations);
		List<DoctorConsultationItemPO> items = new ArrayList<>();
		Set<UUID> visited = new HashSet<>();
		predecessors(consultations)
			.stream()
			.sorted(BY_DATE_TIME)
			.forEach(consultation -> appendWithFollowUps(consultation, false, followUpsByPredecessor, visited, items));
		return items;
	}

	private Map<UUID, List<DoctorConsultation>> followUpsByPredecessor(List<DoctorConsultation> consultations) {
		return consultations
			.stream()
			.filter(this::isFollowUp)
			.collect(Collectors.groupingBy(consultation -> consultation.getSubsequentAppointmentOf().getId()));
	}

	private List<DoctorConsultation> predecessors(List<DoctorConsultation> consultations) {
		return consultations.stream().filter(consultation -> !isFollowUp(consultation)).toList();
	}

	private boolean isFollowUp(DoctorConsultation consultation) {
		return (
			(consultation.getSubsequentAppointmentOf() != null) && (consultation.getSubsequentAppointmentOf().getId() != null)
		);
	}

	private void appendWithFollowUps(
		DoctorConsultation consultation,
		boolean followUp,
		Map<UUID, List<DoctorConsultation>> followUpsByPredecessor,
		Set<UUID> visited,
		List<DoctorConsultationItemPO> items
	) {
		if (!visited.add(consultation.getId())) {
			return;
		}
		items.add(toItem(consultation, followUp));
		followUpsByPredecessor
			.getOrDefault(consultation.getId(), List.of())
			.stream()
			.sorted(BY_DATE_TIME)
			.forEach(subsequent -> appendWithFollowUps(subsequent, true, followUpsByPredecessor, visited, items));
	}

	private DoctorConsultationItemPO toItem(DoctorConsultation consultation, boolean followUp) {
		return new DoctorConsultationItemPO()
			.setDate(formatDate(consultation.getDate()))
			.setTime(formatTime(consultation.getTime()))
			.setDoctor(doctorLabel(consultation.getDoctor()))
			.setReason(orDash(consultation.getReason()))
			.setResult(orDash(consultation.getResult()))
			.setStatus(consultation.isOpen() ? "offen" : "erledigt")
			.setFollowUp(followUp);
	}

	private String doctorLabel(Doctor doctor) {
		if ((doctor == null) || (doctor.getName() == null)) {
			return "-";
		}
		DoctorType doctorType = doctor.getDoctorType();
		if ((doctorType == null) || (doctorType.getName() == null)) {
			return doctor.getName();
		}
		return doctor.getName() + " (" + doctorType.getName() + ")";
	}

	private String formatDate(LocalDate date) {
		return date == null ? "-" : date.format(GERMAN_DATE);
	}

	private String formatTime(LocalTime time) {
		return time == null ? "" : time.format(GERMAN_TIME);
	}

	private String orDash(String value) {
		return (value == null) || value.isBlank() ? "-" : value;
	}
}
