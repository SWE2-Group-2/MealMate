package mci.softwareengineering2.group2.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address_table")
/**
 * Represents the address of an person or a restaurant
 * @version 1.0
 * @since 08.05.2024
 */
public class Address extends AbstractEntity {

    @Id
    @Column(name = "addressId")
    private Long id;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;

    /**
     * 
     * @return the street of an address or null
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set the street of an address
     * @param street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get the zip code of the address
     * @return the zip code or null
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the zip code of the address 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Get the city of an address
     * @return the city or null
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the city of an address
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the state of an address 
     * @return the state or null
     */
    public String getState() {
        return state;
    }

    /**
     * Set the state of an address
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * Get the country of an address
     * @return the country or null
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country of an address
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address [id=" + id + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city + ", state="
                + state + ", country=" + country + "]";
    }
}