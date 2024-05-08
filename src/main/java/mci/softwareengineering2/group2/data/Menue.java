package mci.softwareengineering2.group2.data;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "menue_table")
/**
 * Represents a menu of a restaurant
 * @version 1.0
 * @since 08.05.2024
 */
public class Menue extends AbstractEntity{
    
    @Id
    @Column(name = "menueId")
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private boolean isFavorit;
    @OneToMany(mappedBy = "menu")
    private List<Meal> meals;

    /**
     * Get the id of an menu
     * @return the id of an menu 
     */
    public Long getId() {
        return id;
    }

    /**
     * Set a new id for an menu
     * @param id the new id for an meu
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of an menu
     * @return the name or null
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of an menu
     * @param name the name of the menu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the restaurant that sells this menu
     * @return the restaurant or null
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Set the restaurant that sells this menu
     * @param restaurant the restaurant that sellt this menu
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Get the information if this menu is a favorit menu
     * @return true or false if the menu is a favorit menu 
     */
    public boolean isFavorit() {
        return isFavorit;
    }

    /**
     * Set true or false if this menu is a favorit menu
     * @param isFavorit true or false
     */
    public void setFavorit(boolean isFavorit) {
        this.isFavorit = isFavorit;
    }

    /**
     * Get the list of the meals included in this menu
     * @return a list of meals or an empty ArrayList
     */
    public List<Meal> getMeals() {
        if(meals == null){
            meals = new ArrayList<Meal>(); 
        }
        return meals;
    }

    /**
     * Set a list of meals included in this menu
     * @param meals a list of meals
     */
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
    @Override
    public String toString() {
        return "Menue [id=" + id + ", name=" + name + ", restaurant=" + restaurant + ", isFavourit=" + isFavorit
                + ", meals=" + meals + "]";
    }
}
