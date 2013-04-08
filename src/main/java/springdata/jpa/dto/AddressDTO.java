package springdata.jpa.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AddressDTO {

    private String country;

    private String streetAddress;

    private String postCode;

    private String postOffice;

    private String state;

    public AddressDTO() {

    }

    public String getCountry() {
        return country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public String getState() {
        return state;
    }

}
