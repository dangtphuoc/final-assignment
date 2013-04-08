package springdata.jpa.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import springdata.jpa.model.Address;
import springdata.jpa.model.Contact;

/**
 * A form object for contracts.
 * @author Petri Kainulainen
 */
public class ContactDTO {

    private Long id;

    @Length(max = Address.MAX_LENGTH_COUNTRY)
    private String country;

    @Email
    @Length(max = Contact.MAX_LENGTH_EMAIL_ADDRESS)
    private String emailAddress;

    @NotEmpty
    @Length(max = Contact.MAX_LENGTH_FIRST_NAME)
    private String firstName;

    @NotEmpty
    @Length(max = Contact.MAX_LENGTH_LAST_NAME)
    private String lastName;

    @Length(max = Contact.MAX_LENGTH_PHONE_NUMBER)
    private String phoneNumber;

    @Length(max = Address.MAX_LENGTH_POST_CODE)
    private String postCode;

    @Length(max = Address.MAX_LENGTH_POST_OFFICE)
    private String postOffice;

    @Length(max = Address.MAX_LENGTH_STATE)
    private String state;

    @Length(max = Address.MAX_LENGTH_STREET_ADDRESS)
    private String streetAddress;

    public ContactDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
