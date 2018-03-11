package com.almundo.callcenter.api.dispatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Class that defines the configuration settings needed to connect to an ActiveMQ queue engine.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Configuration
@EnableJms
public class ActiveMQConfiguration
{
    /**
     * The ActiveMQ call queue name.
     */
    public static final String CALL_QUEUE = "call-queue";

    /**
     * The message converter type id property name.
     */
    private static final String TYPE_ID_PROPERTY_NAME = "_type";

    /**
     * Creates a JMS listener container factory bean.
     * It allows to send and receive messages from an ActiveMQ queue engine.
     *
     * @return a JMS listener container factory bean.
     */
    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory()
    {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());

        return factory;
    }

    /**
     * Creates a Message Converter bean.
     * It allows to define the message format to send to an ActiveMQ queue engine.
     *
     * @return a Message Converter bean.
     */
    @Bean
    public MessageConverter messageConverter()
    {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(TYPE_ID_PROPERTY_NAME);

        return converter;
    }
}
