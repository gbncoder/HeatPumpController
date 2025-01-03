package pl.stwstudio.gbn.heatpumpcontroller;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value = "Parsing data to database", layout = AppLayoutMain.class)
public class MeasurementGui extends VerticalLayout {
    Button button1;
    Grid<Measurement> measurementGrid = new Grid<>();
    private MeasurementDAO measurementDAO;

@Autowired
    public MeasurementGui(MeasurementDAO measurementDAO){
        this.button1 = new Button("Dodaj zapis");
        this.measurementDAO = measurementDAO;

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        DatePicker singleFormatDatePicker = new DatePicker("Select a date:");
        singleFormatDatePicker.setI18n(singleFormatI18n);

        List <Measurement> powerConsumptionList = new ArrayList<>();
        LocalDate measurementDate = LocalDate.of(2024,12,8);
        powerConsumptionList = measurementDAO.showPowerConsumption24h(measurementDate);
        measurementGrid.setItems(powerConsumptionList);
        measurementGrid.addColumn(Measurement::getIdMeasurement).setHeader("ID");
        measurementGrid.addColumn(Measurement::getLocalDateTime).setHeader("DATE");
        measurementGrid.addColumn(Measurement::getPower).setHeader("POWER");

        button1.addClickListener(x->{
            List <Measurement> powerConsumptionList2 = new ArrayList<>();
            powerConsumptionList2 = measurementDAO.showPowerConsumption24h(singleFormatDatePicker.getValue());
            measurementGrid.setItems(powerConsumptionList2);
            double averagePowerConsumption = 0;
            double sumPowerConsumption = 0;
            for(int i=0 ; i < powerConsumptionList2.size(); i++){
                sumPowerConsumption = sumPowerConsumption + powerConsumptionList2.get(i).getPower();
            }
            averagePowerConsumption = sumPowerConsumption / powerConsumptionList2.size();
            System.out.println(sumPowerConsumption);
            System.out.println(powerConsumptionList2.size());
            System.out.println(averagePowerConsumption);
            System.out.println(sumPowerConsumption/120);
                });

        add(measurementGrid);
        VerticalLayout verticalLayout = new VerticalLayout();
        add( verticalLayout, singleFormatDatePicker,button1);
    }
}
