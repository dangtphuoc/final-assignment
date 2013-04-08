package springdata.jpa.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import springdata.jpa.model.ClassOffering;

@Service
public class ClassOfferingService {
	
	/**
	 * Search list of class offerings with key, start date and end date
	 * @param key
	 * @param startDate
	 * @param endDate
	 * @return List of matching ClassOfferings
	 */
	public List<ClassOffering> searchClassOfferings(String key, Date startDate, Date endDate) {
		//call repository layer to search class offerings
		return DummyDataRepository.getClassOfferings();
	}

	/**
	 * Enroll students to class offerings
	 * @param classOfferingIds List of class offering ids to be enrolled to students.
	 * @param studentIds List of student ids to enroll to class offerings
	 * @param result
	 */
	public void enrollClassOfferings(List<Long> classOfferingIds, List<Long> studentIds) {
		//handle enrollment process here.
	}

}
