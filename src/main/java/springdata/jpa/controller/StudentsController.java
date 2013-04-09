package springdata.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springdata.jpa.common.ErrorType;
import springdata.jpa.dto.BaseDTO;
import springdata.jpa.dto.ClassOfferingDTO;
import springdata.jpa.dto.CurriculumDTO;
import springdata.jpa.dto.ResponseBean;
import springdata.jpa.dto.StudentDTO;
import springdata.jpa.exception.RestResponseEntityException;
import springdata.jpa.model.AbstractEntity;
import springdata.jpa.model.ClassOffering;
import springdata.jpa.model.Curriculum;
import springdata.jpa.model.Student;
import springdata.jpa.service.StudentService;



@RequestMapping("/students")
@Controller
public class StudentsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentsController.class);

	private static final String STUDENT_HOME_VIEW = "students";
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public StudentDTO getStudent(@PathVariable("id") Long studentId) {
		Student student = studentService.getStudent(studentId);
		if(student == null) throw new RestResponseEntityException("Student not found");
		return new StudentDTO(student);
	}
	
	@RequestMapping(value="/{id}/enrolledcontent", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<BaseDTO> getEnrolledContent(@PathVariable("id") Long studentId) {
		List<AbstractEntity> results = studentService.getEnrolledContent(studentId);
		List<BaseDTO> enrolledContent = new ArrayList<BaseDTO>();
		for(AbstractEntity entity : results) {
			if(entity instanceof ClassOffering) {
				enrolledContent.add(new ClassOfferingDTO((ClassOffering)entity));
			} else {
				enrolledContent.add(new CurriculumDTO((Curriculum)entity));
			}
		}
		return enrolledContent;
	}
	
	@RequestMapping(method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<StudentDTO> getStudents(@RequestParam(value="filterRole", required=false) Long filterRole) {
		List<Student> students;
		if(filterRole == null) {
			 students = studentService.getStudents();
		} else {
			students = studentService.getStudents(filterRole);
		}
		
		List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
		for(Student student : students) {
			studentDTOs.add(new StudentDTO(student));
		}
		return studentDTOs;
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<StudentDTO> searchStudents(@RequestParam(value="key", required=false) String key) {
		
		List<Student> students =  studentService.searchStudents(key);
		List<StudentDTO> studentDTOs = new ArrayList<StudentDTO>();
		for(Student student : students) {
			studentDTOs.add(new StudentDTO(student));
		}
		return studentDTOs;
	}


	@RequestMapping(method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseBean createStudent(@RequestBody StudentDTO studentDTO) {
		Student student = studentService.createStudent(studentDTO);
		ErrorType error = ErrorType.SUCCESS;
		if(student.getId() != null) {
    		error = ErrorType.FAIL;
    	}
        return new ResponseBean(error);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseBean updateStudent(@RequestBody StudentDTO studentDTO) {
		Student student = studentService.updateStudent(studentDTO);
		ErrorType error = ErrorType.SUCCESS;
		if(student.getId() != null) {
    		error = ErrorType.FAIL;
    	}
        return new ResponseBean(error);
	}
	
	/**
     * Shows the student home page.
     * @param model The model.
     * @return  The name of the student home view.
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showStudentsHomePage(Model model) {
        LOGGER.debug("Showing student home page");
        return STUDENT_HOME_VIEW;
    }
}
