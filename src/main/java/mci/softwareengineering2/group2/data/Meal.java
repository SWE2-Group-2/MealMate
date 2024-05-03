package mci.softwareengineering2.group2.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meal")
public class Meal extends AbstractEntity{
    @Id
    private Long id;
    private String name;
    private float price;
    private String allergene;
    private String description;
    @ManyToOne
    @JoinColumn(name = "menueId")
    private Menue menue;
    @Column(length = 500)
    private String picture;

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
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getAllergene() {
        return allergene;
    }
    public void setAllergene(String allergene) {
        this.allergene = allergene;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Menue getMenue() {
        return menue;
    }
    public void setMenue(Menue menue) {
        this.menue = menue;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString() {
        return "Meal [id=" + id + ", name=" + name + ", price=" + price + ", allergene=" + allergene + ", description="
                + description + ", menue=" + menue + ", picture=" + picture + "]";
    }
}
