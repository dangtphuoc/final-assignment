package springdata.jpa.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import springdata.jpa.config.CustomDateSerializer;
import springdata.jpa.model.ClassOffering;

@XmlRootElement(name="classOffering")
public class ClassOfferingDTO extends BaseDTO {
	
	private LocationDTO location;
	
	@JsonSerialize(using=CustomDateSerializer.class)
	private Date startTime;
	
	@JsonSerialize(using=CustomDateSerializer.class)
	private Date endTime;
	
	private CourseDTO course;
	
	private StudentDTO instructor;
	
	public ClassOfferingDTO() {
	}
	
	public ClassOfferingDTO(Long id, String title, String description) {
		super(id, title, description);
	}

	public ClassOfferingDTO(ClassOffering c) {
		super(c.getId(), c.getTitle(), c.getDescription());
		setCourse(new CourseDTO(c.getCourse()));
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
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

	public StudentDTO getInstructor() {
		return instructor;
	}

	public void setInstructor(StudentDTO instructor) {
		this.instructor = instructor;
	}

	public CourseDTO getCourse() {
		return course;
	}

	public void setCourse(CourseDTO course) {
		this.course = course;
	}
}
