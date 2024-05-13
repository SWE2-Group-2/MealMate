package mci.softwareengineering2.group2.views.speisekarte;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Role;
import mci.softwareengineering2.group2.security.AuthenticatedUser;
import mci.softwareengineering2.group2.services.MealService;

public class SpeisekarteComponent extends ListItem {

        private final String defaultString = "https://imgs.search.brave.com/Go2Gk8Q0PVTkq9i3e6Vti9UHhjMx_rdrlzIG6GmzZUg/rs:fit:500:0:0/g:ce/aHR0cHM6Ly90My5m/dGNkbi5uZXQvanBn/LzA0LzYwLzAxLzM2/LzM2MF9GXzQ2MDAx/MzYyMl82eEY4dU42/dWJNdkx4MHRBSkVD/QkhmS1BvTk9SNWNS/YS5qcGc";
        // private final String defaultHeightDiv = "150px";
        // private final String defaultWidthDiv = "650px";
        private SpeisekarteDialog editSpeisenDialog = new SpeisekarteDialog();
        private SpeisekarteDialog addSpeisenDialog = new SpeisekarteDialog();
        private SpeisekarteDialog delSpeisenDialog = new SpeisekarteDialog();
        private int amountValue = 1;
        private Span amount;
        SpeisekarteComponent(Meal meal,Cart cart,AuthenticatedUser currentUser, MealService mealService) {

                //addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START,
                addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START,
                                Padding.MEDIUM,
                                BorderRadius.LARGE);

                Div div = new Div();
                div.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
                div.setHeight("150px");
                div.setWidth("100%");

                Image image = new Image();
                image.setWidth("100%");
                image.setSrc((meal.getPicture() == null || meal.getPicture() != null && meal.getPicture().length() == 0) ? defaultString : meal.getPicture());

                div.add(image);
                
                HorizontalLayout editLayout = new HorizontalLayout();
                editLayout.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                                Margin.Top.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
                // Als Admin wird ein Button zum Löschen und editieren der Speise hinzugefügt
                if (currentUser.get().get().getRoles().contains(Role.ADMIN)) {
                        Button delete = new Button(new Icon(VaadinIcon.FILE_REMOVE));
                        delete.setText("Speise Löschen");
                        delete.addClickListener(event -> {
                                delSpeisenDialog.delSpeisenDialog(meal, mealService);
                                delSpeisenDialog.open();
                        });

                        Button edit = new Button(new Icon(VaadinIcon.EDIT));
                        edit.setText("Speise Bearbeiten");
                        edit.addClickListener(event -> {
                                editSpeisenDialog.editSpeisenDialog(meal, mealService);
                                editSpeisenDialog.open();
                        });

                        Button add = new Button(new Icon(VaadinIcon.FILE_ADD));
                        add.setText("Neue Speise");
                        add.addClickListener(event -> {
                                addSpeisenDialog.addSpeisenDialog(mealService);
                                addSpeisenDialog.open();
                        });
                        editLayout.add(delete, edit, add);
                        editLayout.setWidth("100%");
                        editLayout.setAlignItems(Alignment.STRETCH);
                }
                
                HorizontalLayout headerLayout = new HorizontalLayout();

                Span header = new Span();
                header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
                header.setText(meal.getName());
                headerLayout.add(header);

                Span badge = new Span();
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("" + meal.getPrice() + "€");
                headerLayout.add(badge);

                Span subtitle = new Span();
                subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
                subtitle.setText((meal.getAllergene() == null || meal.getAllergene() != null && meal.getAllergene().length() == 0)  ? "" : meal.getAllergene());

                HorizontalLayout layout = new HorizontalLayout();

                Button decreas = new Button();
                decreas.setText("-");
                decreas.addClickListener(e -> {
                        if(amountValue > 1){
                                amountValue--;
                        }
                        amount.setText(""+amountValue);
                });

                amount = new Span();
                amount.getElement().getStyle().set("font-size", "20px");
                amount.getElement().getStyle().set("margin-top", "5px");
                amount.setText(""+amountValue);
                Button increas = new Button();
                increas.setText("+");
                increas.addClickListener(e -> {
                        if(amountValue < 10){
                                amountValue++;
                        }
                        amount.setText(""+amountValue);
                });

                Button add = new Button();
                add.setText("Hinzufügen");
                add.addClickListener(event -> {
                        for (int i = 0; i < amountValue; i++) {
                                Notification notification = Notification
                                                .show(String.format("%s zum Warenkorb hinzugefügt",meal.getName()));
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                                cart.getMeals().add(meal);
                        }
                });
                if (!currentUser.get().get().getRoles().contains(Role.ADMIN)) {
                        layout.add(decreas);
                        layout.add(amount);
                        layout.add(increas);
                        layout.add(new Span());
                        layout.add(add);
                }

                add(div, headerLayout, subtitle,layout,editLayout);
        }
}
