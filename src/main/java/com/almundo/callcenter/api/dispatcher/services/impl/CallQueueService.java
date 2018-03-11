package com.almundo.callcenter.api.dispatcher.services.impl;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.dispatcher.config.ActiveMQConfiguration;
import com.almundo.callcenter.api.dispatcher.services.IDispatcherService;
import com.almundo.callcenter.api.dispatcher.services.IQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import javax.jms.Session;

/**
 * Class that implements all the methods defined in the call queue interface for ActiveMQ engine.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Service
@Slf4j
public class CallQueueService implements IQueueService<Call>
{
    /**
     * The JMS Template used to queue engine operations.
     */
    private final JmsTemplate jmsTemplate;

    /**
     * The dispatcher service.
     */
    private final IDispatcherService dispatcherService;

    /**
     * Constructs a call queue service object.
     *
     * @param jmsTemplate the JMS Template bean to inject into the current call queue service object.
     * @param dispatcherService the dispatcher service bean to inject into the current call queue service object.
     */
    @Autowired
    public CallQueueService(JmsTemplate jmsTemplate, IDispatcherService dispatcherService)
    {
        this.jmsTemplate = jmsTemplate;

        this.dispatcherService = dispatcherService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Call object)
    {
        log.debug("Sending call with ID [{}] to queue.", object.getId());
        jmsTemplate.convertAndSend(ActiveMQConfiguration.CALL_QUEUE, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @JmsListener(destination = ActiveMQConfiguration.CALL_QUEUE)
    public void receive(Call object, MessageHeaders headers, Message message, Session session)
    {
        log.debug("Call with ID [{}] received from queue successfully.", object.getId());
        dispatcherService.processCall(object);
    }
}
