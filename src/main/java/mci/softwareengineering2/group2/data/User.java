package mci.softwareengineering2.group2.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.template.Id;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user_table")
public class User extends AbstractEntity {

    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    @Column(length = 1000)
    private String profilePicture;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;
    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

    /**
     * Get the username of an user
     * @return the username or null
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set a new username for a user
     * @param username teh new username of an user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the hashed password of an user
     * @return the hashed password for an user
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Set a hashed password for an user
     * @param hashedPassword the new hashed password of an user
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Get the roles of an user 
     * @return 1-n roles a user has
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Set the roles for an user
     * @param roles set the roles of the user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Get the first name of an user 
     * @return teh first name or null
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of an user
     * @param firstName the new first name of an user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of an user
     * @return the last name of an user or null
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the new last name for an user
     * @param lastName the new last name for an user 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the mail of an user
     * @return the mail or null
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the new mail for an user
     * @param email the mail of an user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the phone number of an user
     * @return the phone number or null
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone number for a user
     * @param phone the phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the birth date of an user
     * @return the birth date or null
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Set the birth date of an user
     * @param dateOfBirth the birth date of an user
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Get the profession of an user
     * @return the profession or null
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Set the profession of an user
     * @param occupation the profession of an user
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Get the value of an user is important
     * @return true or false if an user is important
     */
    public boolean isImportant() {
        return important;
    }

    /**
     * Set the value if an user is important
     * @param important id a user important
     */
    public void setImportant(boolean important) {
        this.important = important;
    }

    /**
     * get the address of an user
     * @return teh address of an user or nill
     */
    public Address getAddress() {
        return address;
    }
    
    /**
     * Set the address of an user
     * @param address the address of an user
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Get the id of an user
     * @return the id of an user
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the new id of an user
     * @param id the new id of an user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the image Url of the picture of an user
     * @return the image url or null
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Set the image url of an user
     * @param profilePicture the image url 
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", hashedPassword=" + hashedPassword + ", roles=" + roles
                + ", profilePicture=" + profilePicture + ", firstName=" + firstName + ", lastName=" + lastName
                + ", email=" + email + ", phone=" + phone + ", dateOfBirth=" + dateOfBirth + ", occupation="
                + occupation + ", important=" + important + ", address=" + address + "]";
    }
}