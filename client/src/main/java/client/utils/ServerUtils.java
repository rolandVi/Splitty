package client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.exceptions.PasswordExpiredException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
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
     * Creates HTTP request to the server using the parameter as name of event
     * @param eventName name of event
     * @return HTTP response from the server
     */
    public EventTitleDto createEvent(String eventName){
        return client.target(SERVER).path("api/events/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(eventName, APPLICATION_JSON), EventTitleDto.class);
    }
    /**
     * Updates the event name to the server and update the current event name
     * @param id event ID
     * @param newEventName the new event name to change with the corresponding ID
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public void changeEventName(long id, String newEventName) throws JsonProcessingException {
        // Create HTTP request body
        ObjectMapper objectMapper = new ObjectMapper();
        EventTitleDto eventTitleDto = new EventTitleDto(newEventName);
        String requestBody = objectMapper.writeValueAsString(eventTitleDto);

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER + "api/events/" + id))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send HTTP request to server
        // Return HTTP response from server
        Optional<HttpResponse<String>> response;
        try {
            response = Optional.of(HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Response response = client.target(SERVER).path("api/events/" + id)
//                .request(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .build("PATCH", Entity.entity(requestBody, APPLICATION_JSON))
//                .invoke();
    }

    /**
     * Get the event details of a specific event with the given id
     * @param id the id of the event
     * @return the event details
     */
    public EventDetailsDto getEventDetails(long id) {
        return client
                .target(SERVER).path("/api/events/" + id)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(EventDetailsDto.class);
    }

    /**
     * Get the events of a specific user
     * @param id the id of the user
     * @return the events
     */
    public List<EventOverviewDto> getEventsByUser(long id){
        return client
                .target(SERVER).path("/api/users/"+id+"/events")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<EventOverviewDto>>() {});
    }

    /**
     * Get all events
     * @return all events
     */
    public List<EventOverviewDto> getAllEvents(){
        return client
                .target(SERVER).path("/api/events")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<EventOverviewDto>>() {});
    }

}
