package com.almundo.callcenter.api.model;

import lombok.Getter;

/**
 * Enumeration that defines all the possible API error codes in the system.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Getter
public enum ApiErrorCode
{
	/**
	 * Code for unknown behaviors.
	 */
    GENERAL_ERROR("CALLCENTER-API-001", "Something went wrong."),

	/**
	 * Code for system inputs and outputs fields.
	 */
    FIELD_VALIDATION_ERROR("CALLCENTER-API-002", "Validation field error."),

	/**
	 * Code for unknown errors when employee is created.
	 */
    EMPLOYEE_CREATION_ERROR("CALLCENTER-API-003", "The employee could not be created.");

	/**
	 * The error code.
	 */
	private String code;

	/**
	 * The error message.
	 */
	private String message;

	/**
	 * Creates an API error code enum value given its code and message error.
	 *
	 * @param code the error code.
	 * @param message the error message.
	 */
	ApiErrorCode(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
}
