package springdata.jpa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import springdata.jpa.dto.CurriculumDTO;
import springdata.jpa.model.Curriculum;

@Service
public class CurriculumService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurriculumService.class);
	
	/**
	 * Create/update a curriculum
	 * @param curriculumDTO
	 * @return Curriculum created
	 */
	public Curriculum createUpdateCurriculum(CurriculumDTO curriculumDTO) {
		//call repository to create curriculum
		return DummyDataRepository.getCurriculum();
	}

	/**
	 * Get curricula whose title or description is matching with key
	 * @param key
	 * @return List of Curriculum
	 */
	public List<Curriculum> getCurricula(String key) {
		return DummyDataRepository.getCurricula();
	}

	/**
	 * Get Curriculum by id
	 * @param id
	 * @return Curriculum
	 */
	public Curriculum getCurriculum(Long id) {
		return DummyDataRepository.getCurriculum();
	}

	/**]
	 * Delete a curriculum by id
	 * @param id
	 */
	public void deleteCurriculum(Long id) {
		//call repository to delete a curriculum
	}

	/**
	 * Enroll students to curricula
	 * 
	 * @param curriculumIds List of curriculum ids to be enrolled by students
	 * @param studentIds List of student ids to enroll to curricula
	 * @param result
	 */
	public void enrollCurricula(List<Long> curriculumIds, List<Long> studentIds) {
		//handling enrollment process here
	}

	/**
	 * Search curricula whose title matches with key and it contains the course with title matching with withCourse
	 * @param key
	 * @param withCourse
	 * @return List of matching curricula
	 */
	public List<Curriculum> searchCurricula(String key, String withCourse) {
		return DummyDataRepository.getCurricula();
	}
}
