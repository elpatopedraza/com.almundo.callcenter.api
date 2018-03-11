package com.almundo.callcenter.api.call.services;

import com.almundo.callcenter.api.call.model.Call;

import java.util.Map;

/**
 * Interface that defines all methods that should be implemented by every call state service transition.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface ICallStateMachineService
{
    /**
     * Method that handles all the logic related to a call state transition before apply the new change.
     *
     * @param call the call to operate.
     * @param params the params needed to run the logic.
     * @return the call after the applied logic.
     */
    Call preChangeState(Call call, Map<String, Object> params);

    /**
     * Method that handles all the logic related to a call state transition during the state change.
     * It should run preChangeState and postChangeState methods.
     *
     * @param call the call to apply the new state.
     * @param params the params needed to run the logic.
     * @return the call after state change.
     */
    Call runChangeState(Call call, Map<String, Object> params);

    /**
     * Method that handles all the logic related to a call state transition after the state change.
     *
     * @param call the call to operate.
     * @param params the params needed to run the logic.
     * @return the call after the applied logic.
     */
    Call postChangeState(Call call, Map<String, Object> params);
}
