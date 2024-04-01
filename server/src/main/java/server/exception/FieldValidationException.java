package server.exception;


public class FieldValidationException extends RuntimeException {
    private String message;

    public FieldValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
