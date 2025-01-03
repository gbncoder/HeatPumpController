package pl.stwstudio.gbn.heatpumpcontroller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Value("${password}")
    private String password;

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        /*dataSourceBuilder.url("jdbc:mysql://127.0.0.1:3306/heatpump_database");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver"); */

        dataSourceBuilder.url("jdbc:mysql://h26.seohost.pl/srv50719_pc_driver_gbn");
        dataSourceBuilder.username("srv50719_pc_driver_gbn");
        dataSourceBuilder.password("l92UQvVXLznmrRlYq");
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");

        return dataSourceBuilder.build();
    }

    @Bean 
    public JdbcTemplate getJdbcTemplate (){

        return new JdbcTemplate(getDataSource());
    }

}
