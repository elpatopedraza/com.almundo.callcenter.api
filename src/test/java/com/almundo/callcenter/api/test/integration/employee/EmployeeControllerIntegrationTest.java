package com.almundo.callcenter.api.test.integration.employee;

import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.repositories.EmployeeRepository;
import com.almundo.callcenter.api.employee.utils.EmployeeUtil;
import com.almundo.callcenter.api.test.integration.AbstractControllerIntegrationTest;
import com.almundo.callcenter.api.test.utils.Samples;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class EmployeeControllerIntegrationTest extends AbstractControllerIntegrationTest
{
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenCreateEmployee_thenReturnNewEmployee()
    {
        Employee employee = Samples.createSampleEmployee();
        EmployeeDTO employeeDTO = EmployeeUtil.employeeToEmployeeDTO(employee);

        EmployeeDTO result = restTemplate.postForObject("/employee", employeeDTO, EmployeeDTO.class);
        System.out.println(result);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertNotNull(result.getType());
        Assert.assertNotNull(result.getNames());
        Assert.assertNotNull(result.getSurnames());
        Assert.assertNotNull(result.getEmail());

        Assert.assertTrue(result.getAvailable());

        Assert.assertEquals(employee.getId(), employeeDTO.getId());
    }

    @After
    public void afterTest()
    {
        employeeRepository.deleteAll();
    }
}
