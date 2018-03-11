package com.almundo.callcenter.api.call.domain.dtos;

import com.almundo.callcenter.api.employee.domain.dtos.EmployeeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * Class that represents a Call entity in a DTO format.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Data
@EqualsAndHashCode
@ToString
public class CallDTO
{
    /**
     * The call ID.
     */
    @Size(max = 36)
    private String id;

    /**
     * The employee that attends the call in a DTO format.
     */
    private EmployeeDTO employee;

    /**
     * The call duration in seconds.
     */
    private Integer durationInSecs;

    /**
     * The current call state.
     */
    private String state;
}
