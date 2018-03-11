package com.almundo.callcenter.api.dispatcher.services;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.Session;

/**
 * Interface that defines all the methods related to a Queue engine logic.
 *
 * @param <T> The message class type to work with.
 */
public interface IQueueService<T>
{
    /**
     * Sends an object to a queue engine.
     *
     * @param object the object to send.
     */
    void send(T object);

    /**
     * Receives an object from a queue engine.
     *
     * @param object the received object.
     * @param headers the headers queue engine response.
     * @param message the message that stores the retrieved object.
     * @param session the session used in the queue engine connection.
     */
    void receive(@Payload T object, @Headers MessageHeaders headers, Message message, Session session);
}
