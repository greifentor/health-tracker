package de.ollie.healthtracker.print.jasper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.print.jasper.po.DoctorConsultationItemPO;
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
class DoctorConsultationReportDataCollectorTest {

	private static final LocalDate DATE = LocalDate.of(2026, 3, 10);

	@Mock
	private DoctorConsultationService doctorConsultationService;

	@InjectMocks
	private DoctorConsultationReportDataCollector unitUnderTest;

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
			.setSubsequentAppointmentOf(predecessor != null ? predecessor.getId() : null);
	}

	@Test
	void ordersTopLevelConsultationsByDate() {
		// Prepare
		DoctorConsultation earlier = consultation(UUID.randomUUID(), DATE, null);
		DoctorConsultation later = consultation(UUID.randomUUID(), DATE.plusDays(5), null);
		when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(later, earlier));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(List.of("10.03.2026", "15.03.2026"), result.stream().map(DoctorConsultationItemPO::getDate).toList());
	}

	@Test
	void placesAFollowUpDirectlyBelowItsPredecessor() {
		// Prepare
		DoctorConsultation predecessor = consultation(UUID.randomUUID(), DATE, null);
		DoctorConsultation followUp = consultation(UUID.randomUUID(), DATE.plusDays(14), predecessor);
		when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(followUp, predecessor));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(List.of(false, true), result.stream().map(DoctorConsultationItemPO::isFollowUp).toList());
	}

	@Test
	void doesNotListAFollowUpAsATopLevelEntry() {
		// Prepare
		DoctorConsultation predecessor = consultation(UUID.randomUUID(), DATE, null);
		DoctorConsultation followUp = consultation(UUID.randomUUID(), DATE.plusDays(14), predecessor);
		when(doctorConsultationService.listDoctorConsultations()).thenReturn(List.of(followUp, predecessor));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(2, result.size());
	}

	@Test
	void followsTheChainOfLinkedFollowUpsRegardlessOfTheirDate() {
		// Prepare
		DoctorConsultation original = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 1), null);
		DoctorConsultation firstFollowUp = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 5), original);
		DoctorConsultation secondFollowUp = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 3), firstFollowUp);
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(secondFollowUp, original, firstFollowUp));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(
			List.of("01.03.2026", "05.03.2026", "03.03.2026"),
			result.stream().map(DoctorConsultationItemPO::getDate).toList()
		);
	}

	@Test
	void arrangesEachOriginalFollowedByItsChainAndOrdersOriginalsByDate() {
		// Prepare
		DoctorConsultation firstOriginal = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 10), null);
		DoctorConsultation firstFollowUp = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 12), firstOriginal);
		DoctorConsultation secondFollowUp = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 11), firstFollowUp);
		DoctorConsultation secondOriginal = consultation(UUID.randomUUID(), LocalDate.of(2026, 3, 5), null);
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(secondFollowUp, secondOriginal, firstFollowUp, firstOriginal));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals(
			List.of("05.03.2026", "10.03.2026", "12.03.2026", "11.03.2026"),
			result.stream().map(DoctorConsultationItemPO::getDate).toList()
		);
	}

	@Test
	void formatsTheDoctorWithItsType() {
		// Prepare
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(consultation(UUID.randomUUID(), DATE, null)));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals("Dr. Meier (Allgemeinmedizin)", result.get(0).getDoctor());
	}

	@Test
	void marksAnOpenConsultationAsOffen() {
		// Prepare
		when(doctorConsultationService.listDoctorConsultations())
			.thenReturn(List.of(consultation(UUID.randomUUID(), DATE, null).setOpen(true)));
		// Run
		List<DoctorConsultationItemPO> result = unitUnderTest.collect();
		// Check
		assertEquals("offen", result.get(0).getStatus());
	}
}
