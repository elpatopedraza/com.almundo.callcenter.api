package com.almundo.callcenter.api.employee.controllers;

import com.almundo.callcenter.api.employee.delegates.IEmployeeDelegate;
import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import com.almundo.callcenter.api.model.ApiError;
import com.almundo.callcenter.api.model.ApiErrorCode;
import com.almundo.callcenter.api.model.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Class that handles all employee REST requests.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController
{
    /**
     * The employee delegate.
     */
    private final IEmployeeDelegate employeeDelegate;

    /**
     * The employee controller constructor.
     *
     * @param employeeDelegate the employee delegate component to inject into the controller.
     */
    @Autowired
    public EmployeeController(IEmployeeDelegate employeeDelegate)
    {
        this.employeeDelegate = employeeDelegate;
    }

    /**
     * Creates a new employee and adds it into the system.
     *
     * @param employeeDTO the new employee in its DTO format.
     * @param bindingResult the spring validation object for the employee DTO.
     * @return the created employee in its DTO format.
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult)
    {
        EmployeeDTO employeeDTOResponse = null;
        ApiError apiErrorResponse = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            employeeDTOResponse = employeeDelegate.createEmployee(employeeDTO, bindingResult);
        } catch(FieldValidationException e) {
            apiErrorResponse = new ApiError(ApiErrorCode.FIELD_VALIDATION_ERROR);
            apiErrorResponse.setMessage(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch(Exception e) {
            apiErrorResponse = new ApiError(ApiErrorCode.GENERAL_ERROR);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus == HttpStatus.OK ? employeeDTOResponse : apiErrorResponse, httpStatus);
    }
}
