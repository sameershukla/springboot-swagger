package epharma.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import epharma.db.model.Appointment;
import epharma.model.AppointmentResponse;
import epharma.model.Link;
import epharma.model.OpenSlotResponse;
import epharma.model.Slot;
import epharma.service.AppointmentService;
import epharma.utils.AppointmentNotFoundException;
import epharma.utils.DoctorNotFoundException;

/**
 * 
 * @author Sameer Shukla
 * 
 *         Hard-Coded activities so far, it should give the Gist how this
 *         appointment flow will work.
 * 
 */
@RestController
@Api(value = "appointment service", description = "Operations related to doctor's appointment")
@RequestMapping("/epharma/appointment")
public class AppointmentController {

	@Autowired
	AppointmentService service;

	/**
	 * This method will check all the open slots for all the doctors
	 * 
	 * @return
	 */
	@ApiOperation(value = "Check the open slots of all doctors")
	@RequestMapping(value = "/{status}", method = RequestMethod.GET)
	public OpenSlotResponse checkAppointments() {

		Map<String, Appointment> openSlots = service.checkOpenSlots();
		if (openSlots.size() == 0) {
			throw new AppointmentNotFoundException("No Appointments Found!!");
		}

		OpenSlotResponse openSlotResponse = new OpenSlotResponse();

		List<Slot> slotList = new ArrayList<Slot>();
		for (Map.Entry<String, Appointment> entry : openSlots.entrySet()) {
			Appointment appointment = entry.getValue();
			Slot slot = getSlot(appointment);
			slot.setLink(new Link("/slot/" + appointment.getId()));
			slotList.add(slot);
		}

		openSlotResponse.setSlot(slotList);
		return openSlotResponse;
	}

	/**
	 * Fetch status by doctorname and with status as open
	 * 
	 * @param doctorname
	 * @param status
	 * @return
	 */
	@ApiOperation(value = "Check the open slots doctor of specific doctor")
	@RequestMapping(value = "/{doctorname}/open", method = RequestMethod.GET)
	public OpenSlotResponse checkAppointmentOfSpecificDoctor(
			@ApiParam(name = "doctorname", required = true, value = "Name of the doctor you want to book appointment") @PathVariable(value = "doctorname") String doctorname) {

		if (doctorname == null)
			throw new IllegalArgumentException();

		Map<String, Appointment> openSlots = service.checkOpenSlots(doctorname);

		if (openSlots.size() == 0) {
			throw new DoctorNotFoundException(
					"No Appoinment Found for requested Doctor");
		}

		OpenSlotResponse openSlotResponse = new OpenSlotResponse();

		List<Slot> slotList = new ArrayList<Slot>();
		for (Map.Entry<String, Appointment> entry : openSlots.entrySet()) {
			Appointment appointment = entry.getValue();
			Slot slot = getSlot(appointment);
			slot.setLink(new Link("/slot/" + appointment.getId()));
			slotList.add(slot);
		}

		openSlotResponse.setSlot(slotList);
		return openSlotResponse;
	}

	/**
	 * Check the open slots in a day and return. It will not work for any other
	 * date
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "Book Slot by passing Booking ID")
	@RequestMapping(value = "/book/{id}/{patientname}", method = RequestMethod.POST)
	public AppointmentResponse bookSlots(
			@ApiParam(name = "id", required = true, value = "Booking Id") @PathVariable(value = "id") String id,
			@ApiParam(name = "patientname", required = true, value = "Send request provide patient's name") @PathVariable(value = "patientname") String patientname) {

		if (patientname == null)
			throw new IllegalArgumentException("Patient name is required");

		if (id == null) {
			throw new AppointmentNotFoundException(
					"Appointment cannot be done withoutid");
		}
		AppointmentResponse response = new AppointmentResponse();
		Appointment appointment = service.bookAppointment(id);
		if (appointment == null)
			throw new AppointmentNotFoundException(
					"No appointment with given id");

		if (appointment.getStatus().equalsIgnoreCase("close")) {
			response.setSlot(getSlot(appointment));
			response.setBooked(true);
		} else {
			response.setSlot(null);
		}

		return response;
	}
	

	/**
	 * Common method
	 * @param appointment
	 * @return
	 */
	private Slot getSlot(Appointment appointment) {
		Slot slot = new Slot();
		slot.setDoctor(appointment.getDoctorname());
		slot.setStart(appointment.getStart());
		slot.setEnd(appointment.getEnd());
		slot.setId(appointment.getId());
		return slot;
	}

	/**
	 * In case if No Appointment found
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(AppointmentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleAppointmentNotFoundException(
			AppointmentNotFoundException e) {
		return e.getMessage();
	}

	/**
	 * In case if no Doctor Found
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DoctorNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleDoctorNotFoundException(DoctorNotFoundException e) {
		return e.getMessage();
	}

}
