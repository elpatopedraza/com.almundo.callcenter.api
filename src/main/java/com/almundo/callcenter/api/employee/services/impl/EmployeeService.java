package com.almundo.callcenter.api.employee.services.impl;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.ICallStateMachineService;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.model.EmployeeType;
import com.almundo.callcenter.api.employee.repositories.EmployeeRepository;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Class that implements all the methods defined in the employee service interface.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Service
@Slf4j
public class EmployeeService implements IEmployeeService
{
    /**
     * The employee types to be searched in priority order for free employee method.
     */
    private final static EmployeeType[] EMPLOYEE_TYPE_PRIORITY = {
            EmployeeType.OPERATOR,
            EmployeeType.SUPERVISOR,
            EmployeeType.DIRECTOR,
    };

    /**
     * The employee repository.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * The call state service factory.
     */
    private final CallStateServiceFactory callStateServiceFactory;

    /**
     * Constructs an employee service object.
     *
     * @param employeeRepository the employee repository bean to be injected into the service.
     * @param callStateServiceFactory the call state service factory bean to be injected into the service.
     */
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CallStateServiceFactory callStateServiceFactory)
    {
        this.employeeRepository = employeeRepository;
        this.callStateServiceFactory = callStateServiceFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Employee createEmployee(Employee employee)
    {
        return saveEmployee(employee, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee updateEmployee(Employee employee)
    {
        return saveEmployee(employee, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findEmployeeById(String employeeId)
    {
        try {
            return employeeRepository.findById(employeeId);
        } catch(Exception e) {
            log.error("The employee with ID [{}] could not be retrieved.", employeeId, e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> findFreeEmployee()
    {
        log.debug("Finding next free employee.");
        Optional<Employee> nextEmployee = Optional.empty();

        for(EmployeeType employeeType : EMPLOYEE_TYPE_PRIORITY) {
            log.debug("Finding free employee of type [{}].", employeeType);
            nextEmployee = findFreeEmployee(employeeType);

            if(nextEmployee.isPresent()) {
                log.debug("Employee found.");
                break;
            }
        }

        return nextEmployee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Async
    public CompletableFuture<Call> processCall(Call call)
    {
        Employee employee = call.getEmployee();

        if(employee != null) {
            log.debug("Employee with ID [{}] is attending the call [{}].", employee.getId(), call.getId());

            try {
                Thread.sleep(call.getDurationInSecs() * Time.APR_MSEC_PER_USEC);
            } catch (InterruptedException e) {
                log.error("Error in call processing for call ID [{}].", call.getId(), e);
            }
        }

        return completeCall(call);
    }

    /**
     * Moves a call to its final state (finished).
     *
     * @param call the call to finish.
     * @return the finished call.
     */
    @Transactional
    private CompletableFuture<Call> completeCall(Call call)
    {
        ICallStateMachineService callStateService = callStateServiceFactory.getCallStateService(
                call.getState(), CallState.FINISHED);

        call = callStateService.runChangeState(call, null);
        log.debug("The call with ID [{}] has been finished.", call.getId());

        return CompletableFuture.completedFuture(call);
    }

    /**
     * Finds a free employee given its type.
     *
     * @param employeeType the employee type to be searched.
     * @return a free employee that matches the given type.
     */
    private Optional<Employee> findFreeEmployee(EmployeeType employeeType)
    {
        try {
            return employeeRepository.findTop1ByTypeAndAvailable(employeeType, true);
        } catch(Exception e) {
            log.error("Error trying to find free employee of type [{}].", employeeType, e);
            throw e;
        }
    }

    /**
     * Creates or updates an employee.
     *
     * @param employee the employee that handles the call.
     * @param isNewEmployee indicates if the employee should be created or updated.
     * @return the created or updated employee.
     */
    private Employee saveEmployee(Employee employee, boolean isNewEmployee)
    {
        if(isNewEmployee) {
            employee.setId(UUID.randomUUID().toString());
            employee.setAvailable(true);
        } else {
            if(!findEmployeeById(employee.getId()).isPresent()) {
                RuntimeException runtimeException = new RuntimeException();
                log.error("The employee with ID [{}] does not exists.", employee.getId());
                throw runtimeException;
            }
        }

        try {
            Employee savedEmployee = employeeRepository.saveAndFlush(employee);
            log.debug("Employee with ID [{}] has been {} successfully.", employee.getId(),
                    isNewEmployee ? "created" : "updated");

            return savedEmployee;
        } catch(Exception e) {
            log.debug("The employee with ID [{}] could not be saved right now.", employee.getId(),
                    isNewEmployee ? "created" : "updated", e);
            throw e;
        }
    }
}
