package de.ollie.healthtracker.print.jasper.po;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/** One medication entry of a day, already formatted (e.g. "Aspirin 1 Tbl"), rendered as a row of the medications subreport. */
@Accessors(chain = true)
@Data
@Generated
public class MedicationItemPO {

	private String text;
}
