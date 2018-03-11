package com.almundo.callcenter.api.call.controllers;

import com.almundo.callcenter.api.call.delegates.ICallDelegate;
import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.model.ApiError;
import com.almundo.callcenter.api.model.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Class that handles all call REST requests.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@RestController
@RequestMapping("/call")
@Slf4j
public class CallController
{
    /**
     * The call delegate component.
     */
    private final ICallDelegate callDelegate;

    /**
     * The call controller constructor.
     *
     * @param callDelegate the call delegate component to inject into the controller.
     */
    @Autowired
    public CallController(ICallDelegate callDelegate)
    {
        this.callDelegate = callDelegate;
    }

    /**
     * Finds all calls created in the system.
     *
     * @return all the calls created.
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> findAllCalls()
    {
        List<CallDTO> callDTOsResponse = null;
        ApiError apiErrorResponse = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            callDTOsResponse = callDelegate.findAllCalls();
        } catch(Exception e) {
            log.error("Unexpected error.", e);
            apiErrorResponse = new ApiError(ApiErrorCode.GENERAL_ERROR);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus == HttpStatus.OK ? callDTOsResponse : apiErrorResponse, httpStatus);
    }
}