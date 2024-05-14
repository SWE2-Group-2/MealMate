package mci.softwareengineering2.group2.views.supplier;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import mci.softwareengineering2.group2.data.Order;
import mci.softwareengineering2.group2.data.OrderState;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.views.MainLayout;

@PageTitle("Auslieferungen")
@Route(value = "supply", layout = MainLayout.class)
@RolesAllowed("SUPPLIER")
@Uses(Icon.class)
public class SupplyView extends VerticalLayout {

    private OrderService orderService;
    private Grid<Order> openDeliverGrid;
    private Grid<Order> myDeliverGrid;
    private User currentUser;
    private List<Order> openDeliveries;
    private List<Order> myDeliveries;
    private Button deliveredOrder;
    private Button claimOrder;

    public SupplyView(OrderService orderService, AuthenticatedUser authUser) {

        this.orderService = orderService;
        this.currentUser = authUser.get().get();

        generateUI();
        updateContent();

    }

    private void updateContent() {
        openDeliveries = orderService.list(Pageable.unpaged())
                .filter(order -> order.getState() == OrderState.ORDER_BEING_DELIVERED && order.getSupplierId() == 0)
                .toList();
        myDeliveries = orderService.list(Pageable.unpaged())
                .filter(order -> order.getState() == OrderState.ORDER_BEING_DELIVERED
                        && order.getSupplierId() == currentUser.getId())
                .toList();

        openDeliverGrid.setItems(openDeliveries);
        myDeliverGrid.setItems(myDeliveries);

    }

    private void generateUI() {
        H2 firstSubtitle = new H2("Offene Lieferungen");

        this.setWidth("100%");
        this.setHeight("100%");

        VerticalLayout openLayout = new VerticalLayout();
        openLayout.setHeight("45%");

        openDeliverGrid = new Grid<>(Order.class, false);
        openDeliverGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        openDeliverGrid.addColumn(Order::getId).setHeader("Id").setWidth("100px");
        openDeliverGrid.addColumn(Order::getMealString).setHeader("Gerichte").setWidth("30%");
        openDeliverGrid.addColumn(Order::getOrderSum).setHeader("Preis [€]");
        openDeliverGrid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();
            claimOrder.setEnabled(size != 0);
        });

        claimOrder = new Button();
        claimOrder.setText("Selektierte Bestellungen übernehmen");
        claimOrder.setEnabled(false);
        claimOrder.addClickListener(event -> {
            List<Order> selectedOrders = openDeliverGrid.getSelectedItems().stream().toList();

            for (Order selectedOrder : selectedOrders) {
                selectedOrder.setSupplier(currentUser);
                orderService.update(selectedOrder);

                Notification notification = Notification
                        .show(String.format("BestellungNr %s übernommen", selectedOrder.getId()));
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

            updateContent();

        });

        openLayout.add(firstSubtitle, openDeliverGrid, claimOrder);

        VerticalLayout myLayout = new VerticalLayout();
        myLayout.setHeight("45%");

        H2 secondSubtitle = new H2("Meine Lieferungen");

        myDeliverGrid = new Grid<>(Order.class, false);
        myDeliverGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        myDeliverGrid.addColumn(Order::getId).setHeader("Id").setWidth("100px");
        myDeliverGrid.addColumn(Order::getMealString).setHeader("Gerichte").setWidth("30%");
        myDeliverGrid.addColumn(Order::getOrderSum).setHeader("Preis [€]");
        myDeliverGrid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();
            deliveredOrder.setEnabled(size != 0);
        });

        deliveredOrder = new Button();
        deliveredOrder.setText("Bestellungen ausgeliefert");
        deliveredOrder.setEnabled(false);
        deliveredOrder.addClickListener(event -> {
            List<Order> selectedOrders = myDeliverGrid.getSelectedItems().stream().toList();

            for (Order selectedOrder : selectedOrders) {
                selectedOrder.setState(OrderState.ORDER_DONE);
                orderService.update(selectedOrder);

                Notification notification = Notification
                        .show(String.format("BestellungNr %s ausgeliefert", selectedOrder.getId()));
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

            updateContent();

        });

        myLayout.add(secondSubtitle, myDeliverGrid, deliveredOrder);

        add(openLayout, myLayout);
    }

}
