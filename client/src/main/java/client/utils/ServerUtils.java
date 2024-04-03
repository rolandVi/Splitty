package client.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import dto.BankAccountCreationDto;
import dto.CreatorToTitleDto;
import dto.ExpenseCreationDto;
import dto.exceptions.PasswordExpiredException;
import dto.view.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
     * @param creatorToTitleDto the creator details and the event title
     * @return HTTP response from the server
     */
    public EventDetailsDto createEvent(CreatorToTitleDto creatorToTitleDto){
        return client.target(SERVER).path("api/events/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(creatorToTitleDto, APPLICATION_JSON),
                        EventDetailsDto.class);
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
     * Restores the data of old event
     * @param jsonData The data used to restore
     */
    public void restoreData(String jsonData){
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/events/restore"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData)).build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException("Failed to restore data. HTTP status code: "
                        + response.statusCode());
            }
            // Data restored successfully
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle the exception appropriately, e.g., log it
            throw new RuntimeException("Failed to restore data due to an exception: "
                    + e.getMessage(), e);
        }
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
     * Check for user existence
     * @param user user to check for existence
     * @return whether the user exists
     */
    public boolean userExists(ParticipantNameDto user){
        return client
                .target(SERVER).path("/api/users/exists")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(user, APPLICATION_JSON))
                .getStatus()!=404;
    }

    /**
     * Get the participants of a specific event
     * @param eventId id of the event
     * @return List of participants as List<UserNameDto>
     */
    public List<ParticipantNameDto> getParticipantsByEvent(long eventId) {
        return client
                .target(SERVER).path("/api/events/" + eventId + "/participants")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<ParticipantNameDto>>() {});
    }

    /**
     * Get details of a specific participant
     * @param id id of the participant
     * @return the participant as UserNameDto
     */
    public ParticipantNameDto getParticipantDetails(long id) {
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
                        + "/events/" + eventId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }
    /**
     * Enrolls the current user to the event with this invite code
     * @param inviteCode the invite code
     * @param userId the user id
     */
    public void enrollInEvent(String inviteCode, long userId) {
        client
                .target(SERVER).path("/api/users/events/"+inviteCode)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(userId, APPLICATION_JSON));
    }

    /**
     * Creates bank account
     *
     * @param userId      the user id
     * @param requestBody The request body
     * @param url         The url
     * @return the response of the request
     */
    public Response createBankAccount(Long userId,
                                      BankAccountCreationDto requestBody,
                                      String url) {

        return client
                .target(url).path("/api/users/" + userId + "/account")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(requestBody, APPLICATION_JSON));
    }

    /**
     * Creates the user
     * @param url The url
     * @param requestBody The request body
     * @return The response
     */
    public Optional<HttpResponse<String>> createUser(String url, String requestBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(url + "/api/users/"))
                .header("Content-Type", "application/json")
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

        return response;
    }

    /**
     * Gets the user id
     * @param url The URL
     * @param email the e-mail
     * @return The user id
     */
    public Long getUserId(String url, String email) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + "/api/users/" + email))
                .header("Content-Type", "application/json")
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

        return Long.valueOf(response.get().body());
    }

    /**
     * Adds a new expense to the event
     * @param eventId the id of the event
     * @param expenseCreationDto the details of the expense
     * @return the dreated expense details
     */
    public ExpenseDetailsDto addExpense(long eventId, ExpenseCreationDto expenseCreationDto) {

        Response expenseCreationResponse = client.target(SERVER)
                .path("api/expenses/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(expenseCreationDto, APPLICATION_JSON));

        return expenseCreationResponse.readEntity(ExpenseDetailsDto.class);
    }

    /**
     * Request for editing expenses
     * @param expanse details of the expense
     * @return the edited expense
     */
    public ExpenseDetailsDto editExpense(ExpenseDetailsDto expanse){
        Response response = client.target(SERVER)
                .path("api/expenses/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(expanse, APPLICATION_JSON));

        return response.readEntity(ExpenseDetailsDto.class);
    }

    /**
     * Removes the expense
     * @param eventId the id of the parent event
     * @param expenseId the id of the expense
     */
    public void removeExpense(Long eventId, Long expenseId){
        client.target(SERVER)
                .path("api/expenses/" + expenseId + "/" + eventId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Retreives the bank details of the current user
     * @param userID teh current user id
     * @param serverURL the server url
     * @return the bank details
     */
    public BankAccountDto findBankDetails(String userID, String serverURL) {
        return client.target(serverURL)
                .path("/api/users/" + userID + "/account")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<BankAccountDto>() {});
    }

    /**
     * Edits the bank account detials of the current user
     * @param userId the user id
     * @param bankAccount
     * @param url
     * @return  the response from the server
     */
    public Optional<HttpResponse<String>> editBankAccount(Long userId,
                                                          BankAccountCreationDto bankAccount,
                                                          String url) {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(bankAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/users/" + userId + "/account"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

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
