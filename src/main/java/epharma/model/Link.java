package epharma.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "rel", "uri"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Link 
{

	@XmlElement(name = "rel")
	private String rel = "/slot/book";
	
	@XmlElement(name = "uri")
	private String uri;

	public Link(String u)
	{
		this.uri = u;
	}
	
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	
}
