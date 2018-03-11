package com.almundo.callcenter.api.test.integration;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class that defines all the configuration needed to run integration tests for spring controllers.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerIntegrationTest
{

}
