package com.almundo.callcenter.api.test.integration.dispatcher.controllers;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.repositories.CallRepository;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.repositories.EmployeeRepository;
import com.almundo.callcenter.api.test.integration.AbstractControllerIntegrationTest;
import com.almundo.callcenter.api.test.utils.Samples;
import org.apache.commons.lang3.EnumUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;
import java.util.List;

public class DispatcherControllerIntegrationTest extends AbstractControllerIntegrationTest
{
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CallRepository callRepository;

    @Test
    public void givenNoneCallsAndElevenAvailableEmployees_whenDispatchElevenCalls_thenReturnLastCallInQueuedState()
    {
        int totalDispatches = 11;
        List<CallDTO> callDTOs = new ArrayList<>();

        for(int i = 0; i < totalDispatches; i++) {
            Employee employee = Samples.createSampleEmployee();
            employee.setAvailable(true);
            employeeRepository.saveAndFlush(employee);

            CallDTO callDTO = restTemplate.postForObject("/dispatcher/dispatch", null, CallDTO.class);
            callDTOs.add(callDTO);
        }

        for(int i = 0; i < totalDispatches - 1; i++) {
            CallDTO callDTO = callDTOs.get(i);

            Assert.assertNotNull(callDTO);
            Assert.assertNotNull(callDTO.getId());
            Assert.assertNotNull(callDTO.getEmployee());
            Assert.assertNotNull(callDTO.getState());
            Assert.assertEquals(CallState.IN_PROGRESS, EnumUtils.getEnum(CallState.class, callDTO.getState()));
        }

        CallDTO queuedCallDTO = callDTOs.get(totalDispatches - 1);

        Assert.assertNotNull(queuedCallDTO);
        Assert.assertNotNull(queuedCallDTO.getId());
        Assert.assertNull(queuedCallDTO.getEmployee());
        Assert.assertNotNull(queuedCallDTO.getState());
        Assert.assertEquals(CallState.QUEUED, EnumUtils.getEnum(CallState.class, queuedCallDTO.getState()));
    }

    @After
    public void afterTestSuit()
    {
        callRepository.deleteAll();
        employeeRepository.deleteAll();
    }
}
