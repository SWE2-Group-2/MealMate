package mci.softwareengineering2.group2.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mci.softwareengineering2.group2.data.Order;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setup() {
        this.order = new Order();
    }

    /**
     * This test was needed case it was a list.toString() implemented
     */
    @Test
    public void orderToStringTest() {
        assertDoesNotThrow(() -> order.toString());
    }

}
