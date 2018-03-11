package com.almundo.callcenter.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class that defines basic configuration for the system.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Configuration
@ComponentScan(basePackages = "com.almundo.callcenter.api")
@EntityScan("com.almundo.callcenter.api")
@EnableJpaRepositories(basePackages = {"com.almundo.callcenter.api"})
public class BasicConfiguration
{

}
