package com.almundo.callcenter.api.employee.domain.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class that represents an Employee entity in a DTO format.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Data
@EqualsAndHashCode
@ToString
public class EmployeeDTO
{
    /**
     * The employee ID.
     */
    @Size(max = 36)
    private String id;

    /**
     * The employee names.
     */
    @NotNull
    @Size(max = 256)
    private String names;

    /**
     * The employee surnames.
     */
    @NotNull
    @Size(max = 256)
    private String surnames;

    /**
     * The employee email.
     */
    @NotNull
    @Size(max = 256)
    private String email;

    /**
     * The employee type.
     */
    @NotNull
    @Size(max = 128)
    private String type;

    /**
     * The employee availability.
     */
    private Boolean available;
}
