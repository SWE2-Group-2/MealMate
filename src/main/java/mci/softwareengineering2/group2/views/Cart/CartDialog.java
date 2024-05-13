package mci.softwareengineering2.group2.views.Cart;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.services.UserService;

public class CartDialog extends Dialog {

    private List<Meal> mealList = new ArrayList<>();
    private List<Meal> selectedItems = new ArrayList<Meal>();
    private OrderService orderService;
    private User currentUser;
    private Grid<Meal> grid;
    private Dialog currentDialog;
    private UserService userService;
    private boolean isMobile;

    public CartDialog(OrderService orderService,User currentUser,UserService userService,boolean isMobile) {
        this.orderService = orderService;
        this.currentUser = currentUser;
        this.userService = userService;
        currentDialog = this;
        this.isMobile = isMobile;
    }


    public void generateCart(Cart cart) {

        mealList = cart.getMeals();
        this.removeAll();

        H2 users = new H2("Warenkorb");
        users.getStyle().set("margin", "0 auto 0 0");

        HorizontalLayout header = new HorizontalLayout(users);
        header.setAlignItems(Alignment.CENTER);
        header.getThemeList().clear();

        Button create = new Button("Bestellung anschicken");
        create.setEnabled(true);
        create.addClickListener(event -> {

            PaymentDialog paymentDialog = new PaymentDialog(currentUser, mealList, orderService, userService, isMobile);
            paymentDialog.open();
            cart.setMeals(new ArrayList<Meal>());
            currentDialog.close();
        });

        Button delete = new Button("Delete");
        delete.setEnabled(false);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.getStyle().set("margin-inline-start", "auto");
        delete.addClickListener(event -> {

            for (Meal meal : selectedItems) {
                mealList.remove(meal);
            }

            grid.setItems(mealList);

            List<FooterRow> footerRows = grid.getFooterRows();
            for (FooterRow row : footerRows) {
                row.getCells().get(1).setText(getSum(mealList));
            }

        });

        grid = new Grid<>(Meal.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Meal::getName).setHeader("Gericht").setWidth("20%");
        grid.addColumn(Meal::getPrice).setHeader("Preis [€]");
        grid.addSelectionListener(selection -> {
            int size = selection.getAllSelectedItems().size();
            delete.setEnabled(size != 0);

            selectedItems = selection.getAllSelectedItems().stream().toList();
        });

        HorizontalLayout footer = new HorizontalLayout(create,delete);
        footer.getStyle().set("flex-wrap", "wrap");

        grid.setItems(mealList);
        List<FooterCell> footerCells = grid.appendFooterRow().getCells();
        boolean isFirst = true;
        for (FooterCell cell : footerCells) {
            if(isFirst){
                cell.setText("Summe:");
                isFirst = false;
            }else{
                cell.setText(getSum(mealList));
            }
        }
        add(header, grid, footer);
        this.setWidth(isMobile ? "100%" : "80%");
        this.setHeight(isMobile ? "100%" : "75%");
    }


    private String getSum(List<Meal> mealList) {
        float sum = 0;
        
        for (Meal meal : mealList) {
            sum = sum + meal.getPrice();
        }

        return ""+sum;
    
    }

}