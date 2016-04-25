package epharma.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

		Optional<Map<String, Appointment>> openOptionSlots = Optional.of(service.checkOpenSlots());
		Map<String, Appointment> openSlots = openOptionSlots
				.orElseThrow(() -> new DoctorNotFoundException("No Appoinment Found for requested Doctor"));
		List<Slot> slotList = openSlots.entrySet().stream().map(e -> getSlot(e.getValue()))
				.collect(Collectors.toList());

		OpenSlotResponse openSlotResponse = new OpenSlotResponse();
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

		Optional<Map<String, Appointment>> openOptionSlots = Optional.of(service.checkOpenSlots(doctorname));

		Map<String, Appointment> openSlots = openOptionSlots
				.orElseThrow(() -> new DoctorNotFoundException("No Appoinment Found for requested Doctor"));

		List<Slot> slotList = openSlots.entrySet().stream().map(e -> getSlot(e.getValue()))
				.collect(Collectors.toList());

		OpenSlotResponse openSlotResponse = new OpenSlotResponse();
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
			throw new AppointmentNotFoundException("Appointment cannot be done withoutid");
		}
		Optional<Appointment> optionAppointment = Optional.of(service.bookAppointment(id));
		Appointment appointment = optionAppointment.orElseThrow(() -> new AppointmentNotFoundException());
		AppointmentResponse response = new AppointmentResponse();
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
	 * 
	 * @param appointment
	 * @return
	 */
	private Slot getSlot(Appointment appointment) {
		Slot slot = new Slot();
		slot.setDoctor(appointment.getDoctorname());
		slot.setStart(appointment.getStart());
		slot.setEnd(appointment.getEnd());
		slot.setId(appointment.getId());
		slot.setLink(new Link("/slot/" + appointment.getId()));
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
	public String handleAppointmentNotFoundException(AppointmentNotFoundException e) {
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
