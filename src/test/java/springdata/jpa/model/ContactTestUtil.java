package springdata.jpa.model;

import org.springframework.test.util.ReflectionTestUtils;

import springdata.jpa.dto.ContactDTO;
import springdata.jpa.model.Address;
import springdata.jpa.model.Contact;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Petri Kainulainen
 */
public class ContactTestUtil {

    private static final String CHARACTER = "a";

    private static final String EMAIL_ADDRESS = "foo.bar@bar.com";
    private static final String EMAIL_SUFFIX = "@bar.com";
    private static final String FIRST_NAME = "Foo";
    private static final String LAST_NAME = "Bar";
    private static final String PHONE_NUMBER = "1234567";

    private static final String STREET_ADDRESS = "streetAddress";
    private static final String POST_CODE = "postCode";
    private static final String POST_OFFICE = "postOffice";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";

    public static void assertContact(ContactDTO dto, Contact model) {
        assertEquals(dto.getId(), model.getId());
        assertEquals(dto.getFirstName(), model.getFirstName());
        assertEquals(dto.getLastName(), model.getLastName());
        assertEquals(dto.getEmailAddress(), model.getEmailAddress());
        assertEquals(dto.getPhoneNumber(), model.getPhoneNumber());

        Address address = model.getAddress();
        if (address != null) {
            assertEquals(dto.getStreetAddress(), address.getStreetAddress());
            assertEquals(dto.getPostCode(), address.getPostCode());
            assertEquals(dto.getPostOffice(), address.getPostOffice());
            assertEquals(dto.getState(), address.getState());
            assertEquals(dto.getCountry(), address.getCountry());
        }
        else {
            assertNull(dto.getStreetAddress());
            assertNull(dto.getPostCode());
            assertNull(dto.getPostOffice());
            assertNull(dto.getState());
            assertNull(dto.getCountry());
        }
    }

    public static ContactDTO createDTO() {
        return createDTO(null);
    }

    public static ContactDTO createDTO(Long id) {
        ContactDTO dto = new ContactDTO();

        dto.setId(id);
        dto.setCountry(COUNTRY);
        dto.setEmailAddress(EMAIL_ADDRESS);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setPhoneNumber(PHONE_NUMBER);
        dto.setPostCode(POST_CODE);
        dto.setPostOffice(POST_OFFICE);
        dto.setState(STATE);
        dto.setStreetAddress(STREET_ADDRESS);

        return dto;
    }

    public static ContactDTO createDTOWithLooLongFields() {
        ContactDTO dto = new ContactDTO();

        dto.setCountry(createStringWithLength(Address.MAX_LENGTH_COUNTRY + 1));
        dto.setEmailAddress(createEmailAddressWithLength(Contact.MAX_LENGTH_EMAIL_ADDRESS + 1));
        dto.setFirstName(createStringWithLength(Contact.MAX_LENGTH_FIRST_NAME + 1));
        dto.setLastName(createStringWithLength(Contact.MAX_LENGTH_LAST_NAME + 1));
        dto.setPhoneNumber(createStringWithLength(Contact.MAX_LENGTH_PHONE_NUMBER + 1));
        dto.setPostCode(createStringWithLength(Address.MAX_LENGTH_POST_CODE + 1));
        dto.setPostOffice(createStringWithLength(Address.MAX_LENGTH_POST_OFFICE + 1));
        dto.setState(createStringWithLength(Address.MAX_LENGTH_STATE + 1));
        dto.setStreetAddress(createStringWithLength(Address.MAX_LENGTH_STREET_ADDRESS + 1));

        return dto;
    }

    private static String createEmailAddressWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        length = length - EMAIL_SUFFIX.length();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        builder.append(EMAIL_SUFFIX);

        return builder.toString();
    }

    private static final String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }

        return builder.toString();
    }

    public static Contact createModel(Long id) {
        Contact contact = Contact.getBuilder(FIRST_NAME, LAST_NAME)
                .address(STREET_ADDRESS, POST_CODE, POST_OFFICE, STATE, COUNTRY)
                .emailAddress(EMAIL_ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .build();

        ReflectionTestUtils.setField(contact, "id", id);

        return contact;
    }
}
