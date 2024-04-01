package server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FieldValidationAdvice extends ResponseEntityExceptionHandler {

    /**
     * ObjectNotFoundException handler
     * @param ufve the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({FieldValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleObjectNotFound(FieldValidationException ufve){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ufve.getMessage());
    }
}
