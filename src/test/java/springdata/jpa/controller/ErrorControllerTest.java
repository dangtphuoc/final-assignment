package springdata.jpa.controller;

import org.junit.Before;
import org.junit.Test;

import springdata.jpa.controller.ErrorController;

import static junit.framework.Assert.assertEquals;

/**
 * @author Petri Kainulainen
 */
public class ErrorControllerTest {

    private ErrorController controller;

    @Before
    public void setUp() {
        controller = new ErrorController();
    }

    @Test
    public void show404Page() {
        String view = controller.show404Page();
        assertEquals(ErrorController.NOT_FOUND_PAGE_VIEW, view);
    }

    @Test
    public void showInternalServerErrorPage() {
        String view = controller.showInternalServerErrorPage();
        assertEquals(ErrorController.INTERNAL_SERVER_ERROR_PAGE_VIEW, view);
    }
}
