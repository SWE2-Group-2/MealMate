package mci.softwareengineering2.group2.views.speisekarte;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import jakarta.annotation.security.PermitAll;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.services.MealService;
import mci.softwareengineering2.group2.views.MainLayout;

@PageTitle("Speisekarte")
@Route(value = "speisekarte", layout = MainLayout.class)
@RouteAlias(value = "speisekarte", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class SpeisekarteView extends HorizontalLayout {


    public SpeisekarteView(MealService mealService) {

        //Todo add the tabs like teh design shows it 

        Page<Meal> allMeals = mealService.list(Pageable.unpaged());
        List<Meal> mealList = allMeals.get().toList();


        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        VerticalLayout layout1 = new VerticalLayout();

        int i = 0;
        for (Meal meal : mealList) {

            if (i % 2 == 0) {
                layout.add(new SpeisekarteComponent(meal.getName(),meal.getDescription(),meal.getAllergene(),meal.getPrice(),meal.getPicture()));
            } else {
                layout1.add(new SpeisekarteComponent(meal.getName(),meal.getDescription(),meal.getAllergene(),meal.getPrice(),meal.getPicture()));
            }
            i++;
        }

        HorizontalLayout cartLayout = new HorizontalLayout();
        Button cart = new Button(VaadinIcon.CART_O.create());
        cart.setHeight("50px");
        cart.setText("Warenkorb");
        cartLayout.add(cart);

        mainLayout.add(layout,layout1,cartLayout);
        add(mainLayout);
    }
}