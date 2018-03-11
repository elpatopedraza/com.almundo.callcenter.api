package com.almundo.callcenter.api.call.delegates;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;

import java.util.List;

/**
 * Interface that defines all input and output validations methods from Call Controller.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface ICallDelegate
{
    /**
     * Finds all calls registered in the system.
     *
     * @return all the calls registered in a DTO format.
     */
    List<CallDTO> findAllCalls();
}
