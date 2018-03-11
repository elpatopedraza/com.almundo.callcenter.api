package com.almundo.callcenter.api.call.services.impl.machinestates;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.AbstractCallStateMachineService;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that represents the created to in progress call state transition service.
 * It is used when an employee is available to attend a call.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.CREATED_TO_IN_PROGRESS_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class CreatedToInProgressCallStateService extends AbstractCallStateMachineService
{
    /**
     * The employee parameter name.
     */
    public static final String EMPLOYEE_PARAMETER_NAME = "EMPLOYEE";

    /**
     * The employee service.
     */
    protected final IEmployeeService employeeService;

    /**
     * The minimum duration in seconds for a call.
     */
    @Value("${callcenter.api.call.minDuration}")
    private int minDuration;

    /**
     * The max duration in seconds for a call.
     */
    @Value("${callcenter.api.call.maxDuration}")
    private int maxDuration;

    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     * @param employeeService the employee service bean to be injected into current transition service.
     */
    @Autowired
    public CreatedToInProgressCallStateService(ICallService callService, IEmployeeService employeeService)
    {
        super(callService);

        initialState = CallState.CREATED;
        newState = CallState.IN_PROGRESS;

        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call preChangeState(Call call, Map<String, Object> params)
    {
        log.debug("Calculating duration in seconds for call with ID [{}]", call.getId());
        call.setDurationInSecs(ThreadLocalRandom.current().nextInt(minDuration, maxDuration + 1));

        Employee employee = (Employee) params.get(EMPLOYEE_PARAMETER_NAME);
        call.setEmployee(employee);

        return call;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call postChangeState(Call call, Map<String, Object> params)
    {
        log.debug("Processing call with ID [{}].", call.getId());
        Employee employee = call.getEmployee();

        if(employee != null && call.getDurationInSecs() != null) {
            employee.setAvailable(false);
            call.setEmployee(employeeService.updateEmployee(employee));
            employeeService.processCall(call);
        }

        return call;
    }
}
