package com.almundo.callcenter.api.call.services.impl.machinestates;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.dispatcher.services.IQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that represents the queued to queued call state transition service.
 * It is used when a call was queued by any reason and the system tries to dispatch it again, but needs to be queued it again.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.QUEUED_TO_QUEUED_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class QueuedToQueuedCallStateService extends CreatedToQueuedCallStateService
{
    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     * @param callQueueService the call queue service bean to be injected into current transition service.
     */
    @Autowired
    public QueuedToQueuedCallStateService(ICallService callService, IQueueService<Call> callQueueService)
    {
        super(callService, callQueueService);

        initialState = CallState.QUEUED;
        newState = CallState.QUEUED;
    }
}
