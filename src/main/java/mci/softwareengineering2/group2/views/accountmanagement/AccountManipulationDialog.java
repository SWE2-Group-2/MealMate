package mci.softwareengineering2.group2.views.accountmanagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import mci.softwareengineering2.group2.data.Address;
import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.AddressService;
import mci.softwareengineering2.group2.services.UserService;

public class AccountManipulationDialog extends Dialog {

    private UserService userService;
    private TextField firstName;
    private TextField lastname;
    private TextField username;
    private EmailField emailField;
    private PasswordField passwordField;
    private PasswordField passwordField2nd;
    private Button buttonPrimary;
    private User currentUser = null;
    private TextField country;
    private TextField state;
    private TextField city;
    private TextField zipCode;
    private TextField street;
    private TextField phone;
    private TextField profilePicture;
    private DatePicker birthDate;
    private PasswordField currentPasswordField;
    private AddressService addressService;

    public AccountManipulationDialog(UserService userService, AuthenticatedUser authenticatedUser,AddressService addressService) {

        this.userService = userService;
        this.addressService = addressService;

        if (authenticatedUser != null) {
            currentUser = authenticatedUser.get().get();
        }

        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setMinWidth("800px");
        layoutColumn2.setHeight("min-content");
        this.add(layoutColumn2);

        Accordion accordion = new Accordion();

        FormLayout customerMainLayout = new FormLayout();
        accordion.add("Anmeldedaten",
                customerMainLayout);
        customerMainLayout.setWidth("100%");

        firstName = new TextField();
        firstName.setLabel("Vorname");
        customerMainLayout.add(firstName);

        lastname = new TextField();
        lastname.setLabel("Nachname");
        customerMainLayout.add(lastname);

        username = new TextField();
        username.setLabel("Username");
        username.setRequired(true);
        customerMainLayout.add(username);

        emailField = new EmailField();
        emailField.setLabel("E-Mail Adresse");
        customerMainLayout.add(emailField);

        createPasswordFields(customerMainLayout,currentUser == null);

        FormLayout customerAddressLayout = new FormLayout();
        accordion.add("Lieferadresse",
                customerAddressLayout);

        customerAddressLayout.setWidth("100%");

        country = new TextField();
        country.setLabel("Land");
        customerAddressLayout.add(country);

        state = new TextField();
        state.setLabel("Bundesland");
        customerAddressLayout.add(state);

        city = new TextField();
        city.setLabel("Stadt");
        customerAddressLayout.add(city);

        zipCode = new TextField();
        zipCode.setLabel("Postleitzahl");
        customerAddressLayout.add(zipCode);

        street = new TextField();
        street.setLabel("Straße");
        customerAddressLayout.add(street);

        FormLayout customerDetailsFormLayout = new FormLayout();
        accordion.add("Persönliche Informationen",
                customerDetailsFormLayout);

        customerDetailsFormLayout.setWidth("100%");

        phone = new TextField();
        phone.setLabel("Telefonnummer");
        customerDetailsFormLayout.add(phone);

        profilePicture = new TextField();
        profilePicture.setLabel("Bild als Url");
        customerDetailsFormLayout.add(profilePicture);

        birthDate = new DatePicker();
        birthDate.setLabel("Geburtsdatum");
        customerDetailsFormLayout.add(birthDate);

        layoutColumn2.add(accordion);

        HorizontalLayout layoutRow = new HorizontalLayout();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.END);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        layoutColumn2.add(layoutRow);

        buttonPrimary = new Button();
        buttonPrimary.setText("Account erstellen");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        layoutRow.add(buttonPrimary);

        setListeners(currentUser == null);
        fillData(currentUser == null);
    }

    private void createPasswordFields(FormLayout customerMainLayout, boolean isUserCreate) {

        if(!isUserCreate){
            currentPasswordField = new PasswordField();
            currentPasswordField.setLabel("Bisheriges Passwort");
            currentPasswordField.setRequired(true);
            customerMainLayout.add(currentPasswordField);
        }

        passwordField = new PasswordField();
        passwordField.setLabel("Passwort");
        passwordField.setRequired(true);
        customerMainLayout.add(passwordField);

        passwordField2nd = new PasswordField();
        passwordField2nd.setLabel("Passwort bestätigen");
        passwordField2nd.setRequired(true);
        customerMainLayout.add(passwordField2nd);
    }

    private void fillData(boolean isUserCreate) {
        if(!isUserCreate){

            firstName.setValue(currentUser.getFirstName() == null ? "" : currentUser.getFirstName());
            lastname.setValue(currentUser.getLastName() == null ? "" : currentUser.getLastName());
            username.setValue(currentUser.getUsername() == null ? "" : currentUser.getUsername());
            emailField.setValue(currentUser.getEmail() == null ? "" : currentUser.getEmail());
            phone.setValue(currentUser.getPhone() == null ? "" : currentUser.getPhone());
            profilePicture.setValue(currentUser.getProfilePicture() == null ? "" : currentUser.getProfilePicture());
            birthDate.setValue(currentUser.getDateOfBirth() == null ? null : currentUser.getDateOfBirth());

            Address address = currentUser.getAddress();
            if (address != null) {
                country.setValue(address.getCountry() == null ? "" : address.getCountry());
                state.setValue(address.getState() == null ? "" : address.getState());
                city.setValue(address.getCity() == null ? "" : address.getCity());
                zipCode.setValue(address.getPostalCode() == null ? "" : address.getPostalCode());
                street.setValue(address.getStreet() == null ? "" : address.getStreet());
            }
        }
    }

    private void setListeners(final boolean isUserCreate) {

        buttonPrimary.addClickListener(event -> {
            if (isUserCreate ? save() : update()) {
                Notification notification = Notification
                        .show(isUserCreate ? "User created successfully!" : "User updated successfully");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                this.close();
            } else {
                Notification notification = Notification
                        .show(isUserCreate ? "User could not be created!" : "User could not be updated!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }

    private boolean update() {

        boolean isUsernameFilled = username.getValue() != null && username.getValue().length() > 0;
        
        if (isUsernameFilled) {

            currentUser.setFirstName(firstName.getValue() == null ? "" : firstName.getValue());
            currentUser.setLastName(lastname.getValue()== null ? "" : lastname.getValue());
            currentUser.setUsername(username.getValue()== null ? "" : username.getValue());
            currentUser.setEmail(emailField.getValue()== null ? "" : emailField.getValue());
            currentUser.setPhone(phone.getValue()== null ? "" : phone.getValue());
            currentUser.setDateOfBirth(birthDate.getValue()== null ? null : birthDate.getValue());
            currentUser.setProfilePicture(profilePicture.getValue()== null ? "" : profilePicture.getValue());
            
            Address address = currentUser.getAddress();

            if(address == null){
                address = new Address();
            }

            address.setCountry(country.getValue() == null ? "" : country.getValue());
            address.setState(state.getValue() == null ? "" : state.getValue());
            address.setCity(city.getValue() == null ? "" : city.getValue());
            address.setPostalCode(zipCode.getValue() == null ? "" : zipCode.getValue());
            address.setStreet(street.getValue() == null ? "" : street.getValue());

            currentUser.setAddress(address);

            boolean isPasswordFilled = passwordField.getValue() != null && passwordField.getValue().length() > 0;
            boolean isPassword2ndFilled = passwordField2nd.getValue() != null
                    && passwordField2nd.getValue().length() > 0;
            boolean isCurrentPasswordFilled = currentPasswordField.getValue() != null && currentPasswordField.getValue().length() > 0;

            boolean isPasswordEqual = passwordField.getValue().equals(passwordField2nd.getValue());
            boolean isCurrentPasswordEqual = new BCryptPasswordEncoder().matches(currentPasswordField.getValue(),currentUser.getHashedPassword());

            if(isPasswordFilled && isPassword2ndFilled && isCurrentPasswordFilled){

                if(isCurrentPasswordEqual && isPasswordEqual){

                    currentUser.setHashedPassword(new BCryptPasswordEncoder().encode(passwordField.getValue()));

                }else{
                    Notification notification = Notification
                        .show("Could not change password!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }


            }
            
            Set<Role> set = new HashSet<Role>();
            set.add(Role.USER);
            currentUser.setRoles(set);

            addressService.update(address);
            userService.update(currentUser);

            return true;
        }

        return false;
    }

    private boolean save() {

        boolean isUsernameFilled = username.getValue() != null && username.getValue().length() > 0;
        boolean isPasswordFilled = passwordField.getValue() != null && passwordField.getValue().length() > 0;
        boolean isPassword2ndFilled = passwordField2nd.getValue() != null && passwordField2nd.getValue().length() > 0;
        boolean isPasswordEqual = passwordField.getValue().equals(passwordField2nd.getValue());

        if (isUsernameFilled && isPasswordFilled && isPassword2ndFilled && isPasswordEqual) {

            User user = new User();

            user.setFirstName(firstName.getValue());
            user.setLastName(lastname.getValue());
            user.setUsername(username.getValue());
            user.setEmail(emailField.getValue());
            user.setPhone(phone.getValue()== null ? "" : phone.getValue());
            user.setDateOfBirth(birthDate.getValue()== null ? null : birthDate.getValue());
            user.setProfilePicture(profilePicture.getValue()== null ? "" : profilePicture.getValue());
            user.setHashedPassword(new BCryptPasswordEncoder().encode(passwordField.getValue()));
            Set<Role> set = new HashSet<Role>();
            set.add(Role.USER);
            user.setRoles(set);

            Address address = user.getAddress();

            if(address == null){
                address = new Address();
            }

            address.setCountry(country.getValue() == null ? "" : country.getValue());
            address.setState(state.getValue() == null ? "" : state.getValue());
            address.setCity(city.getValue() == null ? "" : city.getValue());
            address.setPostalCode(zipCode.getValue() == null ? "" : zipCode.getValue());
            address.setStreet(street.getValue() == null ? "" : street.getValue());

            user.setAddress(address);

            addressService.update(address);
            userService.update(user);

            return true;
        }

        return false;
    }
}