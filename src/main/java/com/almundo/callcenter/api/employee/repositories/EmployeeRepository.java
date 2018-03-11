package com.almundo.callcenter.api.employee.repositories;

import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.model.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Class that handles all the data logic operations for an employee in a H2 database.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public interface EmployeeRepository extends JpaRepository<Employee, String>
{
    /**
     * Finds an employee given its type and its availability.
     *
     * @param employeeType the employee type to search.
     * @param available the employee availability to search.
     * @return the employee that matches with both parameters.
     */
    Optional<Employee> findTop1ByTypeAndAvailable(EmployeeType employeeType, boolean available);
}