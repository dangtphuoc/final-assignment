package springdata.jpa.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import springdata.jpa.config.TestContext;
import springdata.jpa.controller.ContactController;
import springdata.jpa.dto.ContactDTO;
import springdata.jpa.model.Contact;
import springdata.jpa.model.ContactTestUtil;
import springdata.jpa.service.ContactService;
import springdata.jpa.service.NotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Petri Kainulainen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class ContactControllerTest {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String FEEDBACK_MESSAGE = "feedbackMessage";

    private static final String FIELD_NAME_COUNTRY = "country";
    private static final String FIELD_NAME_EMAIL_ADDRESS = "emailAddress";
    private static final String FIELD_NAME_FIRST_NAME = "firstName";
    private static final String FIELD_NAME_LAST_NAME = "lastName";
    private static final String FIELD_NAME_PHONE_NUMBER = "phoneNumber";
    private static final String FIELD_NAME_POST_CODE = "postCode";
    private static final String FIELD_NAME_POST_OFFICE = "postOffice";
    private static final String FIELD_NAME_STATE = "state";
    private static final String FIELD_NAME_STREET_ADDRESS = "streetAddress";

    private static final Long ID = Long.valueOf(3);

    private static final String INVALID_EMAIL_ADDRESS = "invalid";

    private ContactController controller;

    private MessageSource messageSourceMock;

    private ContactService serviceMock;

    @Resource
    private Validator validator;

    @Before
    public void setUp() {
        controller = new ContactController();

        messageSourceMock = mock(MessageSource.class);
        ReflectionTestUtils.setField(controller, "messageSource", messageSourceMock);

        serviceMock = mock(ContactService.class);
        ReflectionTestUtils.setField(controller, "service", serviceMock);
    }

    @Test
    public void addContact() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = ContactTestUtil.createDTO();

        Contact model = ContactTestUtil.createModel(ID);
        when(serviceMock.add(formObject)).thenReturn(model);

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        initMessageSourceForFeedbackMessage(ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_ADDED);

        String view = controller.addContact(formObject, result, attributes);

        verify(serviceMock, times(1)).add(formObject);
        verifyNoMoreInteractions(serviceMock);

        String expectedView = createExpectedRedirectViewPath(ContactController.REQUEST_MAPPING_VIEW_CONTACT);

        assertEquals(expectedView, view);

        Long actualId = Long.valueOf((String) attributes.asMap().get(ContactController.PARAMETER_CONTACT_ID));
        assertEquals(model.getId(), actualId);

        assertFeedbackMessage(attributes, ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_ADDED);
    }

    @Test
    public void addEmptyContact() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = new ContactDTO();
        ReflectionTestUtils.setField(formObject, "firstName", "");
        ReflectionTestUtils.setField(formObject, "lastName", "");

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.addContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.ADD_CONTACT_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void addContactWithInvalidEmailAddress() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = ContactTestUtil.createDTO();
        ReflectionTestUtils.setField(formObject, "emailAddress", INVALID_EMAIL_ADDRESS);

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.addContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.ADD_CONTACT_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_EMAIL_ADDRESS);

    }

    @Test
    public void addContactWithTooLongFieldValues() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = ContactTestUtil.createDTOWithLooLongFields();

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.addContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.ADD_CONTACT_VIEW, view);
        assertFieldErrors(result,
                FIELD_NAME_COUNTRY,
                FIELD_NAME_EMAIL_ADDRESS,
                FIELD_NAME_FIRST_NAME,
                FIELD_NAME_LAST_NAME,
                FIELD_NAME_PHONE_NUMBER,
                FIELD_NAME_POST_CODE,
                FIELD_NAME_POST_OFFICE,
                FIELD_NAME_STATE,
                FIELD_NAME_STREET_ADDRESS);
    }

    @Test
    public void deleteContact() throws NotFoundException {
        Contact deleted = ContactTestUtil.createModel(ID);
        when(serviceMock.deleteById(ID)).thenReturn(deleted);

        initMessageSourceForFeedbackMessage(ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_DELETED);

        String feedbackMessage = controller.deleteContact(ID);

        verify(serviceMock, times(1)).deleteById(ID);
        verifyNoMoreInteractions(serviceMock);

        verify(messageSourceMock, times(1)).getMessage(eq(ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_DELETED), any(Object[].class), any(Locale.class));
        verifyNoMoreInteractions(messageSourceMock);

        assertEquals(FEEDBACK_MESSAGE, feedbackMessage);
    }

    @Test(expected = NotFoundException.class)
    public void deleteContactWhenContactIsNotFound() throws NotFoundException {
        when(serviceMock.deleteById(ID)).thenThrow(new NotFoundException(""));

        controller.deleteContact(ID);

        verify(serviceMock, times(1)).deleteById(ID);
        verifyNoMoreInteractions(serviceMock);
        verifyZeroInteractions(messageSourceMock);
    }

    @Test
    public void showAddContactPage() {
        Model model = new BindingAwareModelMap();

        String view = controller.showAddContactPage(model);

        assertEquals(ContactController.ADD_CONTACT_VIEW, view);
        ContactDTO formObject = (ContactDTO) model.asMap().get(ContactController.MODEL_ATTRIBUTE_CONTACT);
        assertNotNull(formObject);

        assertNull(formObject.getId());
        assertNull(formObject.getFirstName());
        assertNull(formObject.getLastName());
        assertNull(formObject.getEmailAddress());
        assertNull(formObject.getPhoneNumber());
        assertNull(formObject.getStreetAddress());
        assertNull(formObject.getPostCode());
        assertNull(formObject.getPostOffice());
        assertNull(formObject.getState());
        assertNull(formObject.getCountry());
    }

    @Test
    public void showContactPage() throws NotFoundException {
        Contact contact = ContactTestUtil.createModel(ID);
        when(serviceMock.findById(ID)).thenReturn(contact);

        Model model = new BindingAwareModelMap();
        String view = controller.showContactPage(ID, model);

        verify(serviceMock, times(1)).findById(ID);
        verifyNoMoreInteractions(serviceMock);

        assertEquals(ContactController.CONTACT_VIEW, view);
        assertEquals(contact, model.asMap().get(ContactController.MODEL_ATTRIBUTE_CONTACT));
    }

    @Test(expected = NotFoundException.class)
    public void showContactPageWhenContactIsNotFound() throws NotFoundException {
        when(serviceMock.findById(ID)).thenThrow(new NotFoundException(""));

        Model model = new BindingAwareModelMap();
        controller.showContactPage(ID, model);

        verify(serviceMock, times(1)).findById(ID);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void showHomePage() {
        List<Contact> contacts = new ArrayList<Contact>();
        when(serviceMock.findAll()).thenReturn(contacts);

        Model model = new BindingAwareModelMap();
        String view = controller.showHomePage(model);

        verify(serviceMock, times(1)).findAll();
        verifyNoMoreInteractions(serviceMock);

        assertEquals(ContactController.HOME_VIEW, view);
        assertEquals(contacts, model.asMap().get(ContactController.MODEL_ATTRIBUTE_CONTACTS));
    }

    @Test
    public void showUpdateContactPage() throws NotFoundException {
        Contact edited = ContactTestUtil.createModel(ID);
        when(serviceMock.findById(ID)).thenReturn(edited);

        Model model = new BindingAwareModelMap();

        String view = controller.showUpdateContactPage(ID, model);

        verify(serviceMock, times(1)).findById(ID);
        verifyNoMoreInteractions(serviceMock);

        assertEquals(ContactController.UPDATE_CONTACT_VIEW, view);

        ContactDTO formObject = (ContactDTO) model.asMap().get(ContactController.MODEL_ATTRIBUTE_CONTACT);
        ContactTestUtil.assertContact(formObject, edited);
    }

    @Test
    public void showUpdateContactPageWhenAddressIsNull() throws NotFoundException {
        Contact updated = ContactTestUtil.createModel(ID);
        ReflectionTestUtils.setField(updated, "address", null);

        when(serviceMock.findById(ID)).thenReturn(updated);

        Model model = new BindingAwareModelMap();

        String view = controller.showUpdateContactPage(ID, model);

        verify(serviceMock, times(1)).findById(ID);
        verifyNoMoreInteractions(serviceMock);

        assertEquals(ContactController.UPDATE_CONTACT_VIEW, view);

        ContactDTO formObject = (ContactDTO) model.asMap().get(ContactController.MODEL_ATTRIBUTE_CONTACT);
        ContactTestUtil.assertContact(formObject, updated);
    }

    @Test(expected = NotFoundException.class)
    public void showUpdateContactPageWhenContactIsNotFound() throws NotFoundException {
        when(serviceMock.findById(ID)).thenThrow(new NotFoundException(""));

        Model model = new BindingAwareModelMap();
        controller.showUpdateContactPage(ID, model);

        verify(serviceMock, times(1)).findById(ID);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void updateContact() throws NotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/update");

        Contact updated = ContactTestUtil.createModel(ID);
        ContactDTO formObject = ContactTestUtil.createDTO(ID);
        when(serviceMock.update(formObject)).thenReturn(updated);

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        initMessageSourceForFeedbackMessage(ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_UPDATED);

        String view = controller.updateContact(formObject, result, attributes);

        verify(serviceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(serviceMock);

        String expectedView = createExpectedRedirectViewPath(ContactController.REQUEST_MAPPING_VIEW_CONTACT);

        assertEquals(expectedView, view);

        Long actualId = Long.valueOf((String) attributes.asMap().get(ContactController.PARAMETER_CONTACT_ID));
        assertEquals(updated.getId(), actualId);

        assertFeedbackMessage(attributes, ContactController.FEEDBACK_MESSAGE_KEY_CONTACT_UPDATED);
    }

    @Test(expected = NotFoundException.class)
    public void updateContactWhenContactIsNotFound() throws NotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/update");

        ContactDTO formObject = ContactTestUtil.createDTO(ID);
        when(serviceMock.update(formObject)).thenThrow(new NotFoundException(""));

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        controller.updateContact(formObject, result, attributes);

        verify(serviceMock, times(1)).update(formObject);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void updateEmptyContact() throws NotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = new ContactDTO();
        ReflectionTestUtils.setField(formObject, "firstName", "");
        ReflectionTestUtils.setField(formObject, "lastName", "");

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.updateContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.UPDATE_CONTACT_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_FIRST_NAME, FIELD_NAME_LAST_NAME);
    }

    @Test
    public void updateContactWithInvalidEmailAddress() throws NotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = ContactTestUtil.createDTO();
        ReflectionTestUtils.setField(formObject, "emailAddress", INVALID_EMAIL_ADDRESS);

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.updateContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.UPDATE_CONTACT_VIEW, view);
        assertFieldErrors(result, FIELD_NAME_EMAIL_ADDRESS);

    }

    @Test
    public void updateContactWithTooLongFieldValues() throws NotFoundException {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest("POST", "/contact/add");
        ContactDTO formObject = ContactTestUtil.createDTOWithLooLongFields();

        BindingResult result = bindAndValidate(mockRequest, formObject);
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        String view = controller.updateContact(formObject, result, attributes);

        verifyZeroInteractions(serviceMock);
        assertEquals(ContactController.UPDATE_CONTACT_VIEW, view);
        assertFieldErrors(result,
                FIELD_NAME_COUNTRY,
                FIELD_NAME_EMAIL_ADDRESS,
                FIELD_NAME_FIRST_NAME,
                FIELD_NAME_LAST_NAME,
                FIELD_NAME_PHONE_NUMBER,
                FIELD_NAME_POST_CODE,
                FIELD_NAME_POST_OFFICE,
                FIELD_NAME_STATE,
                FIELD_NAME_STREET_ADDRESS);
    }

    private void assertErrorMessage(RedirectAttributes model, String messageCode) {
        assertFlashMessages(model, messageCode, ContactController.FLASH_MESSAGE_KEY_ERROR);
    }

    private void assertFeedbackMessage(RedirectAttributes model, String messageCode) {
        assertFlashMessages(model, messageCode, ContactController.FLASH_MESSAGE_KEY_FEEDBACK);
    }

    private void assertFlashMessages(RedirectAttributes model, String messageCode, String flashMessageParameterName) {
        Map<String, ?> flashMessages = model.getFlashAttributes();
        Object message = flashMessages.get(flashMessageParameterName);
        assertNotNull(message);
        flashMessages.remove(message);
        assertTrue(flashMessages.isEmpty());

        verify(messageSourceMock, times(1)).getMessage(eq(messageCode), any(Object[].class), any(Locale.class));
        verifyNoMoreInteractions(messageSourceMock);
    }

    private void assertFieldErrors(BindingResult result, String... fieldNames) {
        assertEquals(fieldNames.length, result.getFieldErrorCount());
        for (String fieldName : fieldNames) {
            assertNotNull(result.getFieldError(fieldName));
        }
    }

    private BindingResult bindAndValidate(HttpServletRequest request, Object formObject) {
        WebDataBinder binder = new WebDataBinder(formObject);
        binder.setValidator(validator);
        binder.bind(new MutablePropertyValues(request.getParameterMap()));
        binder.getValidator().validate(binder.getTarget(), binder.getBindingResult());
        return binder.getBindingResult();
    }

    private String createExpectedRedirectViewPath(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("redirect:");
        builder.append(path);
        return builder.toString();
    }

    private void initMessageSourceForErrorMessage(String errorMessageCode) {
        when(messageSourceMock.getMessage(eq(errorMessageCode), any(Object[].class), any(Locale.class))).thenReturn(ERROR_MESSAGE);
    }

    private void initMessageSourceForFeedbackMessage(String feedbackMessageCode) {
        when(messageSourceMock.getMessage(eq(feedbackMessageCode), any(Object[].class), any(Locale.class))).thenReturn(FEEDBACK_MESSAGE);
    }
}
