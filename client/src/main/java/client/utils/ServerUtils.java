package client.utils;


import com.google.inject.Inject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Entity;
import server.exceptions.PasswordExpiredException;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    // to be changed to pull from the file
    private static final String SERVER = "http://localhost:8080/";

    private final Client client;

    /**
     * Constructor injection
     * @param client - instance of Client
     */
    @Inject
    public ServerUtils(Client client){
        this.client=client;
    }

    /**
     * Validates password
     * @param p - password entered
     * @return - boolean whether password is correct
     */
    public Boolean validatePassword(String p) throws PasswordExpiredException {
        if (p.isEmpty()) throw new PasswordExpiredException("Password cannot be empty");

        Response response = client.target(SERVER).path("api/password/validatePassword")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(p, APPLICATION_JSON));

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        } else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            return false;
        } else {
            throw new PasswordExpiredException(response.readEntity(String.class));
        }
    }



    /**
     * Generates new password
     */
    public void generatePassword(){
        client.target(SERVER).path("api/password/generatePassword")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(null);

    }

}
