package mci.softwareengineering2.group2.data;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_name")
/**
 * Represent the cart of an user
 * 
 * @version 1.0
 * @since 08.05.2024
 */
public class Cart extends AbstractEntity {

    @Id
    private Long id;
    @OneToMany
    private List<Meal> meals;
    @OneToOne
    private User user;

    /**
     * Gets teh id of the card
     * @return the id of the cart
     */
    public Long getId() {
        return id;
    }

    /**
     * Set a new id for an cart
     * @param id the new id of the cart
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get all meals in the cart
     * @return the meals or an empty ArrayList
     */
    public List<Meal> getMeals() {
        if (meals == null) {
            meals = new ArrayList<Meal>();
        }
        return meals;
    }

    /**
     * Sets a list of meals for the cart
     * @param meals list of meals for the cart
     */
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    /**
     * Get the owner of the cart
     * @return the user or null
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the owner of the cart
     * @param user the owner of the cart
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the amount of a specific meal in the cart
     * @param meal the meal, which should be counted
     * @return the amount, how often the meal appears in the cart
     */
    public int getMealAmount(Meal meal) {

        int amount = 0;

        for (Meal currentMeal : getMeals()) {
            if (currentMeal.equals(meal)) {
                amount++;
            }
        }

        return amount;
    }

    @Override
    public String toString() {
        return "Cart [id=" + id + ", meals=" + meals + ", user=" + user + "]";
    }
}