package pl.stwstudio.gbn.heatpumpcontroller;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;

@PermitAll
@Route(value = "PowerConsumption", layout = AppLayoutMain.class)
public class PowerConsumption extends VerticalLayout {
    private int dayCounter = 1 ;
    private LineChartDAO barChartDAO;
    ArrayList <Double> listPowerConsumption = new ArrayList<>();

    public PowerConsumption(LineChartDAO lineChartDAO) {
        this.barChartDAO = lineChartDAO;
        listPowerConsumption = barChartDAO.getData24h("value7",1);

        ApexCharts chart = ApexChartsBuilder.get().withChart(ChartBuilder.get()
                        .withType(Type.BAR)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(false)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withSeries(new Series<>(400.0))
                .withXaxis(XAxisBuilder.get()
                        .withCategories("01-2023")
                        .build())
                .build();
        chart.setHeight("300px");
        Button update = new Button("Następny miesiąc >>", buttonClickEvent -> {
            Series<Double> series = new Series<>();
            Double [] table = {1.0 , 2.0};
            series.setData(table);
            series.getData();
            chart.updateSeries(series);
            //Notification.show("The chart was updated!");
        });
        Button update02 = new Button("<< Poprzedni miesiąc", buttonClickEvent -> {
            listPowerConsumption = barChartDAO.getData24h("value7",dayCounter);

            //double dayPowerConsumption =
            //String sql = "INSERT INTO PowerConsumptionDay VALUES()";
            dayCounter ++ ;
        });
        add(chart);
        HorizontalLayout layout = new HorizontalLayout(update02,update);
        add(layout);
    }

}