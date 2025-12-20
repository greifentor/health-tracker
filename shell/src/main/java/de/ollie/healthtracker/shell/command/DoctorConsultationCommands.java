package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import de.ollie.healthtracker.shell.handler.OutputHandler;
import de.ollie.healthtracker.shell.mapper.DoctorConsultationToStringMapper;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class DoctorConsultationCommands implements CommandsWithTimeOrDate {

	static final String MSG_NO_SUCH_DOCTOR_FOUND = "No doctor found for: ";
	static final String MSG_NO_SUCH_DOCTOR_CONSULTATION_FOUND = "No doctor consultation found for: ";

	//	private final EditDialogComponentFactory componentFactory;
	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;
	private final DoctorConsultationToStringMapper doctorConsultationToStringMapper;

	@Getter
	private final LocalDateFactory localDateFactory;

	@Getter
	private final LocalTimeFactory localTimeFactory;

	private final OutputHandler outputHandler;

	@ShellMethod(value = "Adds a doctor consultation.", key = { "add-doctor-consultation", "adc" })
	public String addDoctorConsultation(
		@ShellOption(
			help = "The date of the doctor consultation (DD.MM.JJJJ or TODAY or TD).",
			value = "date",
			defaultValue = "TODAY"
		) String dateOfMeasurementStr,
		@ShellOption(
			help = "The time of the doctor consultation (HH:MM or NOW).",
			value = "time",
			defaultValue = "NOW"
		) String timeOfMeasurementStr,
		@ShellOption(help = "The name of the doctor name part or id.", value = "doctor") String doctorNamePartOrId,
		@ShellOption(help = "The reason of the doctor consultation.", value = "reason") String reason,
		@ShellOption(help = "The result of the doctor consultation.", value = "result") String result
	) {
		try {
			doctorService
				.findByIdOrNameParticle(doctorNamePartOrId)
				.ifPresentOrElse(
					doctor ->
						doctorConsultationService.createDoctorConsultation(
							getDateFromParameter(dateOfMeasurementStr),
							getTimeFromParameter(timeOfMeasurementStr),
							doctor,
							true,
							reason,
							result
						),
					() -> {
						throw new NoSuchElementException(MSG_NO_SUCH_DOCTOR_FOUND + doctorNamePartOrId);
					}
				);
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	// @ShellMethod(
	// value = "Opens a GUI dialog to edit doctor consultation with passed id.",
	// key = { "edit-doctor-consultation", "edc" }
	// )
	// public String editDoctorConsultation(
	// @ShellOption(help = "The id of the doctor consultation to edit.", value =
	// "id", defaultValue = "unknown") String id
	// ) {
	// try {
	// BaseEditDialog.Observer<DoctorConsultation> observer = new
	// BaseEditDialog.Observer<DoctorConsultation>() {
	// @Override
	// public void onCancel() {
	// outputHandler.println("Canceled!");
	// }
	//
	// @Override
	// public void onDelete(DoctorConsultation toDelete) {
	// outputHandler.println("Deleted! " + toDelete);
	// }
	//
	// @Override
	// public void onSave(DoctorConsultation toSave) {
	// doctorConsultationService.updateDoctorConsultation(toSave);
	// }
	// };
	// DoctorConsultation doctorConsultation = new DoctorConsultation()
	// .setDate(getLocalDateFactory().now())
	// .setId(UUID.randomUUID())
	// .setTime(getLocalTimeFactory().now());
	// if (!"unknown".equals(id)) {
	// doctorConsultation =
	// doctorConsultationService
	// .findById(UUID.fromString(id))
	// .orElseThrow(() -> new
	// NoSuchElementException(MSG_NO_SUCH_DOCTOR_CONSULTATION_FOUND + id));
	// }
	// Optional
	// .ofNullable(doctorConsultation)
	// .ifPresent(dc ->
	// SwingUtilities.invokeLater(() -> {
	// System.out.println(dc);
	// new DoctorConsultationEditDialog(
	// "Doctor Consultation",
	// dc,
	// componentFactory,
	// observer,
	// () ->
	// doctorService.listDoctors().stream().sorted((d0, d1) ->
	// d0.getName().compareTo(d1.getName())).toList()
	// );
	// })
	// );
	// return Constants.OK;
	// } catch (Exception e) {
	// return Constants.ERROR + e.getMessage();
	// }
	// }

	@ShellMethod(value = "Lists doctor consultations.", key = { "list-doctor-consultations", "ldc" })
	public String listDoctorConsultations() {
		try {
			outputHandler.println(doctorConsultationToStringMapper.getHeadLine());
			outputHandler.println(doctorConsultationToStringMapper.getUnderLine());
			doctorConsultationService
				.listDoctorConsultations()
				.forEach(bpm -> outputHandler.println(doctorConsultationToStringMapper.map(bpm)));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}

	@ShellMethod(
		value = "Removes the doctor consultation with the passed id.",
		key = { "remove-doctor-consultation", "rdc" }
	)
	public String removeDoctorConsultation(
		@ShellOption(help = "The id of the doctor consultation to remove.", value = "id") String id
	) {
		try {
			doctorConsultationService.deleteDoctorConsultation(UUID.fromString(id));
			return Constants.OK;
		} catch (Exception e) {
			return Constants.ERROR + e.getMessage();
		}
	}
}
