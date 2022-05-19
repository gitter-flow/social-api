package com.gitter.socialapi.kernel.exceptions;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String message) {
        super(message);
    }

    public static NoSuchEntityException withId(String entityName, Long id) {
        return new NoSuchEntityException(String.format("No %s found with ID %d.", entityName, id));
    }
}
