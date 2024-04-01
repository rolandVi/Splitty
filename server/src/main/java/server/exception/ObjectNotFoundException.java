package server.exception;

public class ObjectNotFoundException extends RuntimeException {

    private final String message;

    public ObjectNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}