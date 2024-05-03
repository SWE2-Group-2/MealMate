package mci.softwareengineering2.group2.data;

import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant_table")
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
    
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Address getAddress() {
        return address;
    }
    public int getRating() {
        return rating;
    }
    public String getTags() {
        return tags;
    }
    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", rating=" + rating + ", tags="
                + tags + "]";
    }
}
