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
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import jakarta.annotation.security.PermitAll;
import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.CartService;
import mci.softwareengineering2.group2.services.MealService;
import mci.softwareengineering2.group2.services.OrderService;
import mci.softwareengineering2.group2.services.UserService;
import mci.softwareengineering2.group2.views.MainLayout;
import mci.softwareengineering2.group2.views.Cart.CartDialog;
import mci.softwareengineering2.group2.datarepository.MealSpecifications;
import mci.softwareengineering2.group2.datarepository.CategorySpecifications;

@PageTitle("Speisekarte")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class SpeisekarteView extends HorizontalLayout {

    private Cart userCart = null;
    private CartDialog cartDialog = null;
    private OrderService orderService = null;
    private UserService userService;
    private boolean isMobile = isMobileDevice();

    public SpeisekarteView(MealService mealService, CartService cartService, AuthenticatedUser currentUser,
            OrderService orderService,UserService userService) {

        this.orderService = orderService;
        this.userService = userService;

        HorizontalLayout mealViewLayout = new HorizontalLayout();
        mealViewLayout.setSizeFull();

        //Todo add the tabs like teh design shows it 
        Tabs tabs = new Tabs();
        tabs.setWidth("100%");
        setTabsCategories(mealService, tabs);
        VerticalLayout tabsLayout = new VerticalLayout();
        tabsLayout.setWidth("100%");
        tabsLayout.setHeight("100%");
        mealViewLayout.add(tabsLayout);

        VerticalLayout layout = new VerticalLayout();

        if (currentUser.get().get().getRoles().contains(Role.USER)) {
            Button cartButton = new Button(VaadinIcon.CART_O.create());
            cartButton.setText("Warenkorb");

            if(isMobile){
                layout.add(cartButton);
            }else{
                mealViewLayout.add(cartButton);
            }

            initializeCartDialog(cartService,currentUser.get().get(),cartButton);
        }

        //Page<Category> allCategories = categoryService.list(Pageable.unpaged());
        //List<Category> categoryList = allCategories.get().toList();
        Page<Meal> allMeals = mealService.list(Pageable.unpaged(), Specification.where(MealSpecifications.isDeleted(false)));
        List<Meal> mealList = allMeals.get().toList();
        
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setWidth("100%");
        mainLayout.setHeight("100%");
        
        layout.setWidth(isMobile ? "100%" : "50%");
        VerticalLayout layout1 = new VerticalLayout();
        layout1.setWidth("50%");

        int i = 0;
        for (Meal meal : mealList) {

            SpeisekarteComponent component = new SpeisekarteComponent(meal,userCart,currentUser, mealService);
            component.setWidth("100%");
            component.setHeight("25%");

            if (isMobile) {
                layout.add(component);
            } else {
                if (i % 2 == 0) {
                    layout.add(component);
                } else {
                    layout1.add(component);
                }
            }
            i++;
        }
        mainLayout.add(layout, layout1);

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
                categoryLayout.setWidth(isMobile ? "100%" : "50%");
                categoryLayout.setHeight("100%");
                VerticalLayout categoryLayout1 = new VerticalLayout();
                categoryLayout1.setWidth("50%");
                categoryLayout1.setHeight("100%");
                int j = 0;
                for (Meal meal : meals) {
                    if (!meal.getDeleted()) {

                        SpeisekarteComponent component = new SpeisekarteComponent(meal,userCart,currentUser, mealService);
                        component.setWidth("100%");
                        component.setHeight("20%");

                        if (isMobile) {
                            categoryLayout.add(component);
                        } else {

                            if (j % 2 == 0) {
                                categoryLayout.add(component);
                            } else {
                                categoryLayout1.add(component);
                            }
                        }
                        j++;
                    }
                }
                mainLayout.add(categoryLayout, categoryLayout1);
            }
        });

        tabsLayout.add(tabs,mainLayout);
        add(mealViewLayout);
    }

    private void initializeCartDialog(CartService cartService,User currentUser,Button cartButton) {

        Page<Cart> carts = cartService.list(Pageable.unpaged());

        for (Cart cart : carts) {
            if(cart.getUser().equals(currentUser)){
                userCart = cart;
                break;
            }
        }

        if(userCart == null){
            userCart = new Cart();
            userCart.setUser(currentUser);
        }

        cartDialog = new CartDialog(orderService,currentUser,userService,isMobile);

        cartButton.addClickListener(event -> {
            cartDialog.generateCart(userCart);
            cartDialog.open();
        });
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

    public  boolean isMobileDevice() {
        WebBrowser webBrowser = VaadinSession.getCurrent().getBrowser();
        return webBrowser.isAndroid() || webBrowser.isIPhone() || webBrowser.isWindowsPhone();
    }
    

}