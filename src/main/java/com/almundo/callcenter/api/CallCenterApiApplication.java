package com.almundo.callcenter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot starting point class.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@SpringBootApplication
public class CallCenterApiApplication
{
    /**
     * Runs the call center api platform in an embedded application server.
     *
     * @param args the arguments needed to start the application.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(CallCenterApiApplication.class, args);
    }
}
