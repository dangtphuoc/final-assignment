package springdata.jpa.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="role")
public class RoleDTO extends BaseDTO {
	
	public RoleDTO() {
	}

	public RoleDTO(Long id, String title, String description) {
		super(id, title, description);
	}
	
}
