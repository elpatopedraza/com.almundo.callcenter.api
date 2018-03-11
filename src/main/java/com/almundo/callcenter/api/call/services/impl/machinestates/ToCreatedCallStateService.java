package com.almundo.callcenter.api.call.services.impl.machinestates;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.AbstractCallStateMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;

/**
 * Class that represents the initial call state transition service.
 * It is used when a call is created at first time.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.TO_CREATED_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class ToCreatedCallStateService extends AbstractCallStateMachineService
{
    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     */
    @Autowired
    public ToCreatedCallStateService(ICallService callService)
    {
        super(callService);

        initialState = null;
        newState = CallState.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Call runChangeState(Call call, Map<String, Object> params)
    {
        preChangeState(call, params);

        log.debug("Creating new call with ID [{}] and state [{}].", call.getId(), newState);

        call.setState(newState);
        Call createdCall = callService.createCall(call);

        postChangeState(createdCall, params);

        return createdCall;
    }
}
