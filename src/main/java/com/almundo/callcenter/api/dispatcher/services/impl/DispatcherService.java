package com.almundo.callcenter.api.dispatcher.services.impl;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.ICallStateMachineService;
import com.almundo.callcenter.api.dispatcher.services.IDispatcherService;
import com.almundo.callcenter.api.call.services.impl.machinestates.CreatedToInProgressCallStateService;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class that implements all the methods defined in the dispatcher server interface.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Service
@Slf4j
public class DispatcherService implements IDispatcherService
{
    /**
     * The employee service.
     */
    private final IEmployeeService employeeService;

    /**
     * The call service.
     */
    private final ICallService callService;

    /**
     * The call state service factory.
     */
    private final CallStateServiceFactory callStateServiceFactory;

    /**
     * Constructs a dispatcher service object.
     *
     * @param employeeService the employee service bean to inject into the current dispatcher service.
     * @param callService the call service bean to inject into the current dispatcher service.
     * @param callStateServiceFactory the call state service factory bean to inject into the current dispatcher service.
     */
    @Autowired
    public DispatcherService(IEmployeeService employeeService,
                             ICallService callService,
                             CallStateServiceFactory callStateServiceFactory)
    {
        this.employeeService = employeeService;
        this.callService = callService;
        this.callStateServiceFactory = callStateServiceFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Call dispatchCall()
    {
        Call newCall = createNewCall();

        return processCall(newCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Call processCall(Call call)
    {
        Optional<Employee> freeEmployee = employeeService.findFreeEmployee();

        if(freeEmployee.isPresent()) {
            Employee employee = freeEmployee.get();
            log.debug("Employee found for incoming call: [{}]", employee);

            ICallStateMachineService createdToInProgressStateService = callStateServiceFactory.getCallStateService(
                    call.getState(), CallState.IN_PROGRESS);

            Map<String, Object> params = new HashMap<>();
            params.put(CreatedToInProgressCallStateService.EMPLOYEE_PARAMETER_NAME, employee);

            try {
                call = createdToInProgressStateService.runChangeState(call, params);
            } catch(TaskRejectedException e) {
                call = callService.findCallById(call.getId()).get();
                queueCall(call, false);
            }

        } else {
            call = queueCall(call, true);
        }

        return call;
    }

    /**
     * Creates a new empty call with its initial state (CREATED).
     *
     * @return a new empty call.
     */
    private Call createNewCall()
    {
        Call newCall = new Call();

        ICallStateMachineService toCreatedStateService = callStateServiceFactory.getCallStateService(
                null, CallState.CREATED);

        return toCreatedStateService.runChangeState(newCall, null);
    }

    /**
     * Queues a call in an ActiveMQ queue engine.
     * It could be called if there is no employees to attend the call or if max concurrence is reached.
     *
     * @param call the call to be queued.
     * @param employeeNotFound indicates if the call to be queued is due to no employee free or if max concurrence is reached.
     * @return the call in its queued state.
     */
    private Call queueCall(Call call, boolean employeeNotFound)
    {
        log.debug("{}. The call with ID [{}] will be queued.", employeeNotFound ? "Employee not found" :
                "Max concurrent calls reached", call.getId());

        ICallStateMachineService createdToQueuedStateService = callStateServiceFactory.getCallStateService(
                call.getState(), CallState.QUEUED);

        return createdToQueuedStateService.runChangeState(call, null);
    }
}
