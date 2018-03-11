package com.almundo.callcenter.api.test.integration.employee;

import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.model.EmployeeType;
import com.almundo.callcenter.api.employee.repositories.EmployeeRepository;
import com.almundo.callcenter.api.test.integration.AbstractRepositoryIntegrationTest;
import com.almundo.callcenter.api.test.utils.Samples;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Integration test suit for Employee Repository class.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class EmployeeRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest
{
    /**
     * The repository to test.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Create employee test case.
     * It should return a simple new employee.
     */
    @Test
    public void testCreateEmployee()
    {
        Employee employee = Samples.createSampleEmployee();
        Employee savedEmployee = employeeRepository.save(employee);

        Assert.assertNotNull(savedEmployee);
        Assert.assertEquals(employee, savedEmployee);
    }

    /**
     * Find employee by ID test case.
     * It should return an employee that matches with its ID.
     */
    @Test
    public void testFindEmployeeById()
    {
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        Optional<Employee> result = employeeRepository.findById(employee.getId());
        Assert.assertTrue(result.isPresent());

        Employee employeeFound = result.get();
        Assert.assertEquals(employee, employeeFound);
    }

    /**
     * Update employee test case.
     * It should return an updated employee that matches with its modified fields.
     */
    @Test
    public void testUpdateEmployee()
    {
        EmployeeType employeeTypeToUpdate = EmployeeType.DIRECTOR;
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        employee.setType(employeeTypeToUpdate);
        employeeRepository.save(employee);

        Optional<Employee> result = employeeRepository.findById(employee.getId());
        Assert.assertTrue(result.isPresent());

        Employee employeeFound = result.get();
        Assert.assertEquals(employeeTypeToUpdate, employeeFound.getType());
    }

    /**
     * Delete employee test case.
     * It should delete an employee given its ID.
     */
    @Test
    public void testDeleteEmployee()
    {
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        employeeRepository.delete(employee);

        Optional<Employee> result = employeeRepository.findById(employee.getId());
        Assert.assertFalse(result.isPresent());
    }

    /**
     * Find by type and available employee test case.
     * It should return an employee that matches with a given employee type and availability.
     */
    @Test
    public void testFindByTypeAndAvailableEmployee()
    {
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        Optional<Employee> result = employeeRepository.findTop1ByTypeAndAvailable(
                employee.getType(), employee.getAvailable());

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(employee, result.get());
    }
}
