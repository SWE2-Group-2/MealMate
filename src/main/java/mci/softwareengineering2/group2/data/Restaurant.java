package mci.softwareengineering2.group2.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_table")
/**
 * Represents a restaurant
 * @version 1.0
 * @since 08.05.2024 
 */
public class Restaurant extends AbstractEntity{

    @Id
    @Column(name = "restaurant_id")
    private Long id;
    private String name;
    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;
    private int rating;
    private String tags;
    
    /**
     * Get the id of an restaurant
     * @return the id of the restaurant
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the new id of an restaurant
     * @param id the new id of the restaurant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the restaurant
     * @return the name of the restaurant or null
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the restaurant
     * @param name the new name of the restaurant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the address of the restaurant
     * @return the address or null
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set the address of an restaurant
     * @param address the address of the restaurant
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Get the rating of the restaurant between 0 and 5
     * @return the rating of the restaurant between 0 and 5
     */
    public int getRating() {

        if(rating < 0){
            return 0;
        }else if(rating > 5){
            return 5;
        }

        return rating;
    }

    /**
     * Set the rating for the restaurant between 0 and 5
     * @param rating between 0 and 5.
     */
    public void setRating(int rating) {

        if(rating < 0){
            rating = 0;
        }else if(rating > 5){
            rating = 5;
        }

        this.rating = rating;
    }

    /**
     * Get the tags of an restaurant
     * @return the tags of the restaurant as String or null
     */
    public String getTags() {
        return tags;
    }

    /**
     * Set the tags for an restaurant
     * @param tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", rating=" + rating + ", tags="
                + tags + "]";
    }
}