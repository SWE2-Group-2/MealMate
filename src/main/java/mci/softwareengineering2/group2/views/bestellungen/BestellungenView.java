package mci.softwareengineering2.group2.views.bestellungen;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Order;
import mci.softwareengineering2.group2.data.OrderState;
import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.views.MainLayout;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Bestellungen")
@Route(value = "bestellungen", layout = MainLayout.class)
@RolesAllowed({"ADMIN","USER"})
@Uses(Icon.class)
public class BestellungenView extends Div {

    private Grid<Order> currentOrdersGrid;
    private Grid<Order> completedOrdersGrid;
    private final OrderService orderService;
    private AuthenticatedUser currentUser;

    public BestellungenView(OrderService orderService, AuthenticatedUser currentUser) {
        this.orderService = orderService;
        this.currentUser = currentUser;
        setSizeFull();
        addClassNames("bestellungen-view");
        setupGrid();

        Accordion accordion = new Accordion();

        VerticalLayout currentOrdersLayout = new VerticalLayout(currentOrdersGrid);
        accordion.add("Aktuelle Bestellungen", currentOrdersLayout);

        VerticalLayout completedOrdersLayout = new VerticalLayout(completedOrdersGrid);
        accordion.add("Abgeschlossene Bestellungen", completedOrdersLayout);

        add(accordion);
    }
    private void setupGrid() {

        currentOrdersGrid = createOrderGrid(false);
        completedOrdersGrid = createOrderGrid(true);

    }

    private Grid<Order> createOrderGrid(boolean completed) {
        Grid<Order> grid = new Grid<>(Order.class, false);
        grid.addColumn(Order::getId).setHeader("Bestellnummer").setAutoWidth(true);
        grid.addColumn(order -> order.getStartDate() != null ? order.getStartDate().toString() : "").setHeader("Datum").setAutoWidth(true);
        grid.addColumn(order -> {
            Map<Meal, Integer> mealCounts = new HashMap<>();
            for (Meal meal : order.getMeals()) {
                mealCounts.merge(meal, 1, Integer::sum);
            }
            return mealCounts.entrySet().stream()
                    .map(entry -> entry.getKey().getName() + " x " + entry.getValue())
                    .collect(Collectors.joining(", "));
        }).setHeader("Speisen/Getränke").setAutoWidth(true);
        grid.addColumn(order -> order.getMeals().stream()
                        .mapToDouble(meal -> meal.getPrice())
                        .sum())
                .setHeader("Gesamtpreis").setAutoWidth(true);
        grid.addColumn(order -> order.getState().toString()).setHeader("Status").setAutoWidth(true);

        if (currentUser.get().isPresent() && currentUser.get().get().getRoles().contains(Role.ADMIN)){
            grid.addColumn(new ComponentRenderer<>(order -> {

                Button editButton = new Button(new Icon(VaadinIcon.EDIT));
                editButton.addClickListener(e -> showEditDialog(order));
                return new HorizontalLayout(editButton);

            })).setHeader("Aktion").setAutoWidth(true);
        }

        DataProvider<Order, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    query.getOffset();
                    query.getLimit();
                    Stream<Order> ordersStream = orderService.list(PageRequest.of(0, Integer.MAX_VALUE))
                            .getContent().stream();
                    if (currentUser.get().isPresent() && !currentUser.get().get().getRoles().contains(Role.ADMIN)) {
                        ordersStream = ordersStream.filter(order -> order.getUser().equals(currentUser.get().get()));
                    }
                    return ordersStream.filter(completed ? order -> order.getState() == OrderState.ORDER_DONE :
                            order -> order.getState() != OrderState.ORDER_DONE);
                },
                query -> {
                    query.getOffset();
                    query.getLimit();
                    Stream<Order> ordersStream = orderService.list(PageRequest.of(0, Integer.MAX_VALUE))
                            .getContent().stream();
                    if (currentUser.get().isPresent() && !currentUser.get().get().getRoles().contains(Role.ADMIN)) {
                        ordersStream = ordersStream.filter(order -> order.getUser().equals(currentUser.get().get()));
                    }
                    return (int) ordersStream.filter(completed ? order -> order.getState() == OrderState.ORDER_DONE :
                            order -> order.getState() != OrderState.ORDER_DONE).count();
                }
        );
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private void showEditDialog(Order order) {
        Dialog dialog = new Dialog();
        dialog.setWidth("40%");

        ArrayList<OrderState> availableStates  = new ArrayList<OrderState>();
        for(OrderState orderstate : OrderState.values()){
            if(orderstate != OrderState.ORDER_DONE){
                availableStates.add(orderstate);
            }
        }

        ComboBox<OrderState> stateComboBox = new ComboBox<>("Status ändern", availableStates);
        stateComboBox.setWidth("80%");
        stateComboBox.setValue(order.getState());
        Button saveButton = new Button("Speichern", e -> {
            OrderState oldState = order.getState();
            OrderState newState = stateComboBox.getValue();
            order.setState(newState);
            orderService.update(order);
            dialog.close();
            if (oldState != newState && (newState == OrderState.ORDER_DONE || oldState == OrderState.ORDER_DONE)) {
                refreshGrids();
            } else {
                currentOrdersGrid.getDataProvider().refreshItem(order);
            }
        });
        dialog.add(stateComboBox, saveButton);
        dialog.open();
    }

    private void refreshGrids() {
        currentOrdersGrid.getDataProvider().refreshAll();
        completedOrdersGrid.getDataProvider().refreshAll();
    }
}
