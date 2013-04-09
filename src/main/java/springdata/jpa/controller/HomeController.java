package springdata.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentsController.class);
	private static final String STUDENT_HOME_VIEW = "students";
	
	/**
     * Shows the student home page.
     * @param model The model.
     * @return  The name of the student home view.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showStudentsHomePage(Model model) {
        LOGGER.debug("Showing student home page");
        return STUDENT_HOME_VIEW;
    }
}
