package mci.softwareengineering2.group2.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import mci.softwareengineering2.group2.views.MainLayout;
import mci.softwareengineering2.group2.views.dashboard.ServiceHealth.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("Report")
@Route(value = "report", layout = MainLayout.class)
@RouteAlias(value = "report", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class DashboardView extends Main {

    public DashboardView() {
        addClassName("dashboard-view");

        Board board = new Board();
        board.addRow(createHighlight("Aktive Kunden", "2.378", 21.30), createHighlight("Bestellungen*", "1.110", 10.75),
                createHighlight("Umsatz*", "€ 11.098,76", 3.9));
        String explanationText = "   * Anzeige bezieht sich auf die aktuelle Periode. Änderungen in % beziehen sich auf die Vorperiode.";
        Span explanationSpan = new Span(explanationText);
        explanationSpan.getStyle().set("font-size", "small");
        board.addRow(createViewEvents());
        board.addRow(createAnzahlEssenverkauft());
        add(board);
        add(explanationSpan);
    }

    private Component createHighlight(String title, String value, Double percentage) {
        VaadinIcon icon = VaadinIcon.ARROW_UP;
        String prefix = "";
        String theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        H2 h2 = new H2(title);
        h2.addClassNames(FontWeight.NORMAL, Margin.NONE, TextColor.SECONDARY, FontSize.XSMALL);

        Span span = new Span(value);
        span.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);

        Icon i = icon.create();
        i.addClassNames(BoxSizing.BORDER, Padding.XSMALL);

        Span badge = new Span(i, new Span(prefix + percentage.toString()));
        badge.getElement().getThemeList().add(theme);

        VerticalLayout layout = new VerticalLayout(h2, span, badge);
        layout.addClassName(Padding.LARGE);
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Component createViewEvents() {
        // Header
        Select<String> yearSelect = new Select<>();
        yearSelect.setItems("2022", "2023", "2024");
        yearSelect.setValue("2024");
        yearSelect.setWidth("100px");

        HorizontalLayout header = createHeader("Umsatz", "Jahresverlauf");
        header.add(yearSelect);

        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration config = chart.getConfiguration();
        config.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez");
        config.addxAxis(xAxis);

        config.getyAxis().setTitle("Umsatz in €");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        plotOptions.setConnectNulls(true); // Hier wird die Verbindung der Nullwerte aktiviert
        config.addPlotOptions(plotOptions);

        ListSeries series2022 = new ListSeries("Umsatz/Monat 2022", 2001.23, 7503.67, 8205.89, 8907.45, 9509.11, 10011.79, 10513.31, 11015.47, 11517.89, 8532.65, 9123.78, 10456.21);
        ListSeries series2023 = new ListSeries("Umsatz/Monat 2023", 1372.25, 7989.43, 9376.89, 9812.34, 10543.22, 8876.55, 5327.56, 9234.67, 9765.89, 10567.32, 8923.45, 10876.90);
        ListSeries series2024 = new ListSeries("Umsatz/Monat 2024", 2378.23, 8176.34, 9543.21, 10123.67, (11098.76 / 2));

        // Füge die Serien zur Konfiguration hinzu
        config.setSeries(series2022, series2023, series2024);

        // Füge die Serien zur Liste hinzu
        List<ListSeries> seriesList = Arrays.asList(series2022, series2023, series2024);

        yearSelect.addValueChangeListener(event -> {
            String selectedYear = event.getValue();

            // Loop über alle Serien
            for (ListSeries series : seriesList) {
                if (series.getName().contains(selectedYear)) {
                    // Wenn das Jahr ausgewählt wurde, ändere die Sichtbarkeit der Serie
                    series.setVisible(!series.isVisible());
                }
            }

            // Update legend visibility
            config.getLegend().setEnabled(seriesList.stream().anyMatch(ListSeries::isVisible));

            // Redraw the chart
            chart.drawChart();
        });

        // Add it all together
        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName(Padding.LARGE);
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
    }


    private Component createAnzahlEssenverkauft() {
        HorizontalLayout header = createHeader("Bestellungen", "Prozentuelle Verteilung");

        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        chart.setThemeName("gradient");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Grillhendl+Kartoffelsalat", 33.7));
        series.add(new DataSeriesItem("Wiener Schnitzel", 27.72));
        series.add(new DataSeriesItem("Pizza Diavolo", 7.33));
        series.add(new DataSeriesItem("Pizza Americano", 17.35));
        series.add(new DataSeriesItem("Insalata Verde", 13.9));
        conf.addSeries(series);

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, chart);
        serviceHealth.addClassName(Padding.LARGE);
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames(FontSize.XLARGE, Margin.NONE);

        Span span = new Span(subtitle);
        span.addClassNames(TextColor.SECONDARY, FontSize.XSMALL);

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

}
