package springdata.jpa.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EnrollmentData {
	private List<Long> studentIds;
	private List<Long> classOfferingIds;
	private List<Long> curriculumIds;
	public List<Long> getCurriculumIds() {
		return curriculumIds;
	}
	public void setCurriculumIds(List<Long> curriculumIds) {
		this.curriculumIds = curriculumIds;
	}
	public List<Long> getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(List<Long> studentIds) {
		this.studentIds = studentIds;
	}
	public List<Long> getClassOfferingIds() {
		return classOfferingIds;
	}
	public void setClassOfferingIds(List<Long> classOfferingIds) {
		this.classOfferingIds = classOfferingIds;
	}
	
}
