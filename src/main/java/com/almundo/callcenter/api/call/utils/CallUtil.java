package com.almundo.callcenter.api.call.utils;

import com.almundo.callcenter.api.call.domain.dtos.CallDTO;
import com.almundo.callcenter.api.call.model.Call;
import com.almundo.callcenter.api.call.model.CallState;
import com.almundo.callcenter.api.employee.utils.EmployeeUtil;
import org.apache.commons.lang3.EnumUtils;

/**
 * Class that defines useful methods for call and call DTO objects.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class CallUtil
{
    /**
     * Converts a call into its DTO format.
     *
     * @param call the call to be converted.
     * @return the call in its DTO format.
     */
    public static CallDTO callToCallDTO(Call call)
    {
        CallDTO callDTO = new CallDTO();

        callDTO.setDurationInSecs(call.getDurationInSecs());
        callDTO.setEmployee(call.getEmployee() != null ? EmployeeUtil.employeeToEmployeeDTO(
                call.getEmployee()) : null);
        callDTO.setId(call.getId());

        if(call.getState() != null) {
            callDTO.setState(call.getState().name());
        }

        return callDTO;
    }

    /**
     * Converts a DTO call into its entity format.
     *
     * @param callDTO the DTO call to be converted.
     * @return the DTO call in its entity format.
     */
    public static Call callDTOToCall(CallDTO callDTO)
    {
        Call call = new Call();

        call.setDurationInSecs(callDTO.getDurationInSecs());
        call.setEmployee(callDTO.getEmployee() != null ? EmployeeUtil.employeeDTOToEmployee(
                callDTO.getEmployee()) : null);
        call.setId(callDTO.getId());

        if(callDTO.getState() != null) {
            call.setState(EnumUtils.getEnum(CallState.class, callDTO.getState()));
        }

        return call;
    }
}
