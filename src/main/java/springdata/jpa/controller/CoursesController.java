package springdata.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.CourseDTO;
import springdata.jpa.dto.ResponseBean;
import springdata.jpa.model.Course;
import springdata.jpa.service.CourseService;



@RequestMapping("/courses")
@Controller
public class CoursesController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoursesController.class);
	private static final String COURSE_HOME_VIEW = "courses";
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<CourseDTO> getCourses(@RequestParam(value="key", required=false) String key) {
		List<Course> courses = courseService.getCourses(key);
		
		//build json courses
		List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>();
		if(courses != null) {
			for(Course course : courses) {
				CourseDTO courseDTO = new CourseDTO(course);
				courseDTOs.add(courseDTO);
			}
		}
		return courseDTOs;
	}
	
	@RequestMapping(value="/search", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<CourseDTO> searchCourses(@RequestParam(value="key", required=false) String key) {
		List<Course> courses = courseService.getCourses(key);
		
		//build json courses
		List<CourseDTO> courseDTOs = new ArrayList<CourseDTO>();
		if(courses != null) {
			for(Course course : courses) {
				CourseDTO courseDTO = new CourseDTO(course);
				courseDTOs.add(courseDTO);
			}
		}
		return courseDTOs;
	}
	
    @RequestMapping(method = RequestMethod.POST, 
    				produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Transactional
    @ResponseBody
    public ResponseBean createCourse(@RequestBody CourseDTO courseDTO, BindingResult result) {
    	Course course = courseService.createCourse(courseDTO);
    	ErrorType error = ErrorType.SUCCESS;
    	if(course.getId() == null) {
    		error = ErrorType.FAIL;
    	}
        return new ResponseBean(error);
    }
    
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseBean updateCourse(@RequestBody CourseDTO courseDTO,
			BindingResult result) {
		// System.out.println(course.getTitle());
		Course course = courseService.updateCourse(courseDTO);
		ErrorType error = ErrorType.SUCCESS;
		if (course == null) {
			error = ErrorType.FAIL;
		}
		return new ResponseBean(error);
	}
    
	@RequestMapping(value = "{courseId}", method = RequestMethod.GET)
	@ResponseBody
	public CourseDTO getCourse(@PathVariable("courseId") Long courseId) 
	{
		Course course = courseService.getCourse(courseId);
		
		CourseDTO courseDTO = new CourseDTO(course);
		return courseDTO;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCourse(@PathVariable("id") Long courseId) 
	{
		courseService.deleteCourse(courseId);
	}
	
	/**
     * Shows the courses home page.
     * @param model The model.
     * @return  The name of the course home view.
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showCoursesHomePage(Model model) {
        LOGGER.debug("Showing courses home page");
        return COURSE_HOME_VIEW;
    }
	
}
