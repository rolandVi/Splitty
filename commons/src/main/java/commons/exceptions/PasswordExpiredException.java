package commons.exceptions;

public class PasswordExpiredException extends RuntimeException{

    /**
     * Default constructor
     */
    public PasswordExpiredException(){
        super();
    }

    /**
     * Constructor
     * @param message - exception message
     */
    public PasswordExpiredException(String message){
        super(message);
    }
}