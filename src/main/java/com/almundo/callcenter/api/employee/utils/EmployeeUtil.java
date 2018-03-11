package com.almundo.callcenter.api.employee.utils;

import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.model.EmployeeType;
import org.apache.commons.lang3.EnumUtils;

/**
 * Class that defines useful methods for employee and employee DTO objects.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class EmployeeUtil
{
    /**
     * Converts an employee into its DTO format.
     *
     * @param employee the employee to be converted.
     * @return the employee in its DTO format.
     */
    public static EmployeeDTO employeeToEmployeeDTO(Employee employee)
    {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setAvailable(employee.getAvailable());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setId(employee.getId());
        employeeDTO.setNames(employee.getNames());
        employeeDTO.setSurnames(employee.getSurnames());

        if(employee.getType() != null) {
            employeeDTO.setType(employee.getType().name());
        }

        return employeeDTO;
    }

    /**
     * Converts a DTO employee into its entity format.
     *
     * @param employeeDTO the DTO employee to be converted.
     * @return the DTO employee in its entity format.
     */
    public static Employee employeeDTOToEmployee(EmployeeDTO employeeDTO)
    {
        Employee employee = new Employee();

        employee.setAvailable(employeeDTO.getAvailable());
        employee.setEmail(employeeDTO.getEmail());
        employee.setId(employeeDTO.getId());
        employee.setNames(employeeDTO.getNames());
        employee.setSurnames(employeeDTO.getSurnames());

        if(employeeDTO.getType() != null) {
            employee.setType(EnumUtils.getEnum(EmployeeType.class, employeeDTO.getType()));
        }

        return employee;
    }
}
