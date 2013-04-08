package springdata.jpa.dto;

import javax.xml.bind.annotation.XmlRootElement;

import springdata.jpa.model.Location;

@XmlRootElement(name="location")
public class LocationDTO extends BaseDTO {

	private String telephone;
	
	private String contactPerson;
	
	public LocationDTO() {
	}
	
	public LocationDTO(Location location) {
		super(location.getId(), location.getTitle(), location.getDescription());
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	
}
