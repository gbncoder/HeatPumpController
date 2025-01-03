package pl.stwstudio.gbn.heatpumpcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class MeasurementDAO {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public MeasurementDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List <Measurement> showPowerConsumption24h(LocalDate date){
        List<Measurement> powerConsumptionList = new ArrayList<Measurement>();
        Measurement measurement = new Measurement(0, 0, LocalDateTime.now() );
        String dateStr = date.toString();
        String sql = "SELECT id, value7, reading_time FROM SensorData WHERE DATE(reading_time) = " + "'" + dateStr + "'" ;
        return jdbcTemplate.query(sql,
                ((rs, rowNum) ->
                        new Measurement(
                                rs.getInt("id"),
                                rs.getDouble("value7"),
                                rs.getTimestamp("reading_time").toLocalDateTime()
                        )
                ));
    }



}
