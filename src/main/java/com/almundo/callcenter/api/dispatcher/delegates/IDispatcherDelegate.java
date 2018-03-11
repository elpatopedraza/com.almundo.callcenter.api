package com.almundo.callcenter.api.dispatcher.delegates;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;

/**
 * Interface that defines all inputs and outputs that comes from Dispatcher Controller.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface IDispatcherDelegate
{
    /**
     * Handles all the logic related to state machine call.
     *
     * @return a call in its current state.
     */
    CallDTO dispatchCall();
}