package epharma.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import epharma.db.model.Appointment;
import epharma.service.AppointmentService;

/**
 * 
 * @author Sameer Shukla
 * 
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

	private static final String OPEN = "open";

	HashMap<String, Appointment> openAppointments = new HashMap<>();

	/**
	 * Prepare all the dummy appointments
	 */
	@SuppressWarnings("serial")
	public AppointmentServiceImpl() {

		final Appointment appointment1 = new Appointment(idGenerator(), "sshukla", new Date(30042014), "1450", "1500",
				OPEN);
		final Appointment appointment2 = new Appointment(idGenerator(), "mjones", new Date(10042014), "1650", "1500",
				OPEN);

		openAppointments = new HashMap<String, Appointment>() {
			{
				put(appointment1.getId(), appointment1);
				put(appointment2.getId(), appointment2);
			}
		};

	}

	/**
	 * Book appointment by checking id and setting status to close.
	 */
	public Appointment bookAppointment(String id) {
		Appointment bookAppointment = openAppointments.entrySet().stream()
				.filter(slot -> slot.getValue().id.equals(id) && slot.getValue().getStatus().equals(OPEN))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue())).getOrDefault(id, new Appointment());
		return bookAppointment;

	}

	public boolean cancelAppointment(int id) {
		return false;
	}

	/**
	 * Fetch all the open slots
	 */
	public Map<String, Appointment> checkOpenSlots() {
		Map<String, Appointment> openslots = openAppointments.entrySet().stream()
				.filter(openSlot -> openSlot.getValue().getStatus().equalsIgnoreCase(OPEN))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		return openslots;
	}

	/**
	 * Fetch all the open slots + name of doctor
	 */
	public Map<String, Appointment> checkOpenSlots(String name) {
		Map<String, Appointment> openslots = openAppointments.entrySet().stream()
				.filter(openSlot -> openSlot.getValue().getDoctorname().equalsIgnoreCase(name)
						&& openSlot.getValue().getStatus().equalsIgnoreCase(OPEN))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		return openslots;
	}

	/**
	 * Generate random strings
	 * 
	 * @return
	 */
	public static String idGenerator() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

}
