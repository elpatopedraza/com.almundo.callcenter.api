package com.almundo.callcenter.api.dispatcher.services;

import com.almundo.callcenter.api.call.model.Call;

/**
 * Interface that defines all the methods related to call dispatching logic.
 */
public interface IDispatcherService
{
    /**
     * Creates a new call and handles all the logic related to call state machine transitions for that call.
     *
     * @return the new call in its current state.
     */
    Call dispatchCall();

    /**
     * Handles all the logic related to call state machine transitions for a call.
     *
     * @param call the call to handle.
     * @return the call in its current state.
     */
    Call processCall(Call call);
}
