package server.service;

import dto.exceptions.PasswordExpiredException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordService {

    private final int length = 16;
    private SecureRandom random;

    private Optional<String> password;

    /**
     * Constructor
     * @param random - injected SecureRandom instance
     */
    public PasswordService(SecureRandom random){
        password = Optional.empty();
        this.random=random;
    }

    /**
     * generates a password and prints it to the console
     */
    public void generatePassword(){
        byte[] arr = new byte[length];
        random.nextBytes(arr);
        password = Optional.of(Base64.getUrlEncoder().withoutPadding()
                .encodeToString(arr).substring(0, length));
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
            throw new PasswordExpiredException("Last password has expired. Generate a new one.");
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
     * @return the password
     */
    protected Optional<String> getPassword() {
        return password;
    }
}
