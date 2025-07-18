package de.ollie.healthtracker.persistence.jpa.dbo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
@Entity(name = "DoctorTypeDbo")
@Table(name = "DOCTORTYPE")
public class DoctorTypeDbo {

	@Id
	@Column(name = "ID", nullable = false)
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;
}
