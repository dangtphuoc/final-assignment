package springdata.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import springdata.jpa.dto.ContactDTO;
import springdata.jpa.model.Address;
import springdata.jpa.model.Contact;
import springdata.jpa.service.ContactService;
import springdata.jpa.service.NotFoundException;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * A controller processing requests concerning contact information.
 * @author Petri Kainulainen
 */
@Controller
@SessionAttributes("contact")
public class ContactController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    protected static final String FEEDBACK_MESSAGE_KEY_CONTACT_ADDED = "feedback.message.contact.added";
    protected static final String FEEDBACK_MESSAGE_KEY_CONTACT_UPDATED = "feedback.message.contact.updated";
    protected static final String FEEDBACK_MESSAGE_KEY_CONTACT_DELETED = "feedback.message.contact.deleted";

    protected static final String FLASH_MESSAGE_KEY_ERROR = "errorMessage";
    protected static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";

    protected static final String ADD_CONTACT_VIEW = "add";
    protected static final String CONTACT_VIEW = "view";
    protected static final String HOME_VIEW = "list";
    protected static final String UPDATE_CONTACT_VIEW = "update";

    protected static final String MODEL_ATTRIBUTE_CONTACT = "contact";
    protected static final String MODEL_ATTRIBUTE_CONTACTS = "contacts";

    protected static final String PARAMETER_CONTACT_ID = "id";

    protected static final String REQUEST_MAPPING_VIEW_CONTACT = "/contact/{id}";

    @Resource
    private ContactService service;

    @Resource
    private MessageSource messageSource;

    /**
     * Processes the submit of add contact form
     * @param dto   The form object.
     * @param result    The binding result describing whether there is validation errors.
     * @param attributes    The attributes used to when the request is redirected.
     * @return  A redirect view  name to the view contract page.
     */
    @RequestMapping(value = "/contact/add", method = RequestMethod.POST)
    public String addContact(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CONTACT) ContactDTO dto, BindingResult result, RedirectAttributes attributes) {
        LOGGER.debug("Adding new contact with information: {}", dto);

        if (result.hasErrors()) {
            LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
            return ADD_CONTACT_VIEW;
        }

        Contact added = service.add(dto);
        LOGGER.debug("Added contact with information: {}", added);

        attributes.addAttribute(PARAMETER_CONTACT_ID, added.getId());
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CONTACT_ADDED, added.getFirstName(), added.getLastName());

        return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONTACT);
    }

    /**
     * Processes delete contact ajax request.
     * @param id    The id of the deleted contact.
     * @return  The feedback message.
     * @throws NotFoundException    if no contact is found with the given id.
     */
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteContact(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting contact with id: {}", id);

        Contact deleted = service.deleteById(id);
        LOGGER.debug("Deleted contact: {}", deleted);

        return getMessage(FEEDBACK_MESSAGE_KEY_CONTACT_DELETED, deleted.getFirstName(), deleted.getLastName());
    }

    /**
     * Shows the add contact page.
     * @param model The model.
     * @return  The name of the add contact view.
     */
    @RequestMapping(value = "/contact/add", method = RequestMethod.GET)
    public String showAddContactPage(Model model) {
        LOGGER.debug("Showing add contact page");

        ContactDTO formObject = new ContactDTO();
        model.addAttribute(MODEL_ATTRIBUTE_CONTACT, formObject);

        return ADD_CONTACT_VIEW;
    }

    /**
     * Shows the view contact page
     * @param id    The id of the viewed contact.
     * @param model The model.
     * @return  The name of the view contact view.
     * @throws NotFoundException    if not contact is found iwth the given id.
     */
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    public String showContactPage(@PathVariable("id") Long id, Model model) throws NotFoundException {
        LOGGER.debug("Showing contact page for contact with id: {}", id);

        Contact found = service.findById(id);
        LOGGER.debug("Found contact: {}", found);

        model.addAttribute(MODEL_ATTRIBUTE_CONTACT, found);
        return CONTACT_VIEW;
    }

    /**
     * Shows the home page.
     * @param model The model.
     * @return  The name of the home page view.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage(Model model) {
        LOGGER.debug("Rendering home page");

        //List<Contact> contacts = service.findAll();
        //model.addAttribute(MODEL_ATTRIBUTE_CONTACTS, contacts);

        return HOME_VIEW;
    }

    /**
     * Shows update contact page.
     * @param id    The id of the updated contact.
     * @param model The model.
     * @return  The name of the update contact view.
     * @throws NotFoundException    if no contact is found with the given id.
     */
    @RequestMapping(value = "/contact/update/{id}", method = RequestMethod.GET)
    public String showUpdateContactPage(@PathVariable("id") Long id, Model model) throws NotFoundException {
        LOGGER.debug("Showing edit contact for a contact with id: {}", id);

        Contact updated = service.findById(id);
        LOGGER.debug("Found contact: {}", updated);

        ContactDTO formObject = createFormObject(updated);
        model.addAttribute(MODEL_ATTRIBUTE_CONTACT, formObject);

        return UPDATE_CONTACT_VIEW;
    }

    /**
     * Creates a form object.
     * @param model The model in which the form object is based.
     * @return  The created form object.
     */
    private ContactDTO createFormObject(Contact model) {
        ContactDTO dto = new ContactDTO();

        dto.setId(model.getId());

        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmailAddress(model.getEmailAddress());
        dto.setPhoneNumber(model.getPhoneNumber());

        Address address = model.getAddress();
        if (address != null) {
            dto.setStreetAddress(address.getStreetAddress());
            dto.setPostCode(address.getPostCode());
            dto.setPostOffice(address.getPostOffice());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
        }

        return dto;
    }

    /**
     * Processes the submit of update contact form.
     * @param dto   The form object.
     * @param result    The binding result describing whether there is validation errors.
     * @param attributes    The redirect attributes used when request is redirected forward.
     * @return  A redirect view  name to the view contract page.
     * @throws NotFoundException
     */
    @RequestMapping(value = "/contact/update", method = RequestMethod.POST)
    public String updateContact(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CONTACT) ContactDTO dto, BindingResult result, RedirectAttributes attributes) throws NotFoundException {
        LOGGER.debug("Updating contact with information: {}", dto);

        if (result.hasErrors()) {
            LOGGER.debug("Form was submitted with validation errors. Rendering form view.");
            return UPDATE_CONTACT_VIEW;
        }

        Contact updated = service.update(dto);
        LOGGER.debug("Updated contact with information: {}", updated);

        attributes.addAttribute(PARAMETER_CONTACT_ID, updated.getId());
        addFeedbackMessage(attributes, FEEDBACK_MESSAGE_KEY_CONTACT_UPDATED, updated.getFirstName(), updated.getLastName());

        return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONTACT);
    }

    /**
     * Adds a flash feedback message.
     * @param model The model which contains the message.
     * @param code  The code used to fetch the localized message.
     * @param params    The params of the message.
     */
    private void addFeedbackMessage(RedirectAttributes model, String code, Object... params) {
        LOGGER.debug("Adding feedback message with code: {} and params: {}", code, params);
        String localizedFeedbackMessage = getMessage(code, params);
        LOGGER.debug("Localized message is: {}", localizedFeedbackMessage);
        model.addFlashAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedFeedbackMessage);
    }

    /**
     * Gets a message from the message source.
     * @param code  The message code.
     * @param params    The params of the message.
     * @return  The localized message.
     */
    private String getMessage(String code, Object... params) {
        Locale current = LocaleContextHolder.getLocale();
        LOGGER.debug("Current locale is {}", current);
        return messageSource.getMessage(code, params, current);
    }

    /**
     * Creates a redirect view path.
     * @param requestMapping    The request mapping of target controller method.
     * @return  The created redirect view path.
     */
    private String createRedirectViewPath(String requestMapping) {
        StringBuilder redirectViewPath = new StringBuilder();
        redirectViewPath.append("redirect:");
        redirectViewPath.append(requestMapping);
        return redirectViewPath.toString();
    }
}
