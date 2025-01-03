package pl.stwstudio.gbn.heatpumpcontroller;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.concurrent.atomic.AtomicBoolean;

@PermitAll
@Route(value = "" , layout = AppLayoutMain.class)
public class ControlPanel extends VerticalLayout {
    private ControlPanelDAO controlPanelDAO;

    public ControlPanel (ControlPanelDAO controlPanelDAO){

        this.controlPanelDAO =controlPanelDAO;
        AtomicBoolean sendCommand = new AtomicBoolean(false);
        Text text = new Text("Control Panel ");
        Text text1 = new Text("Thermostat       :");
        Text text2 = new Text("HP Temperature   :");
        Text text3 = new Text("Water Pump Speed :");

        Button buttonChangeParam = new Button("Set Parameters");
        buttonChangeParam.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setValue(0);

        Button buttonOFF = new Button("OFF");
        buttonOFF.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        Button buttonON = new Button("ON");
        buttonON.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        NumberField thermostat = new NumberField();
        //thermostat.setValue(22.0);
        thermostat.setStepButtonsVisible(true);
        thermostat.setMin(18);
        thermostat.setMax(26);
        thermostat.setStep(0.5);

        IntegerField hpTemp = new IntegerField();
        //hpTemp.setValue(21);
        hpTemp.setStepButtonsVisible(true);
        hpTemp.setMin(17);
        hpTemp.setMax(30);

        IntegerField waterPumpSpeed = new IntegerField();
        //waterPumpSpeed.setValue(1);
        waterPumpSpeed.setStepButtonsVisible(true);
        waterPumpSpeed.setMin(1);
        waterPumpSpeed.setMax(4);

        controlPanelDAO.updateParameters(thermostat, hpTemp, waterPumpSpeed);

        buttonChangeParam.addClickListener(x -> {
                    sendCommand.set(true);
                    controlPanelDAO.changeParameters(thermostat.getValue(), hpTemp.getValue(), waterPumpSpeed.getValue());
                    Notification notification = Notification.show("Parameters changed!");
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                });
        buttonOFF.addClickListener(x->{
                    controlPanelDAO.changeStatus(0);
                    Notification notification = Notification.show("Heat Pump OFF!");
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                });
        buttonON.addClickListener(x-> {
                    controlPanelDAO.changeStatus(1);
                    Notification notification = Notification.show("Heat Pump ON!");
                    notification.setPosition(Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                });

        HorizontalLayout horizontalLayout = new HorizontalLayout(buttonON, buttonOFF);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(text1,thermostat);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(text2,hpTemp);
        HorizontalLayout horizontalLayout3 = new HorizontalLayout(text3,waterPumpSpeed);

        add(horizontalLayout1 , horizontalLayout2,horizontalLayout3);
        add(buttonChangeParam);
        add(horizontalLayout);
    }
}
