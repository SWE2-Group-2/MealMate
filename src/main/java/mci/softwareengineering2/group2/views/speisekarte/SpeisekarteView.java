package mci.softwareengineering2.group2.views.speisekarte;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;

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

    private OrderedList imageContainer;
    private OrderedList imageContainer1;
    private MealService mealService;

    public SpeisekarteView(MealService mealService) {

        this.mealService = mealService;

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
        mainLayout.add(layout,layout1);
        add(mainLayout);
    }

    private void constructUI() {

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        layout.add(imageContainer);
        mainLayout.add(layout);


        HorizontalLayout layout1 = new HorizontalLayout();
        layout1.setPadding(true);
        layout1.setAlignItems(FlexComponent.Alignment.STRETCH);
        imageContainer1 = new OrderedList();
        imageContainer1.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        layout1.add(imageContainer1);
        mainLayout.add(layout1);

        add(mainLayout);
    }
}
