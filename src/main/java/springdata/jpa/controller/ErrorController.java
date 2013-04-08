package springdata.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This controller is used solely to provide redirection to error views
 * when ajax error occurs.
 * @author Petri Kainulainen
 */
@Controller
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    protected static final String NOT_FOUND_PAGE_VIEW = "error/404";
    protected static final String INTERNAL_SERVER_ERROR_PAGE_VIEW = "error/error";

    @RequestMapping("/error/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String show404Page() {
        LOGGER.debug("Rendering 404 page");
        return NOT_FOUND_PAGE_VIEW;
    }

    @RequestMapping("/error/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String showInternalServerErrorPage() {
        LOGGER.debug("Rendering internal server error page");
        return INTERNAL_SERVER_ERROR_PAGE_VIEW;
    }
}
