package com.almundo.callcenter.api.test.integration.call;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.repositories.CallRepository;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.repositories.EmployeeRepository;
import com.almundo.callcenter.api.test.integration.AbstractRepositoryIntegrationTest;
import com.almundo.callcenter.api.test.utils.RandomUtil;
import com.almundo.callcenter.api.test.utils.Samples;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * Integration test suit for Call Repository class.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class CallRepositoryIntegrationTest extends AbstractRepositoryIntegrationTest
{
    /**
     * The repository to test.
     */
    @Autowired
    private CallRepository callRepository;

    /**
     * The employee repository.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Create call test case.
     * It should return a simple new call.
     */
    @Test
    public void testCreateCall()
    {
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        Call call = Samples.createSampleCall(employee);
        Call savedCall = callRepository.save(call);

        Assert.assertNotNull(savedCall);
        Assert.assertEquals(call, savedCall);
        Assert.assertEquals(call.getEmployee(), savedCall.getEmployee());
    }

    /**
     * Find call by ID test case.
     * It should return a call that matches with its ID.
     */
    @Test
    public void testFindCallById()
    {
        Call call = Samples.createSampleCall(null);
        callRepository.save(call);

        Optional<Call> result = callRepository.findById(call.getId());
        Assert.assertTrue(result.isPresent());

        Call callFound = result.get();
        Assert.assertEquals(call, callFound);
    }

    /**
     * Update call test case.
     * It should return an updated call that matches with its modified fields.
     */
    @Test
    public void testUpdateEmployee()
    {
        CallState callStateToUpdate = RandomUtil.getRandomEnum(CallState.class);
        Call call = Samples.createSampleCall(null);
        callRepository.save(call);

        call.setState(callStateToUpdate);
        callRepository.save(call);

        Optional<Call> result = callRepository.findById(call.getId());
        Assert.assertTrue(result.isPresent());

        Call callFound = result.get();
        Assert.assertEquals(callStateToUpdate, callFound.getState());
    }

    /**
     * Delete call test case.
     * It should delete a call given its ID.
     */
    @Test
    public void testDeleteCall()
    {
        Employee employee = Samples.createSampleEmployee();
        employeeRepository.save(employee);

        Call call = Samples.createSampleCall(employee);
        callRepository.save(call);

        callRepository.delete(call);

        Optional<Call> result = callRepository.findById(call.getId());
        Assert.assertFalse(result.isPresent());

        Optional<Employee> employeeResult = employeeRepository.findById(employee.getId());
        Assert.assertTrue(employeeResult.isPresent());
    }
}
