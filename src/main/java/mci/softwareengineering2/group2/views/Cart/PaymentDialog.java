package mci.softwareengineering2.group2.views.Cart;

import java.util.Date;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import mci.softwareengineering2.group2.data.Address;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Order;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.services.UserService;

public class PaymentDialog extends Dialog{
    
    private TextField country;
    private TextField state;
    private TextField city;
    private TextField zipCode;
    private TextField street;
    private Button save;
    private Checkbox checkbox;

    public PaymentDialog(User currentUser,List<Meal> mealList,OrderService orderService,UserService userService,boolean isMobile){

        this.setWidth(isMobile ? "100%" : "80%");
        this.setHeight(isMobile ? "100%" : "75%");
        
        VerticalLayout mainLayout = new VerticalLayout();

        setHeaderTitle("Bestellung abschicken");

        HorizontalLayout countryStateLayout = new HorizontalLayout();
        countryStateLayout.setWidth("100%");

        country = new TextField();
        country.setLabel("Land");
        country.setWidth("50%");
        country.addKeyDownListener(event -> {
            save.setEnabled(isAllFilled());
        });
        countryStateLayout.add(country);

        state = new TextField();
        state.setLabel("Bundesland");
        state.setWidth("50%");
        state.addKeyDownListener(event -> {
            save.setEnabled(isAllFilled());
        });
        countryStateLayout.add(state);
        mainLayout.add(countryStateLayout);

        HorizontalLayout cityLayout = new HorizontalLayout();
        cityLayout.setWidth("100%");

        city = new TextField();
        city.setLabel("Stadt");
        city.setWidth("100%");
        city.addKeyDownListener(event -> {
            save.setEnabled(isAllFilled());
        });
        cityLayout.add(city);
        mainLayout.add(cityLayout);

        HorizontalLayout zipCodeStreet = new HorizontalLayout();
        zipCodeStreet.setWidth("100%");

        zipCode = new TextField();
        zipCode.setLabel("Postleitzahl");
        zipCode.setWidth("50%");
        zipCode.addKeyDownListener(event -> {
            save.setEnabled(isAllFilled());
        });
        zipCodeStreet.add(zipCode);

        street = new TextField();
        street.setLabel("Straße");
        street.setWidth("50%");
        street.addKeyDownListener(event -> {
            save.setEnabled(isAllFilled());
        });
        zipCodeStreet.add(street);
        mainLayout.add(zipCodeStreet);

        checkbox = new Checkbox();
        checkbox.setLabel("Ich akzeptiere die Lieferbedingungen.");
        checkbox.addClickListener(event ->{
            save.setEnabled(isAllFilled());
        });
        mainLayout.add(checkbox);

        save = new Button();
        save.setText("Abschicken");
        save.setEnabled(isAllFilled());
        mainLayout.add(save);

        save.addClickListener(event ->{

            Order newOrder = new Order();
            newOrder.setStartDate(new Date());
            newOrder.setUser(currentUser);
            newOrder.setMeals(mealList);
            orderService.update(newOrder);

            currentUser.setAddress(getAddress());

            Notification notification = Notification
            .show("Bestellung erfolgreich erstellt");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            close();
        });

        add(mainLayout);
        fillData(currentUser);
    }

    private Address getAddress() {

        Address address = new Address();

        address.setCountry(country.getValue() == null ? "" : country.getValue());
        address.setState(state.getValue() == null ? "" : state.getValue());
        address.setCity(city.getValue() == null ? "" : city.getValue());
        address.setPostalCode(zipCode.getValue() == null ? "" : zipCode.getValue());
        address.setStreet(street.getValue() == null ? "" : street.getValue());

        return address;
    }

    private void fillData(User currentUser) {

        Address address = currentUser.getAddress();
        if (address != null) {
            country.setValue(address.getCountry() == null ? "" : address.getCountry());
            state.setValue(address.getState() == null ? "" : address.getState());
            city.setValue(address.getCity() == null ? "" : address.getCity());
            zipCode.setValue(address.getPostalCode() == null ? "" : address.getPostalCode());
            street.setValue(address.getStreet() == null ? "" : address.getStreet());
        }
    }

    private boolean isAllFilled(){
        boolean isCountryFilled = country.getValue() != null && country.getValue().length() > 0;
        boolean isStateFilled = state.getValue() != null && state.getValue().length() > 0;
        boolean isCityFilled = city.getValue() != null && city.getValue().length() > 0;
        boolean isZipCodeFilled = zipCode.getValue() != null && zipCode.getValue().length() > 0;
        boolean isStreetFilled = street.getValue() != null && street.getValue().length() > 0;
        boolean isAGBChecked = checkbox.getValue();

        return isCountryFilled && isStateFilled && isCityFilled && isZipCodeFilled && isStreetFilled && isAGBChecked;
    }

}
