package com.almundo.callcenter.api.employee.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class that represents an Employee as an entity.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Entity
@Table(schema = "core", name = "employee")
@Data
@EqualsAndHashCode
@ToString
public class Employee
{
    /**
     * The employee ID.
     */
    @Id
    @NotNull
    @Size(max = 36)
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * The employee names.
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "names", nullable = false)
    private String names;

    /**
     * The employee surnames.
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "surnames", nullable = false)
    private String surnames;

    /**
     * The employee email.
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * The employee type.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    /**
     * The employee availability.
     */
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;
}
