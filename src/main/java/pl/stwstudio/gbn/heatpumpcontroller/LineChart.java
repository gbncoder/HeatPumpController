package pl.stwstudio.gbn.heatpumpcontroller;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.xaxis.TickPlacement;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;

@PermitAll
@Route(value = "Life Charts", layout = AppLayoutMain.class)

public class LineChart extends VerticalLayout {
    private LineChartDAO lineChartDAO ;
    Text text = new Text("Temperatura wody: ");
    Text text02 = new Text("Temperatura zewnetrzna: ");
    ArrayList<Double> listValue1 = new ArrayList<Double>();
    ArrayList<Double> listValue2 = new ArrayList<Double>();
    ArrayList<Double> listValue4 = new ArrayList<Double>();
    ArrayList<String> timeList = new ArrayList<String>();


    public LineChart(LineChartDAO lineChartDAO) {
        this.lineChartDAO = lineChartDAO ;
        listValue1 = lineChartDAO.getData24h("value1" , 1);
        listValue2 = lineChartDAO.getData24h("value2" , 1);
        listValue4= lineChartDAO.getData24h("value4", 1);
        timeList = lineChartDAO.getTime24h("reading_time");

        ApexCharts chart = ApexChartsBuilder.get().withChart(ChartBuilder.get()
                        .withType(Type.LINE)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .build())
                        .withStroke(StrokeBuilder.get()
                            .withCurve(Curve.STEPLINE)
                                .build())
                        .withGrid(GridBuilder.get()
                            .withRow(RowBuilder.get()
                                .withColors("#f3f3f3", "transparent")
                                .withOpacity(0.5).build())
                                .build())
                .withForecastDataPoints(ForecastDataPointsBuilder.get().withCount(2).build())
                .withSeries(new Series<>(listValue1.toArray()),
                            new Series<>(listValue2.toArray()))
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.DATETIME)
                        .withCategories(new ArrayList<>(timeList))
                        .withTickPlacement(TickPlacement.BETWEEN)
                        .build())
                .build();
        chart.setHeight("300px");

        ApexCharts chart02 = ApexChartsBuilder.get().withChart(ChartBuilder.get()
                        .withType(Type.LINE)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .build())
                .withStroke(StrokeBuilder.get()
                        .withCurve(Curve.SMOOTH)
                        .build())
                .withGrid(GridBuilder.get()
                        .withRow(RowBuilder.get()
                                .withColors("#f3f3f3", "transparent")
                                .withOpacity(0.5).build())
                        .build())
                .withForecastDataPoints(ForecastDataPointsBuilder.get().withCount(2).build())
                .withSeries(new Series<>(listValue4.toArray()))
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.DATETIME)
                        .withCategories(new ArrayList<>(timeList))
                        .build())
                .build();
        chart02.setHeight("300px");

        Button update = new Button("Następny dzień >>", buttonClickEvent -> {
            listValue4= lineChartDAO.getData24h("value4" , 2);
            Series<Object> series = new Series<>();
            series.setData(listValue4.toArray());
            series.getData();
            chart.updateSeries(series);
            //Notification.show("The chart was updated!");
        });
        Button update02 = new Button("<< Poprzedni dzień", buttonClickEvent -> {
            Series<Double> series = new Series<>();
            Double [] table = {8.8 , 7.7 , 8.6 , 51.0, 49.0, 62.0, 69.0, 91.0, 148.0};
            series.setData(table);
            series.getData();
            chart.updateSeries(series);
            //Notification.show("The chart was updated!");
        });

        HorizontalLayout layout = new HorizontalLayout(update02,update);

        add(text);
        add(chart);
        add(text02);
        add(chart02);
        add(layout);
    }

}