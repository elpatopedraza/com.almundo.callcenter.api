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
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Class that represents the in progress to finished call state transition service.
 * It is used when a call is attended by its employee successfully.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component(CallStateServiceFactory.IN_PROGRESS_TO_FINISHED_CALL_STATE_SERVICE_BEAN_NAME)
@Slf4j
public class InProgressToFinishedCallStateService extends AbstractCallStateMachineService
{
    /**
     * The employee service.
     */
    private final IEmployeeService employeeService;

    /**
     * Creates a call state transition service object.
     *
     * @param callService the call service bean to be injected into current transition service.
     * @param employeeService the employee service bean to be injected into current transition service.
     */
    @Autowired
    public InProgressToFinishedCallStateService(ICallService callService, IEmployeeService employeeService)
    {
        super(callService);

        this.employeeService = employeeService;

        initialState = CallState.IN_PROGRESS;
        newState = CallState.FINISHED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Call postChangeState(Call call, Map<String, Object> params)
    {
        Employee employee = call.getEmployee();

        if(employee != null) {
            log.debug("Releasing employee with ID [{}] from call [{}].", employee.getId(), call.getId());
            employee.setAvailable(true);
            employeeService.updateEmployee(employee);
        }

        return call;
    }
}
