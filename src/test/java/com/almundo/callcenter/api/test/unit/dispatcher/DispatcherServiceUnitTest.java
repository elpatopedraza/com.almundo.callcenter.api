package com.almundo.callcenter.api.test.unit.dispatcher;

import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.factories.CallStateServiceFactory;
import com.almundo.callcenter.api.call.services.ICallStateMachineService;
import com.almundo.callcenter.api.dispatcher.services.IDispatcherService;
import com.almundo.callcenter.api.employee.model.Employee;
import com.almundo.callcenter.api.employee.services.IEmployeeService;
import com.almundo.callcenter.api.test.integration.AbstractServiceUnitTest;
import com.almundo.callcenter.api.test.utils.Samples;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;
import java.util.Optional;

/**
 * Unit test suit for Dispatcher Service class.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class DispatcherServiceUnitTest extends AbstractServiceUnitTest
{
    /**
     * The employee service mock bean.
     */
    @MockBean
    private IEmployeeService employeeService;

    /**
     * The call service mock bean.
     */
    @MockBean
    private ICallService callService;

    /**
     * The call state service factory mock bean.
     */
    @MockBean
    private CallStateServiceFactory callStateServiceFactory;

    /**
     * The created to in progress call state service mock bean.
     */
    @MockBean
    @Qualifier("createdToInProgressCallStateService")
    private ICallStateMachineService createdToInProgressCallStateService;

    /**
     * The created to queued call state service mock bean.
     */
    @MockBean
    @Qualifier("createdToQueuedCallStateService")
    private ICallStateMachineService createdToQueuedCallStateService;

    /**
     * The service to test.
     */
    @Autowired
    private IDispatcherService dispatcherService;

    /**
     * Given a new call and the process call dispatcher service is called, the service should return an in progress call.
     */
    @Test
    public void givenNewCall_whenProcessCall_thenReturnInProgressCall() {
        Employee freeEmployee = Samples.createSampleEmployee();
        freeEmployee.setAvailable(true);

        Call newCall = Samples.createSampleCall(null);
        newCall.setState(CallState.CREATED);

        Mockito.when(employeeService.findFreeEmployee()).thenReturn(Optional.of(freeEmployee));
        Mockito.when(callStateServiceFactory.getCallStateService(newCall.getState(), CallState.IN_PROGRESS))
                .thenReturn(createdToInProgressCallStateService);

        Answer<Call> inProgressCallAnswer = invocation -> {
            Call inProgressCall = invocation.getArgument(0);
            inProgressCall.setState(CallState.IN_PROGRESS);

            return inProgressCall;
        };

        Mockito.when(createdToInProgressCallStateService.runChangeState(Mockito.any(Call.class), Mockito.any(Map.class)))
                .then(inProgressCallAnswer);

        Call result = dispatcherService.processCall(newCall);

        Assert.assertNotNull(result);
        Assert.assertEquals(CallState.IN_PROGRESS, result.getState());
    }

    /**
     * Given a new call and the process call dispatcher service is called, the service should return a queued call.
     */
    @Test
    public void givenNewCall_whenProcessCall_thenReturnQueuedCall() {
        Call newCall = Samples.createSampleCall(null);
        newCall.setState(CallState.CREATED);

        Mockito.when(employeeService.findFreeEmployee()).thenReturn(Optional.empty());
        Mockito.when(callStateServiceFactory.getCallStateService(newCall.getState(), CallState.QUEUED))
                .thenReturn(createdToQueuedCallStateService);

        Answer<Call> queuedCallAnswer = invocation -> {
            Call inProgressCall = invocation.getArgument(0);
            inProgressCall.setState(CallState.QUEUED);

            return inProgressCall;
        };

        Mockito.when(createdToQueuedCallStateService.runChangeState(newCall, null)).then(queuedCallAnswer);

        Call result = dispatcherService.processCall(newCall);

        Assert.assertNotNull(result);
        Assert.assertEquals(CallState.QUEUED, result.getState());
    }
}
