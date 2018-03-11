package com.almundo.callcenter.api.call.services.impl.machinestates;

import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that represents the queued to in progress call state transition service.
 * It is used when an employee is available to attend a call, but the max concurrence is reached.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.QUEUED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class QueuedToInProgressCallStateService extends CreatedToInProgressCallStateService
{
    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     * @param employeeService the employee service bean to be injected into current transition service.
     */
    @Autowired
    public QueuedToInProgressCallStateService(ICallService callService, IEmployeeService employeeService)
    {
        super(callService, employeeService);

        initialState = CallState.QUEUED;
        newState = CallState.IN_PROGRESS;
    }
}
