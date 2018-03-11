package com.almundo.callcenter.api.employee.services;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.employee.model.Employee;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Interface that defines all employee business logic.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface IEmployeeService
{
    /**
     * Creates a new employee in the system.
     *
     * @param employee the employee to be created.
     * @return the new employee.
     */
    Employee createEmployee(Employee employee);

    /**
     * Updates an existent employee.
     *
     * @param employee the employee to be updated.
     * @return the updated employee.
     */
    Employee updateEmployee(Employee employee);

    /**
     * Finds an employee given its ID.
     *
     * @param employeeId the employee ID to be searched.
     * @return the found employee.
     */
    Optional<Employee> findEmployeeById(String employeeId);

    /**
     * Finds a free employee ready to attend a call.
     *
     * @return a free employee.
     */
    Optional<Employee> findFreeEmployee();

    /**
     * Processes a call by a free employee.
     *
     * @param call the call to be processed.
     * @return the processed call.
     */
    CompletableFuture<Call> processCall(Call call);
}
