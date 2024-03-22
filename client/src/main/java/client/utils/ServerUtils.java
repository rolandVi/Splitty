package client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import server.dto.CreatorToTitleDto;
import server.dto.UserCreationDto;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventOverviewDto;
import server.dto.view.EventTitleDto;
import server.dto.view.UserNameDto;
import server.exceptions.PasswordExpiredException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
     * @param userId the user id
     * @return HTTP response from the server
     */
    public EventTitleDto createEvent(String eventName, Long userId){
        return client.target(SERVER).path("api/users/events/create")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(new CreatorToTitleDto(userId, eventName), APPLICATION_JSON),
                        EventTitleDto.class);
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

    /**
     * Deletes the event with the given ID from the server.
     *
     * @param eventId the ID of the event to delete
     * @throws IOException       if an I/O error occurs when sending or receiving
     *                           the HTTP request/response
     * @throws URISyntaxException if the provided URI is invalid
     * @throws InterruptedException if the thread is interrupted during the request
     */
    public void deleteEvent(long eventId) throws IOException,
            URISyntaxException, InterruptedException {
        // Create HTTP DELETE request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(SERVER + "api/events/" + eventId))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        // Send HTTP DELETE request to server
        HttpResponse<Void> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.discarding());

        // Check the response status code
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to delete event. Server returned status code: "
                    + response.statusCode());
        }
    }

    /**
     * Check the validity of the given user credentials
     * @param user the user credentials
     * @return true if they are valid and false otherwise
     */
    public boolean checkUserValidity(UserCreationDto user){
        return client
                .target(SERVER).path("/api/users/check")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON))
                .getStatus()!=400;
    }

    /**
     * Get the participants of a specific event
     * @param eventId id of the event
     * @return List of participants as List<UserNameDto>
     */
    public List<UserNameDto> getParticipantsByEvent(long eventId) {
        return client
                .target(SERVER).path("/api/events/" + eventId + "/participants")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<UserNameDto>>() {});
    }

    /**
     * Get details of a specific participant
     * @param id id of the participant
     * @return the participant as UserNameDto
     */
    public UserNameDto getParticipantDetails(long id) {
        return null;
        //TODO
    }

    /**
     * Delete a participant from an event
     * @param eventId id of the specific event
     * @param participantId id of the specific participant
     */
    public void deleteEventParticipant(long eventId, long participantId) {
        client
                .target(SERVER).path("/api/users/" + participantId
                        + "/events/" + eventId + "/leave")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }
    /**
     * Enrolls the current user to the event with this invite code
     * @param inviteCode the invite code
     */
    public void enrollInEvent(String inviteCode) {
        //TODO: get the id of the current user (not hard code it)
        long currentUserId= 1L;

        client
                .target(SERVER).path("/api/users/events/join/"+inviteCode)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(currentUserId, APPLICATION_JSON));
    }
}
