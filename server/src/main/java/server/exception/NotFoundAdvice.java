package server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotFoundAdvice extends ResponseEntityExceptionHandler {

    /**
     * ObjectNotFoundException handler
     * @param onfe the exception
     * @return ResponseEntity
     */
    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleObjectNotFound(ObjectNotFoundException onfe){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(onfe.getMessage());
    }
}
