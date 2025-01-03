package pl.stwstudio.gbn.heatpumpcontroller;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ControlPanelDAO {
  private JdbcTemplate jdbcTemplate;

  @Autowired
    public ControlPanelDAO(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public void changeParameters (double thermostat, int hpTemperature, int waterPumpSpeed){
        String sql = "UPDATE Parameters SET thermostat = (?), hpTemperature = (?) , waterPumpSpeed = (?) , MSG_STATUS = 15 WHERE id = 1" ;
        jdbcTemplate.update(sql,
                thermostat,
                hpTemperature,
                waterPumpSpeed);
    }
    public void updateParameters (NumberField thermostat, IntegerField hpTemp, IntegerField waterPumpSpeed){
    thermostat.setValue(jdbcTemplate.queryForObject("SELECT thermostat FROM Parameters ", Double.class ));
    hpTemp.setValue(jdbcTemplate.queryForObject("SELECT hpTemperature FROM Parameters", Integer.class));
    waterPumpSpeed.setValue(jdbcTemplate.queryForObject("SELECT waterPumpSpeed FROM Parameters", Integer.class));
    }
    public void changeStatus (int hpStatus) {
      String sql = "UPDATE Parameters SET hpStatus = (?) , MSG_STATUS = 14 WHERE id = 1" ;
      jdbcTemplate.update(sql,
              hpStatus);
    }

}
