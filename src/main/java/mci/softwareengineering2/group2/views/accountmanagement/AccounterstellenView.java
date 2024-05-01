package mci.softwareengineering2.group2.views.accountmanagement;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.services.UserService;
import mci.softwareengineering2.group2.views.login.LoginView;

@PageTitle("Account erstellen")
@Route(value = "person-form")
@AnonymousAllowed
@Uses(Icon.class)
public class AccounterstellenView extends Composite<VerticalLayout> {

    private UserService userService;
    private TextField firstName;
    private TextField lastname;
    private TextField username;
    private EmailField emailField;
    private PasswordField passwordField;
    private PasswordField passwordField2nd;
    private Button buttonPrimary;

    public AccounterstellenView(UserService userService) {
        
        this.userService = userService;

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);

        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        getContent().add(layoutColumn2);

        FormLayout formLayout2Col = new FormLayout();
        formLayout2Col.setWidth("100%");

        firstName = new TextField();
        firstName.setLabel("Vorname");
        formLayout2Col.add(firstName);

        lastname = new TextField();
        lastname.setLabel("Nachname");
        formLayout2Col.add(lastname);

        username = new TextField();
        username.setLabel("Username");
        username.setRequired(true);
        formLayout2Col.add(username);

        emailField = new EmailField();
        emailField.setLabel("E-Mail Adresse");
        formLayout2Col.add(emailField);

        passwordField = new PasswordField();
        passwordField.setLabel("Passwort");
        passwordField.setRequired(true);
        formLayout2Col.add(passwordField);

        passwordField2nd = new PasswordField();
        passwordField2nd.setLabel("Passwort bestätigen");
        passwordField2nd.setRequired(true);
        formLayout2Col.add(passwordField2nd);

        layoutColumn2.add(formLayout2Col);

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
        
        // Button buttonSecondary = new Button();
        // buttonSecondary.setText("Schließen");
        // buttonSecondary.setWidth("min-content");
        // layoutRow.add(buttonSecondary);

        setListeners();
    }

    private void setListeners() {

        buttonPrimary.addClickListener(event -> {
            if (save()){
                Notification notification = Notification
                .show("User created successfully!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                getUI().ifPresent(ui -> ui.navigate(LoginView.class));

            }else{
                Notification notification = Notification
                .show("User could not be created!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
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
            user.setHashedPassword(new BCryptPasswordEncoder().encode(passwordField.getValue()));
            Set<Role> set = new HashSet<Role> (); 
            set.add(Role.USER);
            user.setRoles(set);

            userService.update(user);

            return true;
        }

        return false;
    }
}
