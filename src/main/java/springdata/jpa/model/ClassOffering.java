package springdata.jpa.model;

public class ClassOffering extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8363374897307430318L;
	
	private Course course;

	public ClassOffering() {
	}
	
	public ClassOffering(String title, String description) {
		super(title, description);
	}
	
	public ClassOffering(Long id, String title, String description) {
		super(id, title, description);
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Course getCourse() {
		return course;
	}

}
