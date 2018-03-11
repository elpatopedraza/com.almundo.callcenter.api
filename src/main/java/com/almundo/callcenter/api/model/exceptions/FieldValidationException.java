package com.almundo.callcenter.api.model.exceptions;

/**
 * Class that represents a field validation exception.
 *
 * @author Andrés Felipe Pedraza Infante.
 * @version 0.0.1
 */
public class FieldValidationException extends RuntimeException
{
    public FieldValidationException(String message)
    {
        super(message);
    }
}
