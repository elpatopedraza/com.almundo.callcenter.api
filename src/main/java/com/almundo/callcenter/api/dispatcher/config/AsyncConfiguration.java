package com.almundo.callcenter.api.dispatcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Class that defines all the configuration settings needed to handle spring asynchronous operations.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Configuration
@EnableAsync
public class AsyncConfiguration
{
    /**
     * The thread name prefix used to identify threads handled by an Executor.
     */
    private final static String THREAD_NAME_PREFIX = "Callcenterapi-";

    @Value("${callcenter.api.dispatcher.async.maxConcurrentCalls}")
    private int maxConcurrentCalls;

    /**
     * Creates an Executor bean.
     * It is used to handle spring thread pools for asynchronous operations.
     *
     * @return an Executor bean.
     */
    @Bean
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(maxConcurrentCalls);
        executor.setMaxPoolSize(maxConcurrentCalls);
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();

        return executor;
    }
}
