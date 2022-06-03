package com.gitter.socialapi;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NoSuchEntityException.class)
    public ResponseEntity<Object> noSuchEntityException(NoSuchEntityException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<Object> invalidEntryException(InvalidParameterException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = InvalidCodeTypeException.class)
    public ResponseEntity<Object> invalidTypeCodeException(InvalidCodeTypeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
