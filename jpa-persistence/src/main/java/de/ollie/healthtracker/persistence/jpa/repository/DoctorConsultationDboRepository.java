package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.DoctorConsultationDbo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Repository
public interface DoctorConsultationDboRepository extends JpaRepository<DoctorConsultationDbo, UUID> {
	@Query("SELECT dbo FROM DoctorConsultationDbo dbo ORDER BY dbo.date DESC, dbo.time DESC")
	List<DoctorConsultationDbo> findAllOrdered();
}
