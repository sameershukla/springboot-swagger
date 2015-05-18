package epharma.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="openSlotRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenSlotRequest 
{

    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@XmlElement(name="date")
    private String date;

    @XmlElement(name="name")
    private String doctor;

    
}
