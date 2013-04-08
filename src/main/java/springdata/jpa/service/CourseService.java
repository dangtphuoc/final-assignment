package springdata.jpa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springdata.jpa.dto.CourseDTO;
import springdata.jpa.model.Course;

@Service
public class CourseService {
	
	/**
	 * Create a course
	 * @param courseDTO
	 * @return Course created
	 */
	public Course createCourse(CourseDTO courseDTO) {
		//call repository layer to save course, then return created course entity
		return new Course();
	}

	/**
	 * Get courses which its title or description is matching with key
	 * @param key
	 * @return List of courses
	 */
	public List<Course> getCourses(String key) {
		//call repository layer to search courses matching with key
		return DummyDataRepository.getCourses();
	}

	/**
	 * Get a course with id
	 * @param courseId
	 * @return Course
	 */
	public Course getCourse(Long courseId) {
		//call repository to get course by id
		return DummyDataRepository.getCourse();
	}

	/**
	 * update a course
	 * @param courseDTO
	 * @return Course updated
	 */
	public Course updateCourse(CourseDTO courseDTO) {
		//call repository to update course
		return DummyDataRepository.getCourse();
	}

	/**
	 * Search courses whose title and description is matching with key
	 * @param key
	 * @return List of Course
	 */
	public List<Course> searchCourses(String key) {
		return DummyDataRepository.getCourses();
	}

	
	/**
	 * Delete a course with id
	 * @param courseId
	 */
	public void deleteCourse(Long courseId) {
		//call repository to delete a course
	}
}
