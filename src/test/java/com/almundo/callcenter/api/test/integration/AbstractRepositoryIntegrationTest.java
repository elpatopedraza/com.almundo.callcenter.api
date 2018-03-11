package com.almundo.callcenter.api.test.integration;

import com.almundo.callcenter.api.config.DataSourceConfiguration;
import com.almundo.callcenter.api.test.config.BasicRepositoryTestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class that defines all the configuration needed to run integration tests for spring JPA repositories.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        BasicRepositoryTestConfiguration.class,
        DataSourceConfiguration.class
})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractRepositoryIntegrationTest
{

}
