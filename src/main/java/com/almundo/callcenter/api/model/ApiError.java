package com.almundo.callcenter.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a generic API error.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
@Getter
@Setter
public class ApiError
{
	/**
	 * The error code.
	 */
	private String code;

	/**
	 * The error message.
	 */
	private String message;

	/**
	 * Constructs an empty API error object.
	 */
	public ApiError()
	{

	}

	/**
	 * Constructs an API error object given an error code.
	 *
	 * @param apiErrorCode the error code to set in the API error object.
	 */
	public ApiError(ApiErrorCode apiErrorCode)
	{
		code = apiErrorCode.getCode();
		message = apiErrorCode.getMessage();
	}
}
