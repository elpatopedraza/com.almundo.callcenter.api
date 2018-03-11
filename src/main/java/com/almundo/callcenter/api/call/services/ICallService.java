package com.almundo.callcenter.api.call.services;

import com.almundo.callcenter.api.call.model.Call;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines all call business logic.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface ICallService
{
    /**
     * Creates a new call in the system.
     *
     * @param call the call to be created.
     * @return the new call.
     */
    Call createCall(Call call);

    /**
     * Updates an existent call.
     *
     * @param call the call to be updated.
     * @return the updated call.
     */
    Call updateCall(Call call);

    /**
     * Finds a call given its ID.
     *
     * @param callId the call ID to be searched.
     * @return the found call.
     */
    Optional<Call> findCallById(String callId);

    /**
     * Finds all calls created in the system.
     *
     * @return all calls.
     */
    List<Call> findAllCalls();
}
