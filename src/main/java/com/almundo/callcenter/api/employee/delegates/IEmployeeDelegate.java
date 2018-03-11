package com.almundo.callcenter.api.employee.delegates;

import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import org.springframework.validation.BindingResult;

/**
 * Interface that defines all input and output validations methods from Employee Controller.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface IEmployeeDelegate
{
    /**
     * Creates a new employee and adds it into the system.
     *
     * @param employeeDTO the new employee in its DTO format.
     * @param bindingResult the spring validation object for the employee DTO.
     * @return the created employee in its DTO format.
     */
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO, BindingResult bindingResult);
}
