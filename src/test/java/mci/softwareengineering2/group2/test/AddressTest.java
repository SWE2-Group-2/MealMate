package mci.softwareengineering2.group2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mci.softwareengineering2.group2.data.Address;

public class AddressTest {

    private Address address;

    @BeforeEach
    public void setup(){
        this.address = new Address();
    }

    @Test
    public void correctAddress(){

        address.setCity("Innsbruck");
        address.setCountry("Österreich");
        address.setPostalCode("6020");
        address.setState("Tirol");
        address.setStreet("Universitätsstraße 15");

        assertEquals(true, address.isValid());
    }

    @Test
    public void failedAddress(){

        address.setCity("Innsbruck");
        address.setCountry("Österreich");
        address.setPostalCode("6020");
        address.setStreet("Universitätsstraße 15");

        assertNotEquals(true, address.isValid());
    }
}