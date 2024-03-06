package client.utils;

import commons.exceptions.PasswordExpiredException;
import jakarta.ws.rs.core.Response;
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
    public Boolean validatePassword(String p) throws PasswordExpiredException {
        if (p.isEmpty()) throw new PasswordExpiredException("Password cannot be empty");
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/password/validatePassword")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(p, APPLICATION_JSON));

        System.out.println(response.getStatus());

        if (response.getStatus() == Response.Status.OK.getStatusCode()){
            return true;
        }else if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()){
            return false;
        }else {
            throw new PasswordExpiredException(response.readEntity(String.class));
        }

//        if (p.isEmpty()) return false;
//        return ClientBuilder.newClient(new ClientConfig())
//                .target(SERVER).path("api/password/validatePassword")
//                .request(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .post(Entity.entity(p, APPLICATION_JSON), Boolean.class);
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
