package springdata.jpa.service;


import java.util.List;

import springdata.jpa.dto.ContactDTO;
import springdata.jpa.model.Contact;

/**
 * Specifies the service methods for contracts.
 * @author Petri Kainulainen
 */
public interface ContactService {

    /**
     * Adds a new contact.
     * @param added The information of the added contact.
     * @return  The added contact.
     */
    public Contact add(ContactDTO added);

    /**
     * Deletes a contact.
     * @param id    The id of the deleted contact.
     * @return  The deleted contact.
     * @throws NotFoundException    if a contact is not found with the given id.
     */
    public Contact deleteById(Long id) throws NotFoundException;

    /**
     * Finds all contacts.
     * @return  A list of contacts. If no contacts is found this method returns an empty list.
     */
    public List<Contact> findAll();

    /**
     * Finds a contact.
     * @param id    The id of the wanted contact.
     * @return  The found contact.
     * @throws NotFoundException    if no contact is found with the given id.
     */
    public Contact findById(Long id) throws NotFoundException;

    /**
     * Updates the information of a contact.
     * @param updated   The new information of a contact.
     * @return  The updated contact.
     * @throws NotFoundException    if no contact is found with the provided id.
     */
    public Contact update(ContactDTO updated) throws NotFoundException;
}
