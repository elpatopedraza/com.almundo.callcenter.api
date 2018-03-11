package com.almundo.callcenter.api.call.services.impl.machinestates;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.AbstractCallStateMachineService;
import com.almundo.callcenter.api.dispatcher.services.IQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class that represents the created to queued call state transition service.
 * It is used when a call is queued when there is not an available employee or if max concurrence is reached.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.CREATED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class CreatedToQueuedCallStateService extends AbstractCallStateMachineService
{
    /**
     * The call queue service.
     */
    private final IQueueService<Call> callQueueService;

    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     * @param callQueueService the call queue service bean to be injected into current transition service.
     */
    @Autowired
    public CreatedToQueuedCallStateService(ICallService callService, IQueueService<Call> callQueueService)
    {
        super(callService);

        initialState = CallState.CREATED;
        newState = CallState.QUEUED;

        this.callQueueService = callQueueService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call postChangeState(Call call, Map<String, Object> params)
    {
        log.debug("Queueing call with ID [{}].", call.getId());
        callQueueService.send(call);

        return call;
    }
}
