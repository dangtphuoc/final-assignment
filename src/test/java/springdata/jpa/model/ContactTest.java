package springdata.jpa.model;

import org.junit.Test;

import springdata.jpa.TestUtil;
import springdata.jpa.model.Address;
import springdata.jpa.model.Contact;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Petri Kainulainen
 */
public class ContactTest {

    private static final String EMAIL_ADDRESS = "foo.bar@bar.com";
    private static final String FIRST_NAME = "Foo";
    private static final String LAST_NAME = "Bar";
    private static final String PHONE_NUMBER = "1234567";

    private static final String STREET_ADDRESS = "streetAddress";
    private static final String POST_CODE = "postCode";
    private static final String POST_OFFICE = "postOffice";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";


    @Test
    public void buildWithMandatoryValues() {
        Contact build = Contact.getBuilder(FIRST_NAME, LAST_NAME).build();

        assertNull(build.getId());

        assertEquals(FIRST_NAME, build.getFirstName());
        assertEquals(LAST_NAME, build.getLastName());

        assertNull(build.getAddress());
        assertNull(build.getEmailAddress());
        assertNull(build.getPhoneNumber());
    }

    @Test
    public void buildWithMandatoryValuesAndAddress() {
        Contact build = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .address(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY)
                .build();

        assertNull(build.getId());

        assertEquals(FIRST_NAME, build.getFirstName());
        assertEquals(LAST_NAME, build.getLastName());

        Address address = build.getAddress();

        assertEquals(STREET_ADDRESS, address.getStreetAddress());
        assertEquals(POST_CODE, address.getPostCode());
        assertEquals(POST_OFFICE, address.getPostOffice());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());

        assertNull(build.getEmailAddress());
        assertNull(build.getPhoneNumber());
    }

    @Test
    public void buildWithMandatoryValuesAndEmailAddress() {
        Contact build = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .emailAddress(EMAIL_ADDRESS)
                .build();

        assertNull(build.getId());

        assertEquals(FIRST_NAME, build.getFirstName());
        assertEquals(LAST_NAME, build.getLastName());
        assertEquals(EMAIL_ADDRESS, build.getEmailAddress());

        assertNull(build.getAddress());
        assertNull(build.getPhoneNumber());
    }

    @Test
    public void buildWithMandatoryValuesAndPhoneNumber() {
        Contact build = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .phoneNumber(PHONE_NUMBER)
                .build();

        assertNull(build.getId());

        assertEquals(FIRST_NAME, build.getFirstName());
        assertEquals(LAST_NAME, build.getLastName());
        assertEquals(PHONE_NUMBER, build.getPhoneNumber());

        assertNull(build.getAddress());
        assertNull(build.getEmailAddress());
    }

    @Test
    public void buildWithAllValues() {
        Contact build = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .address(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY)
                .emailAddress(EMAIL_ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();

        assertNull(build.getId());

        assertEquals(FIRST_NAME, build.getFirstName());
        assertEquals(LAST_NAME, build.getLastName());

        Address address = build.getAddress();

        assertEquals(STREET_ADDRESS, address.getStreetAddress());
        assertEquals(POST_CODE, address.getPostCode());
        assertEquals(POST_OFFICE, address.getPostOffice());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());

        assertEquals(EMAIL_ADDRESS, build.getEmailAddress());
        assertEquals(PHONE_NUMBER, build.getPhoneNumber());
    }

    @Test
    public void update() {
        Contact updated = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .address(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY)
                .emailAddress(EMAIL_ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();

        String updatedFirstName = TestUtil.createUpdatedString(FIRST_NAME);
        String updatedLastName = TestUtil.createUpdatedString(LAST_NAME);
        String updatedEmailAddress = TestUtil.createUpdatedString(EMAIL_ADDRESS);
        String updatedPhoneNumber = TestUtil.createUpdatedString(PHONE_NUMBER);

        updated.update(updatedFirstName, updatedLastName, updatedEmailAddress, updatedPhoneNumber);

        assertEquals(updatedFirstName, updated.getFirstName());
        assertEquals(updatedLastName, updated.getLastName());
        assertEquals(updatedEmailAddress, updated.getEmailAddress());
        assertEquals(updatedPhoneNumber, updatedPhoneNumber);

        Address address = updated.getAddress();

        assertEquals(STREET_ADDRESS, address.getStreetAddress());
        assertEquals(POST_CODE, address.getPostCode());
        assertEquals(POST_OFFICE, address.getPostOffice());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());
    }

    @Test
    public void updateAddress() {
        Contact updated = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .address(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY)
                .emailAddress(EMAIL_ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();

        String updatedStreetAddress = TestUtil.createUpdatedString(STREET_ADDRESS);
        String updatedPostCode = TestUtil.createUpdatedString(POST_CODE);
        String updatedPostOffice = TestUtil.createUpdatedString(POST_OFFICE);
        String updatedState = TestUtil.createUpdatedString(STATE);
        String updatedCountry = TestUtil.createUpdatedString(COUNTRY);

        updated.updateAddress(updatedStreetAddress, updatedPostCode, updatedPostOffice, updatedState, updatedCountry);

        assertEquals(FIRST_NAME, updated.getFirstName());
        assertEquals(LAST_NAME, updated.getLastName());

        Address address = updated.getAddress();

        assertEquals(updatedStreetAddress, address.getStreetAddress());
        assertEquals(updatedPostCode, address.getPostCode());
        assertEquals(updatedPostOffice, address.getPostOffice());
        assertEquals(updatedState, address.getState());
        assertEquals(updatedCountry, address.getCountry());

        assertEquals(EMAIL_ADDRESS, updated.getEmailAddress());
        assertEquals(PHONE_NUMBER, updated.getPhoneNumber());
    }

    @Test
    public void updateAddressWhenAddressIsNull() {
        Contact updated = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .emailAddress(EMAIL_ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();

        updated.updateAddress(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY);

        assertEquals(FIRST_NAME, updated.getFirstName());
        assertEquals(LAST_NAME, updated.getLastName());

        Address address = updated.getAddress();

        assertEquals(STREET_ADDRESS, address.getStreetAddress());
        assertEquals(POST_CODE, address.getPostCode());
        assertEquals(POST_OFFICE, address.getPostOffice());
        assertEquals(STATE, address.getState());
        assertEquals(COUNTRY, address.getCountry());

        assertEquals(EMAIL_ADDRESS, updated.getEmailAddress());
        assertEquals(PHONE_NUMBER, updated.getPhoneNumber());
    }
}
