package com.almundo.callcenter.api.call.model;

import com.almundo.callcenter.api.employee.model.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class that represents a Call as an entity.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Entity
@Table(schema = "core", name = "call")
@Data
@EqualsAndHashCode
@ToString
public class Call
{
    /**
     * The call ID.
     */
    @Id
    @NotNull
    @Size(max = 36)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * The employee that attends the call.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * The call duration in seconds.
     */
    @Column(name = "duration_in_secs")
    private Integer durationInSecs;

    /**
     * The current call state.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private CallState state;
}