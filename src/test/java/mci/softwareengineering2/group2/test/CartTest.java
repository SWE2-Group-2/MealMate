package mci.softwareengineering2.group2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;

public class CartTest {

    private Cart cart;

    @BeforeEach
    public void setup(){
        cart = new Cart();
    }

    @Test
    public void filledCart(){
        List<Meal> mealList = new ArrayList<Meal>(); 

        for(int i = 0; i < 10; i++){
            Meal meal = new Meal();
            meal.setName("Meal"+1);
            meal.setPrice(2.99f * i);
            mealList.add(meal);
        }

        cart.setMeals(mealList);

        assertEquals(10, cart.getSumMealAmount());
    }

    @Test
    public void emptyCart(){
        assertNotEquals(10, cart.getSumMealAmount());
    }

}
