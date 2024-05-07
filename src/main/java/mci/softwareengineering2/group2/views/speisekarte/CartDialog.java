package mci.softwareengineering2.group2.views.speisekarte;

import java.util.ArrayList;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;

public class CartDialog extends Dialog {

    private ArrayList<Meal> mealList = new ArrayList<>();

    public void generateCart(Cart cart) {

        VerticalLayout layout = new VerticalLayout();
        mealList.clear();
        this.removeAll();

        if (cart != null && cart.getMeals() != null) {

            float cartAmount = 0;

            for (Meal meal : cart.getMeals()) {

                if (!mealList.contains(meal)) {
                    HorizontalLayout horizontalLayout = new HorizontalLayout();
                    layout.add(horizontalLayout);
                    int mealAmount = cart.getMealAmount(meal);

                    Span mealName = new Span();
                    mealName.setText(meal.getName());
                    horizontalLayout.add(mealName);

                    Span price = new Span();
                    price.setText("" + meal.getPrice());
                    horizontalLayout.add(price);
                    
                    if (mealAmount > 1) {
                        Span mealAmountSpan = new Span();
                        mealAmountSpan.setText("" + mealAmount);
                        horizontalLayout.add(mealAmountSpan);
                    }

                    Button deleteButton = new Button();
                    deleteButton.setText("Delete");
                    horizontalLayout.add(deleteButton);

                    mealList.add(meal);
                }
                cartAmount = cartAmount + meal.getPrice();
            }

            Span cartprice = new Span();
            cartprice.setText("" + cartAmount);
            layout.add(cartprice);

        }
        this.add(layout);
    }

}
