package client.utils;

import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Validates password
     * @param p - password entered
     * @return - boolean whether password is correct
     */
    public Boolean validatePassword(String p){
        //had to add this line, because for some reason whenever p is empty,
        //i.e. p="", server returns 400 bad request
        if (p.isEmpty()) return false;
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/password/validatePassword")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(p, APPLICATION_JSON), Boolean.class);
    }

    /**
     * Generates new password
     */
    public void generatePassword(){
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/password/generatePassword")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(null);

    }

}
