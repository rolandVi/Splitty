package server.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordService {

    private static final int LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    private String password;
    private boolean used;

    /**
     * Constructor
     */
    public PasswordService(){}

    /**
     * genarates a password and prints it to the console
     */
    public void generatePassword(){
        byte[] arr = new byte[LENGTH];
        random.nextBytes(arr);
        password = Base64.getUrlEncoder().withoutPadding().encodeToString(arr).substring(0, LENGTH);
        used=false;
        System.out.println("Password: " + password);
    }

    /**
     * Validates the password
     * @param p - password to validate
     * @return whether password is correct
     */
    public boolean validatePassword(String p) {
        if (!used){
            used=true;
            return p.equals(password);
        }else {
            return false;
        }

    }
}
