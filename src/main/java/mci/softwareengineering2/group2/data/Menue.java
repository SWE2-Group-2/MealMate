package mci.softwareengineering2.group2.data;

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
public class Menue extends AbstractEntity{
    
    @Id
    @Column(name = "menueId")
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private boolean isFavourit;
    @OneToMany(mappedBy = "menue")
    private List<Meal> meals;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public boolean isFavourit() {
        return isFavourit;
    }
    public void setFavourit(boolean isFavourit) {
        this.isFavourit = isFavourit;
    }
    public List<Meal> getMeals() {
        return meals;
    }
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
    @Override
    public String toString() {
        return "Menue [id=" + id + ", name=" + name + ", restaurant=" + restaurant + ", isFavourit=" + isFavourit
                + ", meals=" + meals + "]";
    }
}
