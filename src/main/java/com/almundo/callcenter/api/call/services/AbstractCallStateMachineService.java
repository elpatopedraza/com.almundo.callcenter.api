package com.almundo.callcenter.api.call.services;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * Abstract class that implements all call state machine methods for every call state service transitions.
 * Every call state service transition should extends this class.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Service
@Slf4j
public abstract class AbstractCallStateMachineService implements ICallStateMachineService
{
    /**
     * The initial call state for current call state service transition.
     */
    protected CallState initialState;

    /**
     * The final call state for current call state service transition.
     */
    protected CallState newState;

    /**
     * The call service.
     */
    protected ICallService callService;

    /**
     * Constructs a call state service transition object.
     *
     * @param callService the call service bean to inject into the current call state service transition object.
     */
    @Autowired
    public AbstractCallStateMachineService(ICallService callService)
    {
        this.callService = callService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call preChangeState(Call call, Map<String, Object> params)
    {
        log.debug("Preparing call state change for call with ID [{}] from [{}] to [{}].", call.getId(),
                initialState, newState);

        return call;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Call runChangeState(Call call, Map<String, Object> params)
    {
        preChangeState(call, params);

        log.debug("Making call state change for call with ID [{}] from [{}] to [{}].", call.getId(),
                initialState, newState);

        call.setState(newState);
        Call updatedCall = callService.updateCall(call);

        postChangeState(updatedCall, params);

        return updatedCall;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call postChangeState(Call call, Map<String, Object> params)
    {
        log.debug("Finishing call state change for call with ID [{}] with new state [{}].", call.getId(), newState);

        return call;
    }
}
