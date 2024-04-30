package mci.softwareengineering2.group2.views.accounterstellen;

import mci.softwareengineering2.group2.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
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

@PageTitle("Account erstellen")
@Route(value = "person-form")
@AnonymousAllowed
@Uses(Icon.class)
public class AccounterstellenView extends Composite<VerticalLayout> {

    public AccounterstellenView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        FormLayout formLayout2Col = new FormLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        EmailField emailField = new EmailField();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField2 = new PasswordField();
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button buttonSecondary = new Button();
        Button buttonPrimary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        formLayout2Col.setWidth("100%");
        textField.setLabel("Vorname");
        textField2.setLabel("Nachname");
        emailField.setLabel("E-Mail Adresse");
        layoutColumn3.setHeightFull();
        //formLayout2Col.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        passwordField.setLabel("Passwort");
        passwordField.setWidth("min-content");
        passwordField2.setLabel("Passwort bestätigen");
        passwordField2.setWidth("min-content");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.END);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        buttonSecondary.setText("Schließen");
        buttonSecondary.setWidth("min-content");
        buttonPrimary.setText("Account erstellen");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutColumn2);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(textField);
        formLayout2Col.add(textField2);
        formLayout2Col.add(emailField);
        formLayout2Col.add(layoutColumn3);
        formLayout2Col.add(passwordField);
        formLayout2Col.add(passwordField2);
        layoutColumn2.add(layoutRow);
        layoutRow.add(buttonSecondary);
        layoutRow.add(buttonPrimary);
    }
}
