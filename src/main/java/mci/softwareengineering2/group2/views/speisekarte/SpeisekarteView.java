package mci.softwareengineering2.group2.views.speisekarte;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;

import jakarta.annotation.security.PermitAll;
import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.CartService;
import mci.softwareengineering2.group2.services.MealService;
import mci.softwareengineering2.group2.services.CategoryService;
import mci.softwareengineering2.group2.views.MainLayout;
import mci.softwareengineering2.group2.datarepository.MealSpecifications;
import mci.softwareengineering2.group2.datarepository.CategorySpecifications;

@PageTitle("Speisekarte")
@Route(value = "speisekarte", layout = MainLayout.class)
@RouteAlias(value = "speisekarte", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class SpeisekarteView extends HorizontalLayout {

    private Cart userCart = null;
    private CartDialog cartDialog = null;

    public SpeisekarteView(MealService mealService,CartService cartService, AuthenticatedUser currentUser) {

        //Todo add the tabs like teh design shows it 
        Tabs tabs = new Tabs();
        tabs.setWidth("100%");
        setTabsCategories(mealService, tabs);
        VerticalLayout tabsLayout = new VerticalLayout();

        //Page<Category> allCategories = categoryService.list(Pageable.unpaged());
        //List<Category> categoryList = allCategories.get().toList();
        Page<Meal> allMeals = mealService.list(Pageable.unpaged(), Specification.where(MealSpecifications.isDeleted(false)));
        List<Meal> mealList = allMeals.get().toList();
        Page<Cart> carts = cartService.list(Pageable.unpaged());

        for (Cart cart : carts) {
            if(cart.getUser().equals(currentUser.get().get())){
                userCart = cart;
                break;
            }
        }

        if(userCart == null){
            userCart = new Cart();
            userCart.setUser(currentUser.get().get());
        }

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        VerticalLayout layout1 = new VerticalLayout();

        int i = 0;
        for (Meal meal : mealList) {

            if (i % 2 == 0) {
                layout.add(new SpeisekarteComponent(meal,userCart,currentUser, mealService));
            } else {
                layout1.add(new SpeisekarteComponent(meal,userCart,currentUser, mealService));
            }
            i++;
        }
        mainLayout.add(layout, layout1);

        HorizontalLayout cartLayout = new HorizontalLayout();
        if (!currentUser.get().get().getRoles().contains(Role.ADMIN)) {
            Button cart = new Button(VaadinIcon.CART_O.create());
            cart.setHeight("50px");
            cart.setText("Warenkorb");
            cartLayout.add(cart);

            cartDialog = new CartDialog();
            cart.addClickListener(event -> {
                cartDialog.generateCart(userCart);
                cartDialog.open();
            });
        }

        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab != null) {
                String tabName = selectedTab.getLabel();
                mainLayout.removeAll(); // Clear existing content

                // Add content based on selected tab name
                if (tabName.equals("Alle")) {
                    mainLayout.add(layout, layout1);
                    return;
                }
                
                Category category = mealService.listCategory(Pageable.unpaged(), Specification.where(CategorySpecifications.isName(tabName))).stream().findFirst().get();
                List<Meal> meals = category.getMeals();
                VerticalLayout categoryLayout = new VerticalLayout();
                VerticalLayout categoryLayout1 = new VerticalLayout();
                int j = 0;
                for (Meal meal : meals) {
                    if (!meal.getDeleted()) {
                        if (j % 2 == 0) {
                            categoryLayout.add(new SpeisekarteComponent(meal,userCart,currentUser, mealService));
                        } else {
                            categoryLayout1.add(new SpeisekarteComponent(meal,userCart,currentUser, mealService));
                        }
                        j++;
                    }
                }
                mainLayout.add(categoryLayout, categoryLayout1);
            }
        });

        tabsLayout.add(tabs,mainLayout);
        add(tabsLayout);
    }

    // Tabs
    private void setTabsCategories(MealService categoryService, Tabs tabs) {
        Page<Category> allCategories = categoryService.listCategory(Pageable.unpaged());
        List<Category> CategoryList = allCategories.get().toList();
        tabs.add(new Tab("Alle"));
        for (Category category : CategoryList) {
            tabs.add(new Tab(category.getName()));
        }
    }

}