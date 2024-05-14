package mci.softwareengineering2.group2.views;

import mci.softwareengineering2.group2.data.User;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.AddressService;
import mci.softwareengineering2.group2.services.UserService;
import mci.softwareengineering2.group2.views.accountmanagement.AccountManipulationDialog;
import mci.softwareengineering2.group2.views.bestellungen.BestellungenView;
import mci.softwareengineering2.group2.views.dashboard.DashboardView;
import mci.softwareengineering2.group2.views.speisekarte.SpeisekarteView;
import mci.softwareengineering2.group2.views.supplier.SupplyView;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
@PermitAll
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;
    private UserService userService;
    private AddressService addressService;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker,UserService userService,AddressService addressService) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        this.userService = userService;
        this.addressService = addressService;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("MealMate");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (authenticatedUser.get().get().getRoles().contains(mci.softwareengineering2.group2.data.Role.SUPPLIER)) {
            if (accessChecker.hasAccess(SupplyView.class)) {
                nav.addItem(new SideNavItem("Lieferungen", SupplyView.class,
                        LineAwesomeIcon.UTENSIL_SPOON_SOLID.create()));

                        
            }
        } else {

            if (accessChecker.hasAccess(SpeisekarteView.class)) {
                nav.addItem(new SideNavItem("Speisekarte", SpeisekarteView.class,
                        LineAwesomeIcon.UTENSIL_SPOON_SOLID.create()));
            }

            if (accessChecker.hasAccess(BestellungenView.class)) {
                nav.addItem(
                        new SideNavItem("Bestellungen", BestellungenView.class, LineAwesomeIcon.FILTER_SOLID.create()));
            }

            if (accessChecker.hasAccess(DashboardView.class)) {
                nav.addItem(new SideNavItem("Report", DashboardView.class,
                        LineAwesomeIcon.CHART_LINE_SOLID.create()));

            }
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getFirstName());
            if (user.getProfilePicture() != null) {
                avatar.setImage(user.getProfilePicture() );
                avatar.setThemeName("xsmall");
                avatar.getElement().setAttribute("tabindex", "-1");
            }
            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getFirstName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });
            userName.getSubMenu().addItem("Edit",e ->{
                AccountManipulationDialog dialog = new AccountManipulationDialog(userService, authenticatedUser,addressService);
                dialog.open();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}