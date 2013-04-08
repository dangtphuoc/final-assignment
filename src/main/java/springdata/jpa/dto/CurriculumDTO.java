package springdata.jpa.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import springdata.jpa.model.Curriculum;

@XmlRootElement(name="curriculum")
public class CurriculumDTO extends BaseDTO {
	
	private List<CourseDTO> courses;
	
	public CurriculumDTO() {
	}
	
	public CurriculumDTO(Curriculum curriculum) {
		super(curriculum.getId(), curriculum.getTitle(), curriculum.getDescription());
	}
	public List<CourseDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDTO> courses) {
		this.courses = courses;
	}

}
