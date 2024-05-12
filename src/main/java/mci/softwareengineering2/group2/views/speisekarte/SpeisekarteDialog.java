package mci.softwareengineering2.group2.views.speisekarte;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionModel.Multi;
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

import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.services.CategoryService;
import mci.softwareengineering2.group2.services.MealService;

public class SpeisekarteDialog extends Dialog {
    private final String defaultHeightDiv = "150px";
    private final String defaultWidthDiv = "650px";
    private TextField speisenName;
    private TextField speisenPreis;
    private TextField speisenAllergene;
    private TextField speisenBild;
    
    public void editSpeisenDialog(Meal meal, MealService mealService) {
        this.removeAll();
        
        if (meal != null) {
            Div div = new Div();
            div.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                            Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
            div.setHeight(defaultHeightDiv);
            div.setWidth(defaultWidthDiv);
            div.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
            div.setText("Speise editieren");

            HorizontalLayout headerLayout = new HorizontalLayout();
            
            Span subtitle = new Span();
            subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
            subtitle.setText("Bitte ändern Sie die Felder");

            HorizontalLayout layout = new HorizontalLayout();
            HorizontalLayout layout1 = new HorizontalLayout();
            HorizontalLayout layout2 = new HorizontalLayout();
            HorizontalLayout layout3 = new HorizontalLayout();

            layout.setWidthFull();
            layout1.setWidthFull();
            layout2.setWidthFull();
            layout3.setWidthFull();

            speisenName = new TextField();
            speisenName.setLabel("Name für Speise");
            speisenName.setWidth("50%");
            speisenName.setRequired(true);
            speisenName.setRequiredIndicatorVisible(true);
            speisenName.setValue(meal.getName());                
            layout.add(speisenName);

            speisenPreis = new TextField();
            speisenPreis.setLabel("Preis in €");
            speisenPreis.setWidth("50%");
            speisenPreis.setRequired(true);
            speisenPreis.setRequiredIndicatorVisible(true);
            speisenPreis.setValue(String.valueOf(meal.getPrice())); 
            layout.add(speisenPreis);
            
            layout.add(new Span());

            speisenAllergene = new TextField();
            speisenAllergene.setLabel("Allergene");
            speisenAllergene.setWidth("98%");
            speisenAllergene.setValue(meal.getAllergene()); 
            layout1.add(speisenAllergene);

            speisenBild = new TextField();
            speisenBild.setLabel("Bild als URL");
            speisenBild.setWidth("98%");
            speisenBild.setValue(meal.getPicture());
            layout2.add(speisenBild);
            
            // add a listbox with all categories to choose
            HorizontalLayout layoutListBox = new HorizontalLayout();
            MultiSelectListBox<String> categoryMultiSelectList = new MultiSelectListBox<>();
            Page<Category> allCategories = mealService.listCategory(Pageable.unpaged());
            List<Category> categoryList = allCategories.get().toList();
            List<String> listCategories = new ArrayList<String>();
            List<String> listSelectedCategories = new ArrayList<String>();
            
            // GET ERROR HERE
            List<String> categoryListSelected = mealService.getMealCategoryNames(meal.getId());
            for (String category : categoryListSelected) {
                listSelectedCategories.add(category);
            }

            for (Category category : categoryList) {
                listCategories.add(category.getName());
            }
            categoryMultiSelectList.setItems(listCategories);
            categoryMultiSelectList.select(listSelectedCategories);
            layoutListBox.add(categoryMultiSelectList);
            layoutListBox.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.START, JustifyContent.START,
            Margin.Bottom.MEDIUM, Margin.Top.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);

            Button add = new Button();
            add.setText("Ändern");
            add.setWidth("45%");
            add.addClickListener(event -> {
                boolean valid = true;
                if (speisenName.getValue().isEmpty()) {
                    speisenName.setInvalid(true);
                    speisenName.setErrorMessage("Bitte geben Sie einen Namen ein");
                    valid = false;
                }
                if (speisenPreis.getValue().isEmpty()) {
                    speisenPreis.setInvalid(true);
                    speisenPreis.setErrorMessage("Bitte geben Sie einen Preis ein");
                    valid = false;
                }
                if (valid) {
                    meal.setName(speisenName.getValue());
                    meal.setPrice(Float.parseFloat(speisenPreis.getValue()));
                    meal.setAllergene(speisenAllergene.getValue());
                    meal.setPicture(speisenBild.getValue()); 

                    // set categories
                    Page<Category> allPossibleCategories = mealService.listCategory(Pageable.unpaged());
                    List<Category> allCategoryList = allPossibleCategories.get().toList();
                    List<Long> newCategoryList = new ArrayList<Long>();
                    Set<String> selectedValues = categoryMultiSelectList.getValue();
                    for (Category category : allCategoryList) {
                        if (selectedValues.contains(category.getName())) {
                            newCategoryList.add(category.getId());
                        }
                    }
                    // Das geht bei Category nicht, er speichert es nicht in die Datenbank
                    //meal.setCategory(newCategoryList);
                    mealService.updateMealCategories(meal.getId(), newCategoryList);
                    mealService.update(meal);
                    getUI().ifPresent(ui -> ui.getPage().reload());
                }
            });
            Button cancle = new Button();
            cancle.setText("Abbrechen");
            cancle.setWidth("45%");
            cancle.addClickListener(event -> {
                this.close();
            });

            layout3.add(new Span());

            layout3.add(cancle);
            layout3.add(add);

            add(div, headerLayout, subtitle,layout,layout1,layout2, layoutListBox, layout3);
        }
    }

    public void addSpeisenDialog(MealService mealService) {
        this.removeAll();
        Meal meal = new Meal();

        Div div = new Div();
        div.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                        Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight(defaultHeightDiv);
        div.setWidth(defaultWidthDiv);
        div.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        div.setText("Neue Speise hinzufügen");

        HorizontalLayout headerLayout = new HorizontalLayout();
        
        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("Bitte befüllen Sie die Felder");

        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout layout1 = new HorizontalLayout();
        HorizontalLayout layout2 = new HorizontalLayout();
        HorizontalLayout layout3 = new HorizontalLayout();

        layout.setWidthFull();
        layout1.setWidthFull();
        layout2.setWidthFull();
        layout3.setWidthFull();

        speisenName = new TextField();
        speisenName.setLabel("Name für Speise");
        speisenName.setWidth("50%");
        speisenName.setRequired(true);
        speisenName.setRequiredIndicatorVisible(true);
        layout.add(speisenName);

        speisenPreis = new TextField();
        speisenPreis.setLabel("Preis in €");
        speisenPreis.setWidth("50%");
        speisenPreis.setRequired(true);
        speisenPreis.setRequiredIndicatorVisible(true);
        layout.add(speisenPreis);
        
        layout.add(new Span());

        speisenAllergene = new TextField();
        speisenAllergene.setLabel("Allergene");
        speisenAllergene.setWidth("98%");
        layout1.add(speisenAllergene);

        speisenBild = new TextField();
        speisenBild.setLabel("Bild als URL");
        speisenBild.setWidth("98%");
        layout2.add(speisenBild);
        
        // add a listbox with all categories to choose
        HorizontalLayout layoutListBox = new HorizontalLayout();
        MultiSelectListBox<String> categoryMultiSelectList = new MultiSelectListBox<>();
        Page<Category> allCategories = mealService.listCategory(Pageable.unpaged());
        List<Category> categoryList = allCategories.get().toList();
        List<String> listCategories = new ArrayList<String>();
        List<String> listSelectedCategories = new ArrayList<String>();
        
        for (Category category : categoryList) {
            listCategories.add(category.getName());
        }
        categoryMultiSelectList.setItems(listCategories);
        categoryMultiSelectList.select(listSelectedCategories);
        layoutListBox.add(categoryMultiSelectList);
        layoutListBox.addClassNames(Background.CONTRAST_5, Display.FLEX, AlignItems.START, JustifyContent.START,
        Margin.Bottom.MEDIUM, Margin.Top.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);

        Button add = new Button();
        add.setText("Erstellen");
        add.setWidth("45%");
        add.addClickListener(event -> {
            boolean valid = true;
            if (speisenName.getValue().isEmpty()) {
                speisenName.setInvalid(true);
                speisenName.setErrorMessage("Bitte geben Sie einen Namen ein");
                valid = false;
            }
            if (speisenPreis.getValue().isEmpty()) {
                speisenPreis.setInvalid(true);
                speisenPreis.setErrorMessage("Bitte geben Sie einen Preis ein");
                valid = false;
            }
            if (valid) {
                meal.setName(speisenName.getValue());
                meal.setPrice(Float.parseFloat(speisenPreis.getValue()));
                meal.setAllergene(speisenAllergene.getValue());
                meal.setPicture(speisenBild.getValue()); 
                // set categories
                Page<Category> allPossibleCategories = mealService.listCategory(Pageable.unpaged());
                List<Category> allCategoryList = allPossibleCategories.get().toList();
                List<Long> newCategoryList = new ArrayList<Long>();
                Set<String> selectedValues = categoryMultiSelectList.getValue();
                for (Category category : allCategoryList) {
                    if (selectedValues.contains(category.getName())) {
                        newCategoryList.add(category.getId());
                    }
                }
                mealService.create(meal);
                mealService.updateMealCategories(meal.getId(), newCategoryList);
                getUI().ifPresent(ui -> ui.getPage().reload());
            }
        });
        Button cancle = new Button();
        cancle.setText("Abbrechen");
        cancle.setWidth("45%");
        cancle.addClickListener(event -> {
            this.close();
        });

        layout3.add(new Span());

        layout3.add(cancle);
        layout3.add(add);

        add(div, headerLayout, subtitle,layout,layout1,layout2,layoutListBox,layout3);
    }

    public void delSpeisenDialog(Meal meal, MealService mealService) {
        this.removeAll();
        HorizontalLayout headerLayout = new HorizontalLayout();
        
        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.XLARGE, TextColor.SECONDARY);
        subtitle.setText("Sind sie sicher, dass Sie die Speise löschen wollen?");

        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout layout1 = new HorizontalLayout();
        HorizontalLayout layout2 = new HorizontalLayout();
        HorizontalLayout layout3 = new HorizontalLayout();

        layout.setWidthFull();
        layout1.setWidthFull();
        layout2.setWidthFull();
        layout3.setWidthFull();

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(meal.getName());
        layout.add(header);

        Button add = new Button();
        add.setText("Löschen");
        add.setWidth("45%");
        add.addClickListener(event -> {
            meal.setDeleted(true);
            mealService.update(meal);
            getUI().ifPresent(ui -> ui.getPage().reload());
        });
        Button cancle = new Button();
        cancle.setText("Abbrechen");
        cancle.setWidth("45%");
        cancle.addClickListener(event -> {
            this.close();
        });

        layout3.add(new Span());

        layout3.add(cancle);
        layout3.add(add);

        add(headerLayout, subtitle,layout,layout1,layout2,layout3);
    }

}
