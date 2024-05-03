package mci.softwareengineering2.group2.views.speisekarte;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

public class SpeisekarteComponent extends ListItem {

        private final String defaultString = "https://imgs.search.brave.com/Go2Gk8Q0PVTkq9i3e6Vti9UHhjMx_rdrlzIG6GmzZUg/rs:fit:500:0:0/g:ce/aHR0cHM6Ly90My5m/dGNkbi5uZXQvanBn/LzA0LzYwLzAxLzM2/LzM2MF9GXzQ2MDAx/MzYyMl82eEY4dU42/dWJNdkx4MHRBSkVD/QkhmS1BvTk9SNWNS/YS5qcGc";
        private int amountValue = 1;
        private Span amount;
        SpeisekarteComponent(String name, String descriptionText, String allergene,float price,String url) {

                addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START,
                                Padding.MEDIUM,
                                BorderRadius.LARGE);

                Div div = new Div();
                div.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
                div.setHeight("150px");
                div.setWidth("650px");

                Image image = new Image();
                image.setWidth("100%");
                image.setSrc((url == null || url != null && url.length() == 0) ? defaultString : url);
                image.setAlt("text");

                div.add(image);

                HorizontalLayout headerLayout = new HorizontalLayout();

                Span header = new Span();
                header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
                header.setText(name);
                headerLayout.add(header);

                Span badge = new Span();
                badge.getElement().setAttribute("theme", "badge");
                badge.setText("" + price + "€");
                headerLayout.add(badge);

                Span subtitle = new Span();
                subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
                subtitle.setText((allergene == null || allergene != null && allergene.length() == 0)  ? "" : allergene);

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

                layout.add(decreas);
                layout.add(amount);
                layout.add(increas);
                layout.add(new Span());
                layout.add(add);

                add(div, headerLayout, subtitle,layout);
        }

}
