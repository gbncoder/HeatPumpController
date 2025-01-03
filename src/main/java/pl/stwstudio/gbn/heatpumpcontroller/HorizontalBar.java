package pl.stwstudio.gbn.heatpumpcontroller;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

public class HorizontalBar extends ApexChartsBuilder {
    public HorizontalBar () {
    withChart(ChartBuilder.get()
                .withType(Type.BAR)
                .build())
            .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(true)
                                .build())
            .build())
            .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
            .withSeries(new Series<>(400.0, 430.0, 448.0, 470.0, 540.0, 580.0, 690.0, 1100.0, 1200.0, 1380.0))
            .withXaxis(XAxisBuilder.get()
                        .withCategories()
                        .build());
}

}
