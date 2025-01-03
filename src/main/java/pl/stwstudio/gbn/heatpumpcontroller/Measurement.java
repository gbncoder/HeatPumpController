package pl.stwstudio.gbn.heatpumpcontroller;

import java.time.LocalDateTime;

public class Measurement {
    private int idMeasurement;
    private double power;
    private LocalDateTime localDateTime ;

    public Measurement(int idMeasurement, double power , LocalDateTime localDateTime) {
        this.idMeasurement = idMeasurement;
        this.localDateTime = localDateTime;
        this.power = power ;
    }
    public int getIdMeasurement() {
        return idMeasurement;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

}
