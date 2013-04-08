package springdata.jpa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.ClassOfferingDTO;
import springdata.jpa.dto.EnrollmentData;
import springdata.jpa.dto.ResponseBean;
import springdata.jpa.model.ClassOffering;
import springdata.jpa.service.ClassOfferingService;



@RequestMapping("/classofferings")
@Controller
public class ClassOfferingsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassOfferingsController.class);
	
	@Autowired
	private ClassOfferingService classOfferingService;
	
	@RequestMapping(value="/search", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<ClassOfferingDTO> searchClassOfferings(@RequestParam(value="key", required=false) String key, 
			@RequestParam(value="startDate", required=false) Date startDate,
			@RequestParam(value="endDate", required=false) Date endDate) {
		
		LOGGER.debug("Searching class offerings...");
		
		List<ClassOffering> classOfferings = classOfferingService.searchClassOfferings(key, startDate, endDate);
		
		//build json class offering
		List<ClassOfferingDTO> classOfferingDTOs = new ArrayList<ClassOfferingDTO>();
		if(classOfferings != null) {
			for(ClassOffering classOffering : classOfferings) {
				ClassOfferingDTO classOfferingDTO = new ClassOfferingDTO(classOffering);
				classOfferingDTOs.add(classOfferingDTO);
			}
		}
		return classOfferingDTOs;
	}
	
	@RequestMapping(value="/enroll", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBean enrollClassOfferings(@RequestBody EnrollmentData enrollmentData,
			BindingResult result) {
		LOGGER.debug("enrolling class offering...");
		
		classOfferingService.enrollClassOfferings(enrollmentData.getClassOfferingIds(), enrollmentData.getStudentIds());
		ErrorType error = ErrorType.SUCCESS;
		return new ResponseBean(error);
	}
}
