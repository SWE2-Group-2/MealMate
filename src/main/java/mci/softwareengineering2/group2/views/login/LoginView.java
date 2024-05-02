package mci.softwareengineering2.group2.views.login;

import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.UserService;
import mci.softwareengineering2.group2.views.accountmanagement.AccounterstellenDialog;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    private AccounterstellenDialog signUpDialog;

    public LoginView(AuthenticatedUser authenticatedUser,UserService userService) {

        this.authenticatedUser = authenticatedUser;

        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        signUpDialog = new AccounterstellenDialog(userService);

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("MealMate");
        i18n.getHeader().setDescription("Please enter credentials");
        i18n.setAdditionalInformation(null);
        
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setForgotPassword("Sign in");
        addForgotPasswordListener(event -> {
            // getUI().ifPresent(ui -> ui.navigate(AccounterstellenView.class));
            signUpDialog.open();
        });
        i18n.setForm(i18nForm);

        setI18n(i18n);

        setForgotPasswordButtonVisible(true);
        setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticatedUser.get().isPresent()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
