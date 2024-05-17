package mci.softwareengineering2.group2.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.data.Meal;

public class MealTest {

    private Meal meal;

    @BeforeEach
    public void buildObjects() {
        this.meal = new Meal();

    }

    @Test
    public void correctMeal() {
        meal.setName("Pizza");
        meal.setAllergene("Mehl,Ei");

        List<Category> categories = new ArrayList<Category>();
        Category austrian = new Category();
        austrian.setName("Österreichisch");
        categories.add(austrian);

        meal.setCategory(categories);
        meal.setPrice(19.99f);

        assertEquals(true, meal.isMealCorrect());
    }

    @Test
    public void failedMeal() {
        meal.setName("Pizza");
        meal.setAllergene("Mehl,Ei");

        List<Category> categories = new ArrayList<Category>();
        Category austrian = new Category();
        austrian.setName("Österreichisch");
        categories.add(austrian);

        meal.setCategory(categories);

        assertNotEquals(true, meal.isMealCorrect());
    }

}
