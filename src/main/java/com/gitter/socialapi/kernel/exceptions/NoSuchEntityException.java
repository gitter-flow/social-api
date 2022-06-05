package com.gitter.socialapi.kernel.exceptions;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public static NoSuchEntityException withId(String entityName, String id) {
        return new NoSuchEntityException(String.format("No %s found with ID %s.", entityName, id));
    }
}
