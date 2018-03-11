package com.almundo.callcenter.api.call.delegates.impl;

import com.almundo.callcenter.api.call.delegates.ICallDelegate;
import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.services.ICallService;
import com.almundo.callcenter.api.call.utils.CallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that implements all call delegate methods.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component
public class CallDelegate implements ICallDelegate
{
    private final ICallService callService;

    /**
     * Constructs a call delegate object.
     *
     * @param callService the call service bean to inject into the current delegate.
     */
    @Autowired
    public CallDelegate(ICallService callService)
    {
        this.callService = callService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CallDTO> findAllCalls()
    {
        List<Call> allCalls = callService.findAllCalls();

        return allCalls.stream().map(call -> CallUtil.callToCallDTO(call)).collect(Collectors.toList());
    }
}