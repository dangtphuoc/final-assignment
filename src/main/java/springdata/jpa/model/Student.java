package springdata.jpa.model;


public class Student extends AbstractEntity {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3443978888325595929L;

	private String firstName;
	
	private String lastName;
	
	
	public Student() {
	}
	
	public Student(Long id, String firstName, String lastName) {
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
}
