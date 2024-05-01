package mci.softwareengineering2.group2.views.speisekarte;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import jakarta.annotation.security.PermitAll;

@PageTitle("Speisekarte")
@Route(value = "speisekarte")
@PermitAll
@Uses(Icon.class)
public class SpeisekarteView extends Composite<VerticalLayout> {

    public SpeisekarteView() {
        Tabs tabs = new Tabs();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Paragraph textMedium = new Paragraph();
        Paragraph textSmall = new Paragraph();
        HorizontalLayout layoutRow = new HorizontalLayout();
        NumberField numberField = new NumberField();
        Button buttonSecondary = new Button();
        FormLayout formLayout2Col = new FormLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        tabs.setWidth("100%");
        setTabsSampleData(tabs);
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        textMedium.setText("Schnitzel mit Pommes");
        textMedium.setWidth("100%");
        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textSmall.setText("Dies ist das beste Schnitzel on earth");
        textSmall.setWidth("100%");
        textSmall.getStyle().set("font-size", "var(--lumo-font-size-xs)");
        layoutRow.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.START);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        numberField.setLabel("Menge");
        layoutRow.setAlignSelf(FlexComponent.Alignment.END, numberField);
        numberField.setWidth("100px");
        buttonSecondary.setText("Hinzufügen");
        layoutRow.setAlignSelf(FlexComponent.Alignment.END, buttonSecondary);
        buttonSecondary.setWidth("min-content");
        formLayout2Col.setWidth("100%");
        getContent().add(tabs);
        getContent().add(layoutColumn2);
        layoutColumn2.add(textMedium);
        layoutColumn2.add(textSmall);
        layoutColumn2.add(layoutRow);
        layoutRow.add(numberField);
        layoutRow.add(buttonSecondary);
        getContent().add(formLayout2Col);
    }

    private void setTabsSampleData(Tabs tabs) {
        tabs.add(new Tab("Dashboard"));
        tabs.add(new Tab("Payment"));
        tabs.add(new Tab("Shipping"));
    }
}
