package client.utils;

import commons.exceptions.PasswordExpiredException;

import com.google.inject.Inject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Entity;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

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

    /**
     * Creates a new event on the database
     * @param eventName name of event
     * @return
     */
    public Optional<HttpResponse<String>> createEvent(String eventName){
        // Create HTTP request using eventName as body
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(eventName))
                .uri(URI.create(SERVER + "/api/events/"))
                .header("Content-Type", "text/plain")
                .build();

        // Send HTTP request to server
        // Receive HTTP response from server
        Optional<HttpResponse<String>> response;
        try {
            response = Optional.of(HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

}
