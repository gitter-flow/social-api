package com.gitter.socialapi.kernel.exceptions;

import javax.naming.directory.InvalidAttributesException;

public class InvalidCodeTypeException extends InvalidAttributesException {
    public InvalidCodeTypeException(String message) {super(message);}
    public static InvalidParameterException withValue(String value) {
        return new InvalidParameterException(String.format("%s is not an handled language [chose: \"c\",\"shell\",\"python\"]", value));
    }
}
