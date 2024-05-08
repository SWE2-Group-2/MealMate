package mci.softwareengineering2.group2.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_table")
/**
 * Represents a order a user took 
 * @version 1.0
 * @since 08.05.2024
 */
public class Order extends AbstractEntity{

    @Id
    private Long id;
    private Date startDate;
    private Date endDate;
    @OneToMany
    private List<Meal> meals;
    private String note;
    @OneToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private OrderState state;

    /**
     * Get the id of an order
     * @return the id of the order 
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the new id of an order
     * @param the new id of an order
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * The Date the order was created
     * @return the start date of the order or null
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the order
     * @param startDate the date the order was created
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get the date the order was fulfilled
     * @return the date the order was fulfilled or null
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set the date the order was fulfilled
     * @param endDate the date the order was fulfilled
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get the meals the user ordered
     * @return a list of meals the user orders or an empty ArrayList
     * 
     */
    public List<Meal> getMeals() {
        if(meals == null){
            meals = new ArrayList<Meal>();
        }
        return meals;
    }

    /**
     * Set a list of meals the user ordered
     * @param meals a list of meals the user ordered
     */
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    /**
     * Get the note a user wrote for the order
     * @return a note for the order or null
     */
    public String getNote() {
        return note;
    }

    /**
     * Set a note for the order
     * @param note the note for a order
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * The user that took the order
     * @return the user that took the order or null
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user that took the order
     * @param user that took the order
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * The State the order is in 
     * @return the state the order is in
     */
    public OrderState getState() {
        if(state == null){
            state = OrderState.ORDER_RECEIVED;
        }
        return state;
    }

    /**
     * Set teh new state of an order
     * @param state the new state of an order
     */
    public void setState(OrderState state) {
        this.state = state;
    }
    @Override
    public String toString() {
        return "Order [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", meals=" + meals + ", note="
                + note + ", user=" + user + ", state=" + state + "]";
    }
}