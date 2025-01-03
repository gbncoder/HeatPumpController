package pl.stwstudio.gbn.heatpumpcontroller;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.stwstudio.gbn.heatpumpcontroller.security.SecurityService;

import javax.annotation.security.PermitAll;


@PermitAll
@Route("App Layout")

public class AppLayoutMain extends AppLayout {
    private final SecurityService securityService;

    public AppLayoutMain (SecurityService securityService){
        this.securityService = securityService;

        H1 title = new H1("HeatPump Controller");
        title.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        String u = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Log out: " + u , e ->securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        Tabs tabs = getTabs();
        addToDrawer(tabs);
        addToNavbar(header);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.add(createTab(VaadinIcon.DASHBOARD, "Control Panel", ControlPanel.class),
                createTab(VaadinIcon.LIST, "Parameters", MeasurementGui.class),
                createTab(VaadinIcon.CHART, "Life Charts", LineChart.class),
                createTab(VaadinIcon.BAR_CHART_H, "Power Consumption", PowerConsumption.class),
                createTab(VaadinIcon.FACTORY, "How it's made?", PowerConsumption.class),
                createTab(VaadinIcon.COGS, "HARDWARE", PowerConsumption.class),
                createTab(VaadinIcon.USER_HEART, "My Account", PowerConsumption.class));

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private Tab createTab(VaadinIcon viewIcon, String viewName , Class viewClass) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setRoute(viewClass);
        link.setTabIndex(-1);
        return new Tab(link);
    }

}
