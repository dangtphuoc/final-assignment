package springdata.jpa.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import springdata.jpa.model.Student;

@XmlRootElement(name="student")
public class StudentDTO extends BaseDTO {
	
	private String firstName;
	
	private String lastName;
	
	private Date birthday;
	
	private StudentDTO manager;
	
	private List<RoleDTO> roles;
	
	public StudentDTO() {
	}
	
	public StudentDTO(Student student) {
		super(student.getId());
		setFirstName(student.getFirstName());
		setLastName(student.getLastName());
	}
	public StudentDTO(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public StudentDTO getManager() {
		return manager;
	}
	public void setManager(StudentDTO manager) {
		this.manager = manager;
	}
	public List<RoleDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}
}
