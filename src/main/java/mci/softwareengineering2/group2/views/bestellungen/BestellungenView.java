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
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.views.MainLayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Bestellungen")
@Route(value = "bestellungen", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class BestellungenView extends Div {

    private Grid<Order> currentOrdersGrid;
    private Grid<Order> completedOrdersGrid;
    private final OrderService orderService;

    public BestellungenView(OrderService orderService) {
        this.orderService = orderService;
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

       /* currentOrdersGrid = new Grid<>(Order.class, false);
        currentOrdersGrid.addColumn(Order::getId).setHeader("Bestellnummer").setAutoWidth(true);
        currentOrdersGrid.addColumn(order -> order.getStartDate() != null ? order.getStartDate().toString() : "").setHeader("Datum").setAutoWidth(true);
        currentOrdersGrid.addColumn(order -> {
            Map<Meal, Integer> mealCounts = new HashMap<>();
            for (Meal meal : order.getMeals()) {
                mealCounts.merge(meal, 1, Integer::sum);
            }
            return mealCounts.entrySet().stream()
                    .map(entry -> entry.getKey().getName() + " x" + entry.getValue())
                    .collect(Collectors.joining(", "));
        }).setHeader("Speisen/Getränke").setAutoWidth(true);
        currentOrdersGrid.addColumn(order -> order.getMeals().stream()
                        .mapToDouble(meal -> meal.getPrice())
                        .sum())
                .setHeader("Gesamtpreis").setAutoWidth(true);

        currentOrdersGrid.addColumn(order -> order.getState().toString()).setHeader("Status").setAutoWidth(true);

        currentOrdersGrid.addColumn(new ComponentRenderer<>(order -> {

                Button editButton = new Button(new Icon(VaadinIcon.EDIT));
                editButton.addClickListener(e -> showEditDialog(order));
                return new HorizontalLayout(editButton);

        })).setHeader("Aktion").setAutoWidth(true);



        DataProvider<Order, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    PageRequest pageRequest = PageRequest.of(query.getPage(), query.getPageSize());
                    return orderService.list(pageRequest).stream();
                },
                query -> orderService.count()
        );
        currentOrdersGrid.setDataProvider(dataProvider);*/
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

        grid.addColumn(new ComponentRenderer<>(order -> {

            Button editButton = new Button(new Icon(VaadinIcon.EDIT));
            editButton.addClickListener(e -> showEditDialog(order));
            return new HorizontalLayout(editButton);

        })).setHeader("Aktion").setAutoWidth(true);

        DataProvider<Order, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    Page<Order> orderPage = orderService.list(PageRequest.of(query.getPage(), query.getPageSize()));
                    if (!orderPage.hasContent()) {
                        return Stream.empty();
                    }

                    return orderPage.getContent().stream().filter(
                            completed ? order -> order.getState() == OrderState.ORDER_DONE :
                                    order -> order.getState() != OrderState.ORDER_DONE
                    );
                },
                query -> {

                    return (int) orderService.list(PageRequest.of(0, Integer.MAX_VALUE))
                            .getContent()
                            .stream()
                            .filter(completed ? order -> order.getState() == OrderState.ORDER_DONE :
                                    order -> order.getState() != OrderState.ORDER_DONE)
                            .count();
                }
        );
        grid.setDataProvider(dataProvider);

        return grid;
    }

    private void showEditDialog(Order order) {
        Dialog dialog = new Dialog();
        ComboBox<OrderState> stateComboBox = new ComboBox<>("Status ändern", OrderState.values());
        stateComboBox.setValue(order.getState());
        Button saveButton = new Button("Speichern", e -> {
            order.setState(stateComboBox.getValue());
            orderService.update(order);
            dialog.close();
            currentOrdersGrid.getDataProvider().refreshItem(order);
        });
        dialog.add(stateComboBox, saveButton);
        dialog.open();
    }
}
