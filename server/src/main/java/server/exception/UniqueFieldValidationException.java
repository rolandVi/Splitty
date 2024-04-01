package server.exception;

public class UniqueFieldValidationException extends RuntimeException {

    private String message;

    /**
     * Constructor
     * @param message the message of the exception
     */
    public UniqueFieldValidationException(String message) {
        this.message=message;
    }

    /**
     * Getter for the message
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
