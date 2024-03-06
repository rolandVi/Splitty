package server.service;

import org.springframework.stereotype.Service;
import commons.exceptions.PasswordExpiredException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordService {

    private static final int LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();

    private Optional<String> password;

    /**
     * Constructor
     */
    public PasswordService(){
        password = Optional.empty();
    }

    /**
     * generates a password and prints it to the console
     */
    public void generatePassword(){
        byte[] arr = new byte[LENGTH];
        random.nextBytes(arr);
        password = Optional.of(Base64.getUrlEncoder().withoutPadding()
                .encodeToString(arr).substring(0, LENGTH));
        System.out.println("Password: " + password.get());
    }

    /**
     * Validates the password
     * @param p - password to validate
     * @return whether password is correct
     */
    public boolean validatePassword(String p)
            throws PasswordExpiredException {
        if (password.isEmpty()){
            throw new PasswordExpiredException("Last password has expired");
        }

        if (p.equals(password.get())){
            password=Optional.empty();
            return true;
        }else {
            return false;
        }
    }

    /**
     * Getter for password
     * @return
     */
    protected Optional<String> getPassword() {
        return password;
    }
}
