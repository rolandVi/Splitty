package server.exception;


public class FieldValidationException extends RuntimeException {
    private String message;

    /**
     * Constructor for the exception
     * @param message the message of the exception
     */
    public FieldValidationException(String message) {
        this.message = message;
    }

    /**
     * Message getter
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
