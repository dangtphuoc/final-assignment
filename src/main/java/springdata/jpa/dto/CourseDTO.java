package springdata.jpa.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import springdata.jpa.model.Course;

@XmlRootElement(name="course")
public class CourseDTO extends BaseDTO{
	protected List<ClassOfferingDTO> classOfferings;
	
	public CourseDTO() {
	}
	
	public CourseDTO(Course course) {
		super(course.getId(), course.getTitle(), course.getDescription());
	}
	public CourseDTO(Long id, String title, String description) {
		super(id, title, description);
	}

	public List<ClassOfferingDTO> getClassOfferings() {
		return classOfferings;
	}

	public void setClassOfferings(List<ClassOfferingDTO> classOfferings) {
		this.classOfferings = classOfferings;
	}
}
