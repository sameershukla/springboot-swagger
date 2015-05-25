package epharma.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	Map<String, Appointment> openAppointments = new HashMap<String, Appointment>();

	private static final String OPEN = "open";

	/**
	 * Prepare all the dummy appointments
	 */
	public AppointmentServiceImpl() {
		Appointment appointment1 = new Appointment(idGenerator(), "sshukla",
				new Date(30042014), "1450", "1500", "open");
		Appointment appointment2 = new Appointment(idGenerator(), "sshukla",
				new Date(30052014), "1550", "1600", "open");
		Appointment appointment3 = new Appointment(idGenerator(), "mjones",
				new Date(10042014), "1650", "1500", "open");
		Appointment appointment4 = new Appointment(idGenerator(), "achourey",
				new Date(20042014), "1750", "1500", "open");

		openAppointments.put(appointment1.getId(), appointment1);
		openAppointments.put(appointment2.getId(), appointment2);
		openAppointments.put(appointment3.getId(), appointment3);
		openAppointments.put(appointment4.getId(), appointment4);
	}

	/**
	 * Book appointment by checking id and setting status to close.
	 */
	public Appointment bookAppointment(String id) {
		Appointment appointment = openAppointments.get(id);

		if (appointment == null) {
			return null;
		}

		if (appointment.getStatus().equalsIgnoreCase(OPEN)) {
			appointment.setStatus("close");
		}

		openAppointments.put(id, appointment);

		return appointment;

	}

	public boolean cancelAppointment(int id) {
		return false;
	}

	/**
	 * Fetch all the open slots
	 */
	public Map<String, Appointment> checkOpenSlots() {
		Map<String, Appointment> openslots = populateOpenSlots(null);
		return openslots;
	}

	/**
	 * Fetch all the open slots + name of doctor
	 */
	public Map<String, Appointment> checkOpenSlots(String name) {
		Map<String, Appointment> openslots = populateOpenSlots(name);
		return openslots;
	}

	private Map<String, Appointment> populateOpenSlots(String name) {
		Map<String, Appointment> openslots = new HashMap<String, Appointment>();
		for (Map.Entry<String, Appointment> entry : openAppointments.entrySet()) {
			if (entry.getValue().getStatus().equalsIgnoreCase(OPEN)
					&& name != null) {
				if (entry.getValue().getDoctorname().equalsIgnoreCase(name)) {
					openslots.put(entry.getKey(), entry.getValue());
				}
			} else if (entry.getValue().getStatus().equalsIgnoreCase(OPEN)) {
				openslots.put(entry.getKey(), entry.getValue());
			}

		}

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
