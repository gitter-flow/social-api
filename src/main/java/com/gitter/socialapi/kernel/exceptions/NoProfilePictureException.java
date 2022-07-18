package com.gitter.socialapi.kernel.exceptions;

public class NoProfilePictureException extends Exception {
    public NoProfilePictureException(String message) {super(message);}

    public static NoProfilePictureException forUser(String userId) {
        return new NoProfilePictureException(String.format("User with ID %s has no profile picture", userId));
    }
}
