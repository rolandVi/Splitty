package server.controller.exception;

public class PasswordNotFoundException extends RuntimeException{

    /**
     * Default constructor
     */
    public PasswordNotFoundException(){
        super();
    }

    /**
     * Constructor
     * @param message - exception message
     */
    public PasswordNotFoundException(String message){
        super(message);
    }
}
