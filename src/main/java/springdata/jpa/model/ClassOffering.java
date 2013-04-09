package springdata.jpa.model;

import java.util.Date;

public class ClassOffering extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8363374897307430318L;
	
	private Course course;
	private Date startTime;
	private Date endTime;

	public ClassOffering() {
	}
	
	public ClassOffering(String title, String description) {
		super(title, description);
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
