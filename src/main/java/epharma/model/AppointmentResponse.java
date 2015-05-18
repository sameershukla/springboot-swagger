package epharma.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "appointmentResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentResponse {

	@XmlElement(name = "booked")
	public boolean isBooked;

	@XmlElement(name = "slot")
	public Slot slot;

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

}
