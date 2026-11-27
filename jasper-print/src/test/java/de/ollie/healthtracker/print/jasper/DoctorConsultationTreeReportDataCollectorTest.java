package de.ollie.healthtracker.print.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.print.jasper.po.DoctorConsultationTreeItemPO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DoctorConsultationTreeReportDataCollectorTest {

	private static final LocalDate DATE = LocalDate.of(2026, 3, 10);

	@Mock
	private DoctorConsultationService doctorConsultationService;

	@InjectMocks
	private DoctorConsultationTreeReportDataCollector unitUnderTest;

	private Doctor doctor(String name, String type) {
		DoctorType doctorType = type == null ? null : new DoctorType().setName(type);
		return new Doctor().setName(name).setDoctorType(doctorType);
	}

	private DoctorConsultation consultation(UUID id, LocalDate date, DoctorConsultation predecessor) {
		return new DoctorConsultation()
			.setId(id)
			.setDate(date)
			.setTime(LocalTime.of(9, 0))
			.setDoctor(doctor("Dr. Meier", "Allgemeinmedizin"))
			.setReason("Kontrolle")
			.setResult("ok")
			.setOpen(false)
			.setSubsequentAppointmentOf(predecessor);
	}

	@Test
	void increasesTheLevelAlongTheChainOfLinks() {
		// Prepare
		DoctorConsultation original = consultation(UUID.randomUUID(), DATE, null);
		DoctorConsultation firstFollowUp = consultation(UUID.randomUUID(), DATE.plusDays(5), original);
		DoctorConsultation secondFollowUp = consultation(UUID.randomUUID(), DATE.plusDays(10), firstFollowUp);
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(secondFollowUp, original, firstFollowUp));
		// Run
		List<DoctorConsultationTreeItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(List.of(0, 1, 2), result.stream().map(DoctorConsultationTreeItemPO::getLevel).toList());
	}

	@Test
	void indentsAFollowUpUnderItsPredecessor() {
		// Prepare
		DoctorConsultation original = consultation(UUID.randomUUID(), DATE, null);
		DoctorConsultation followUp = consultation(UUID.randomUUID(), DATE.plusDays(5), original);
		when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(followUp, original));
		// Run
		List<DoctorConsultationTreeItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals("└─ 15.03.2026", result.get(1).getTree());
	}

	@Test
	void showsTheUnindentedDateForATopLevelConsultation() {
		// Prepare
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(consultation(UUID.randomUUID(), DATE, null)));
		// Run
		List<DoctorConsultationTreeItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals("10.03.2026", result.get(0).getTree());
	}

	@Test
	void showsOnlyTheHeaderDoctorData() {
		// Prepare
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(consultation(UUID.randomUUID(), DATE, null)));
		// Run
		List<DoctorConsultationTreeItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals("Dr. Meier (Allgemeinmedizin)", result.get(0).getDoctor());
	}
}
