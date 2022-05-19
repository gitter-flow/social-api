package com.gitter.socialapi.kernel.exceptions;

import javax.naming.directory.InvalidAttributesException;

public class InvalidParameterException extends InvalidAttributesException {
    public InvalidParameterException(String message) {super(message);}

    public static InvalidParameterException forField(String field, String value) {
        return new InvalidParameterException(String.format("%s is not a valid value for the field %s", value, field));
    }
    public static InvalidParameterException unprovided(String field) {
        return new InvalidParameterException(String.format("No value has been provided for %s", field));
    }
}
