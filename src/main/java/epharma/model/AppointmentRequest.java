package epharma.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="appointmentRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentRequest 
{

	@XmlElement(name="patient")
    private String patient;

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}
}
