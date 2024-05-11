package mci.softwareengineering2.group2.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category_table")
/**
 * Represents a meal category
 * @version 1.0
 * @since 08.05.2024 
 */
public class Category extends AbstractEntity{
    @Id
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_meal",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;

    /**
     * Get the id of the meal
     * @return the id of the meal 
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the new id of the meal
     * @param id the new id of the meal
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get the name of the meal
     * @return teh name of the meal or null
     */
    public String getName() {
        return name;
    }

    /**
     * Set a new name for a meal
     * @param name the name of the meal
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Delete meal from category */
    public void deleteMeal(Meal meal) {
        meals.remove(meal);
    }
    
    /**
     * Get the meals of a category
     * @return the meals of a category or null
     */
    public List<Meal> getMeals() {
        if(meals == null){
            meals = new ArrayList<Meal>();
        }
        return meals;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", meals=" + meals +
                '}';
    }
}
