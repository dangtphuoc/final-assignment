package springdata.jpa.controller;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.CurriculumDTO;
import springdata.jpa.dto.EnrollmentData;
import springdata.jpa.dto.ResponseBean;
import springdata.jpa.exception.RestResponseEntityException;
import springdata.jpa.model.Curriculum;
import springdata.jpa.service.CurriculumService;


@Controller
@RequestMapping(value="/curricula")
public class CurriculaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CurriculaController.class);
	private static final String CURRICULA_HOME_VIEW = "curricula";
	
	@Autowired
	private CurriculumService curriculumService;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<CurriculumDTO> getCurricula(@RequestParam(value="key", required=false) String key) {
		LOGGER.debug("getting curricula with key = " + key);
		
		List<Curriculum> curricula = curriculumService.getCurricula(key);
		List<CurriculumDTO> curriculumDTOs = new ArrayList<CurriculumDTO>();
		if(curricula != null) {
			for(Curriculum curriculum : curricula) {
				curriculumDTOs.add(new CurriculumDTO(curriculum));
			}
		}
		return curriculumDTOs;
	}
	
	/**
	 * Search curricula
	 * 
	 * @param key search with curriculum's title and description
	 * @param withCourse curricula containing course
	 * @return List of CurriculumDTO
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	@ResponseBody
	public List<CurriculumDTO> searchCurricula(@RequestParam(value="key", required=false) String key, 
			@RequestParam(value="withCourse", required=false) String withCourse) {
		LOGGER.debug("getting curricula with key = " + key + " and withCourse = " + withCourse);
		
		List<Curriculum> curricula = curriculumService.searchCurricula(key, withCourse);
		List<CurriculumDTO> curriculumDTOs = new ArrayList<CurriculumDTO>();
		if(curricula != null) {
			for(Curriculum curriculum : curricula) {
				curriculumDTOs.add(new CurriculumDTO(curriculum));
			}
		}
		return curriculumDTOs;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public CurriculumDTO getCurriculum(@PathVariable(value="id") Long id) {
		
		Curriculum curriculum = curriculumService.getCurriculum(id);
		if(curriculum == null) {
			
			LOGGER.debug("Cannot find a curriculum with id: " + id);
			
			throw new RestResponseEntityException("Cannot find a curriculum with id: " + id);
		}
		
		return new CurriculumDTO(curriculum);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteCurriculum(@PathVariable(value="id") Long id) {
		curriculumService.deleteCurriculum(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public CurriculumDTO createCurriculum(@RequestBody CurriculumDTO curriculumDTO) {
		
		Curriculum curriculum = curriculumService.createCurriculum(curriculumDTO);
		if(curriculum == null) {
			LOGGER.debug("Failed to create curriculum with title: " + curriculumDTO.getTitle());
			throw new RestResponseEntityException("Failed to create curriculum with title: " + curriculumDTO.getTitle());
		}
		
		return new CurriculumDTO(curriculum);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	@ResponseBody
	public CurriculumDTO updateCurriculum(@RequestBody CurriculumDTO curriculumDTO) {
		Curriculum curriculum = curriculumService.updateCurriculum(curriculumDTO);
		if(curriculum == null) {
			LOGGER.debug("Failed to create curriculum with id: " + curriculumDTO.getId());
			throw new RestResponseEntityException("Failed to update curriculum with id: " + curriculumDTO.getId());
		}
		
		return new CurriculumDTO(curriculum);
	}
	
	@RequestMapping(value="/enroll", method=RequestMethod.POST)
	@ResponseBody
	public ResponseBean enrollCurricula(@RequestBody EnrollmentData enrnollmentData, BindingResult result) {
		curriculumService.enrollCurricula(enrnollmentData.getCurriculumIds(), enrnollmentData.getStudentIds());
		return new ResponseBean(ErrorType.SUCCESS);
	}
	
	@RequestMapping(value="/home")
	public String showCurriculaHomePage(Model model) {
		LOGGER.debug("showing curricula home page...");
		return CURRICULA_HOME_VIEW;
	}
}
