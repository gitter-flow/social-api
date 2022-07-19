package com.gitter.socialapi.kernel.exceptions;

public class InvalidCodeVersionException extends InvalidParameterException {

    public InvalidCodeVersionException(String message) {
        super(message);
    }
    
    public static InvalidCodeVersionException of(String codeId, String versionId) {
        return new InvalidCodeVersionException(String.format("No code found with id %s and version %s", codeId, versionId));
    }
    
    
}
