package com.almundo.callcenter.api.employee.delegates.impl;

import com.almundo.callcenter.api.employee.delegates.IEmployeeDelegate;
import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.utils.EmployeeUtil;
import com.almundo.callcenter.api.model.exceptions.FieldValidationException;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Class that implements all employee delegate methods.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component
@Slf4j
public class EmployeeDelegate implements IEmployeeDelegate
{
    /**
     * The employee service.
     */
    private final IEmployeeService employeeService;

    /**
     * Constructs an employee delegate object.
     *
     * @param employeeService the employee service bean to inject into the current delegate.
     */
    @Autowired
    public EmployeeDelegate(IEmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()) {
            FieldError firstFieldError = bindingResult.getFieldErrors().get(0);

            StringBuilder errorMessage = new StringBuilder(firstFieldError.getField());
            errorMessage.append(StringUtils.SPACE);
            errorMessage.append(firstFieldError.getDefaultMessage());

            log.error(errorMessage.toString());
            throw new FieldValidationException(errorMessage.toString());
        }

        Employee employee = EmployeeUtil.employeeDTOToEmployee(employeeDTO);
        employee = employeeService.createEmployee(employee);

        return EmployeeUtil.employeeToEmployeeDTO(employee);
    }
}
