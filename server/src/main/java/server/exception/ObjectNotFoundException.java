package server.exception;

public class ObjectNotFoundException extends RuntimeException {

    private final String message;

    /**
     * Constructor for the exception
     * @param message teh exception message
     */
    public ObjectNotFoundException(String message) {
        this.message = message;
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