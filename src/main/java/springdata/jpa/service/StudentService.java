package springdata.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springdata.jpa.model.AbstractEntity;
import springdata.jpa.model.Student;

@Service
public class StudentService {
	
	/**
	 * Get all students
	 * @return List of Students
	 */
	public List<Student> getStudents() {
		//call repository to get all students
		return DummyDataRepository.getStudents();
	}

	/**
	 * Get student by id
	 * @param studentId
	 * @return Student
	 */
	public Student getStudent(Long studentId) {
		//call repository to get student by id
		return DummyDataRepository.getStudent();
	}

	/**
	 * Create/update student
	 * @param student
	 * @return Student created/updated
	 */
	public Student createUpdateStudent(Student student) {
		//create or update student
		return DummyDataRepository.getStudent();
	}

	/**
	 * Get list of students by role
	 * @param filterRole role to filter
	 * @return List of matching student
	 */
	public List<Student> getStudents(Long filterRole) {
		//call repository to get student with specific role
		return DummyDataRepository.getStudents();
	}

	/**
	 * Search students whose first name or last name matches with key
	 * @param key
	 * @return List of matching Students
	 */
	public List<Student> searchStudents(String key) {
		return DummyDataRepository.getStudents();
	}

	/**
	 * Get courses and curricula which student has enrolled.
	 * @param studentId
	 * @return List of enrolled content
	 */
	public List<AbstractEntity> getEnrolledContent(Long studentId) {
		
		return DummyDataRepository.getContents();
	}

}
