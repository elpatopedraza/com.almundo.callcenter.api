package com.almundo.callcenter.api.dispatcher.delegates.impl;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.utils.CallUtil;
import com.almundo.callcenter.api.dispatcher.delegates.IDispatcherDelegate;
import com.almundo.callcenter.api.dispatcher.services.IDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that implements all the dispatcher delegate interface methods.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Component
public class DispatcherDelegate implements IDispatcherDelegate
{
    /**
     * The dispatcher service.
     */
    private final IDispatcherService dispatcherService;

    /**
     * Constructs a dispatcher delegate object.
     *
     * @param dispatcherService the dispatcher service bean to inject into current dispatcher delegate.
     */
    @Autowired
    public DispatcherDelegate(IDispatcherService dispatcherService)
    {
        this.dispatcherService = dispatcherService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CallDTO dispatchCall()
    {
        Call newCall = dispatcherService.dispatchCall();

        return CallUtil.callToCallDTO(newCall);
    }
}