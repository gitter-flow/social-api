package com.gitter.socialapi.kernel.exceptions;

public class TeamAlreadyExistsException extends RuntimeException {
    public TeamAlreadyExistsException(String message) {
        super(message);
    }

    public static TeamAlreadyExistsException withName(String teamName) {
        return new TeamAlreadyExistsException(String.format("Team with name %s already exists.", teamName));
    }
}
