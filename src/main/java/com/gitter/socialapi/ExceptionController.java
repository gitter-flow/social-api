package com.gitter.socialapi;

import com.gitter.socialapi.kernel.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NoSuchEntityException.class)
    public ResponseEntity<Object> noSuchEntityException(NoSuchEntityException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<Object> invalidEntryException(InvalidParameterException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = InvalidCodeTypeException.class)
    public ResponseEntity<Object> invalidTypeCodeException(InvalidCodeTypeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UnexpectedInternalResponseException.class)
    public ResponseEntity<Object> unexpectedInternalResponse(UnexpectedInternalResponseException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = UnexpectedInternalRequestException.class)
    public ResponseEntity<Object> unexpectedInternalRequest(UnexpectedInternalResponseException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = InvalidCodeVersionException.class)
    public ResponseEntity<Object> unexpectedInternalRequest(InvalidCodeVersionException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = TeamAlreadyExistsException.class)
    public ResponseEntity<Object> teamAlreadyExists(TeamAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
