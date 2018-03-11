package com.almundo.callcenter.api.dispatcher.controllers;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.dispatcher.delegates.IDispatcherDelegate;
import com.almundo.callcenter.api.model.ApiError;
import com.almundo.callcenter.api.model.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class that handles all REST requests for call dispatcher.
 */
@RestController
@RequestMapping("/dispatcher")
@Slf4j
public class DispatcherController
{
    /**
     * The dispatcher delegate.
     */
    private final IDispatcherDelegate dispatcherDelegate;

    /**
     * Constructs a dispatcher controller object.
     *
     * @param dispatcherDelegate the dispatcher delegate bean to inject into dispatcher controller.
     */
    @Autowired
    public DispatcherController(IDispatcherDelegate dispatcherDelegate)
    {
        this.dispatcherDelegate = dispatcherDelegate;
    }

    /**
     * Handles all the logic related to state machine call.
     *
     * @return a call in its current state.
     */
    @RequestMapping(value = "/dispatch", method = RequestMethod.POST)
    ResponseEntity<?> dispatchCall()
    {
        CallDTO callDTOResponse = null;
        ApiError apiErrorResponse = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            callDTOResponse = dispatcherDelegate.dispatchCall();
        } catch(Exception e) {
            log.error("Unexpected error.", e);
            apiErrorResponse = new ApiError(ApiErrorCode.GENERAL_ERROR);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus == HttpStatus.OK ? callDTOResponse : apiErrorResponse, httpStatus);
    }
}