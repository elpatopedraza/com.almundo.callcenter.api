package com.almundo.callcenter.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Class that defines all configuration related to system data sources.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceConfiguration extends HikariConfig
{
    /**
     * Creates a data source bean for H2 database main connection.
     *
     * @return a datas ource bean that contains all the H2 database main connection properties.
     */
    @Bean
    public DataSource dataSource()
    {
        return new HikariDataSource(this);
    }
}
