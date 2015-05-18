package epharma.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="openSlotResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpenSlotResponse 
{

	@XmlElement(name="openSlotList")
	private List<Slot> slot;

	public List<Slot> getSlot() {
		return slot;
	}

	public void setSlot(List<Slot> slot) {
		this.slot = slot;
	}

}
