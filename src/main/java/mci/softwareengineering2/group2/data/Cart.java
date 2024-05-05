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
public class Cart extends AbstractEntity{

    @Id
    private Long id;
    @OneToMany
    private List<Meal> meals;
    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Meal> getMeals() {
        if(meals == null){
            meals = new ArrayList<Meal>();
        }
        return meals;
    }
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public int getMealAmount(Meal meal){

        int amount = 0;

        for(Meal currentMeal : getMeals()){
            if(currentMeal.equals(meal)){
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