package com.gitter.socialapi.kernel.exceptions;

public class UnexpectedInternalResponseException extends RuntimeException {
    public UnexpectedInternalResponseException(String message) {
        super(message);
    } 
    
    public static UnexpectedInternalResponseException forRequest(String url, String body) {
        return new UnexpectedInternalResponseException(
                String.format("{url: %s, \nbody: %s", url, body)
        );
    }
}
