package pl.stwstudio.gbn.heatpumpcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LineChartDAO {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public LineChartDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList <Double>  getData24h(String value , int day){
        ArrayList<Double> list = new ArrayList<Double>();
        String sql = "SELECT " + value + " FROM SensorData WHERE reading_time >= NOW() - INTERVAL " + day + " DAY";
        List<Double> doubleList = new ArrayList<>();
        doubleList = jdbcTemplate.queryForList(sql, Double.class);
        doubleList.forEach(id->list.add(id));
        return list;
    }

    public ArrayList <String>  getTime24h(String value){
        ArrayList<String> list = new ArrayList<String>();
        String sql = "SELECT " + value + " FROM SensorData WHERE reading_time >= NOW() - INTERVAL 1 DAY";
        List<String> doubleList = new ArrayList<>();
        doubleList = jdbcTemplate.queryForList(sql, String.class);
        doubleList.forEach(id->list.add(id));
        return list;
    }
}
