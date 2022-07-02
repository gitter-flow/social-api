package com.gitter.socialapi.kernel.exceptions;

public class UnexpectedInternalRequestException extends RuntimeException {
    public UnexpectedInternalRequestException(String message) {
        super(message);
    } 
    
    public static UnexpectedInternalRequestException forRequest(String url, int statusCode, String body) {
        return new UnexpectedInternalRequestException(
                String.format("{url: %s,\n code: %d,\nbody: %s", url, statusCode, body)
        );
    }
}
