package mci.softwareengineering2.group2.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meal_table")
/**
 * Represents a meal 
 * @version 1.0
 * @since 08.05.2024 
 */
public class Meal extends AbstractEntity{
    @Id
    private Long id;
    private String name;
    private float price;
    private String allergene;
    private String description;
    @ManyToOne
    @JoinColumn(name = "menueId")
    private Menue menu;
    @Column(length = 500)
    private String picture;

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

    /**
     * Get teh price for the meal
     * @return the price of the meal
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the new price for the meal
     * @param price the new price for the meal
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Get the allergene of an meal
     * @return the allergene of an meal
     */
    public String getAllergene() {
        return allergene;
    }

    /**
     * Set new allergene for a meal
     * @param allergene teh enw allergene for the meal
     */
    public void setAllergene(String allergene) {
        this.allergene = allergene;
    }

    /**
     * Get the description of an meal
     * @return the description or null
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the new description for a meal
     * @param description the new description of the meal
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the menu, which the meal appears in 
     * @return the menu or null
     */
    public Menue getMenu() {
        return menu;
    }

    /**
     * Set the menu, which the meal appears in
     * @param menu the menu
     */
    public void setMenu(Menue menu) {
        this.menu = menu;
    }

    /**
     * Get a image Url for the picture 
     * @return the image Url or null
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Set the image Url for a picture
     * @param picture the image Url for a picture 
     * 
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString() {
        return "Meal [id=" + id + ", name=" + name + ", price=" + price + ", allergene=" + allergene + ", description="
                + description + ", menue=" + menu + ", picture=" + picture + "]";
    }
}
