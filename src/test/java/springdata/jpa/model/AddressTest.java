package springdata.jpa.model;

import org.junit.Test;

import springdata.jpa.TestUtil;
import springdata.jpa.model.Address;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Petri Kainulainen
 */
public class AddressTest {

    private static final String STREET_ADDRESS = "streetAddress";
    private static final String POST_CODE = "postCode";
    private static final String POST_OFFICE = "postOffice";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";

    @Test
    public void buildWithMandatoryValues() {
        Address built = Address.getBuilder(STREET_ADDRESS, POST_CODE, POST_OFFICE).build();

        assertEquals(STREET_ADDRESS, built.getStreetAddress());
        assertEquals(POST_CODE, built.getPostCode());
        assertEquals(POST_OFFICE, built.getPostOffice());
        assertNull(built.getState());
        assertNull(built.getCountry());
    }

    @Test
    public void builtWithMandatoryValuesAndCountry() {
        Address built = Address.getBuilder(STREET_ADDRESS, POST_CODE, POST_OFFICE)
                .country(COUNTRY)
                .build();

        assertEquals(STREET_ADDRESS, built.getStreetAddress());
        assertEquals(POST_CODE, built.getPostCode());
        assertEquals(POST_OFFICE, built.getPostOffice());
        assertNull(built.getState());
        assertEquals(COUNTRY, built.getCountry());
    }

    @Test
    public void builtWithMandatoryValuesAndState() {
        Address built = Address.getBuilder(STREET_ADDRESS, POST_CODE, POST_OFFICE)
                .state(STATE)
                .build();

        assertEquals(STREET_ADDRESS, built.getStreetAddress());
        assertEquals(POST_CODE, built.getPostCode());
        assertEquals(POST_OFFICE, built.getPostOffice());
        assertEquals(STATE, built.getState());
        assertNull(built.getCountry());
    }

    @Test
    public void builtWithAllValues() {
        Address built = Address.getBuilder(STREET_ADDRESS, POST_CODE, POST_OFFICE)
                .state(STATE)
                .country(COUNTRY)
                .build();

        assertEquals(STREET_ADDRESS, built.getStreetAddress());
        assertEquals(POST_CODE, built.getPostCode());
        assertEquals(POST_OFFICE, built.getPostOffice());
        assertEquals(STATE, built.getState());
        assertEquals(COUNTRY, built.getCountry());
    }

    @Test
    public void update() {
        Address updated = Address.getBuilder(STREET_ADDRESS, POST_CODE, POST_OFFICE)
                .state(STATE)
                .country(COUNTRY)
                .build();

        String updatedStreetAddress = TestUtil.createUpdatedString(STREET_ADDRESS);
        String updatedPostCode = TestUtil.createUpdatedString(POST_CODE);
        String updatedPostOffice = TestUtil.createUpdatedString(POST_OFFICE);
        String updatedState = TestUtil.createUpdatedString(STATE);
        String updatedCountry = TestUtil.createUpdatedString(COUNTRY);

        updated.update(updatedStreetAddress, updatedPostCode, updatedPostOffice, updatedState, updatedCountry);

        assertEquals(updatedStreetAddress, updated.getStreetAddress());
        assertEquals(updatedPostCode, updated.getPostCode());
        assertEquals(updatedPostOffice, updated.getPostOffice());
        assertEquals(updatedState, updated.getState());
        assertEquals(updatedCountry, updated.getCountry());
    }


}
