package com.almundo.callcenter.api.call.model;

/**
 * Enumeration that contains all the possible call states.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public enum CallState
{
    /**
     * The initial call state.
     */
    CREATED,

    /**
     * State for queued calls (used when no employee is available or pool threads are unavailable).
     */
    QUEUED,

    /**
     * State for calls in progress (used when an employee is attending the call).
     */
    IN_PROGRESS,

    /**
     * The final call state (used when employee finishes the cal).
     */
    FINISHED;
}