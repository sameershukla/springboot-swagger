package epharma.service;

import java.util.Map;

import epharma.db.model.Appointment;

/**
 * Interface for all the model operations
 * @author SameerS
 *
 */
public interface AppointmentService
{

	public Appointment bookAppointment(String id);
	
	public boolean cancelAppointment(int id);
	
	public Map<String,Appointment> checkOpenSlots();
	
	public Map<String,Appointment> checkOpenSlots(String doctorname);
	
}
