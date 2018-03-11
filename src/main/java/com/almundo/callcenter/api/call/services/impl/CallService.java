package com.almundo.callcenter.api.call.services.impl;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.repositories.CallRepository;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.services.impl.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class that implements all the methods defined in the call service interface.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Service
@Slf4j
public class CallService implements ICallService
{
    /**
     * The employee service.
     */
    private final EmployeeService employeeService;

    /**
     * The call repository.
     */
    private final CallRepository callRepository;

    /**
     * Constructs a call service object.
     *
     * @param employeeService the employee service bean to be injected into the service.
     * @param callRepository the call repository bean to be injected into the service.
     */
    @Autowired
    public CallService(EmployeeService employeeService, CallRepository callRepository)
    {
        this.employeeService = employeeService;
        this.callRepository = callRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Call createCall(Call call)
    {
        return saveCall(call, null, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Call updateCall(Call call)
    {
        return saveCall(call, null, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Call> findCallById(String callId)
    {
        try {
            return callRepository.findById(callId);
        } catch(Exception e) {
            log.error("The call with ID [{}] could not be retrieved.", callId, e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Call> findAllCalls()
    {
        return callRepository.findAll();
    }

    /**
     * Creates or updates a call with an employee that handles it.
     *
     * @param call the call to be created or updated.
     * @param employee the employee that handles the call.
     * @param isNewCall indicates if the call should be created or updated.
     * @return the created or updated call.
     */
    private Call saveCall(Call call, Employee employee, boolean isNewCall)
    {
        if(isNewCall) {
            call.setId(UUID.randomUUID().toString());
        }

        if(employee != null) {
            if(employeeService.findEmployeeById(employee.getId()).isPresent()) {
                call.setEmployee(employee);
            } else {
                RuntimeException runtimeException = new RuntimeException();
                log.error("The employee with ID [{}] does not exists.", employee.getId());
                throw runtimeException;
            }
        }

        try {
            Call savedCall = callRepository.saveAndFlush(call);
            log.debug("Call with ID [{}] has been {} successfully.", call.getId(), isNewCall ? "created" : "updated");

            return savedCall;
        } catch(Exception e) {
            log.debug("The call with ID [{}] could not be saved right now.", call.getId(),
                    isNewCall ? "created" : "updated", e);
            throw e;
        }
    }
}
