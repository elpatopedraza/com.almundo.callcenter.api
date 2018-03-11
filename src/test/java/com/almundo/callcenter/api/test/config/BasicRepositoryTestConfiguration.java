package com.almundo.callcenter.api.test.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Class that defines basic configuration for repository test suites.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Configuration
@EntityScan("com.almundo.callcenter.api")
@EnableJpaRepositories(basePackages = {"com.almundo.callcenter.api"})
public class BasicRepositoryTestConfiguration
{

}
