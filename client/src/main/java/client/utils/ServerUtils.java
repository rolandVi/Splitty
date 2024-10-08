package client.utils;


import client.scenes.MainCtrl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.ExpenseEntity;
import commons.TagEntity;
import dto.BankAccountCreationDto;
import dto.CreatorToTitleDto;
import dto.ExpenseCreationDto;
import dto.exceptions.PasswordExpiredException;
import dto.view.*;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.scene.control.Alert;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private String server = "http://localhost:8080/";
    private StompSession session;

    private final Client client;

    private MainCtrl mainCtrl;

    private final Pattern pattern=Pattern.compile(
            "^(([^:\\/?#]+):)?(\\/\\/([^\\/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");

    /**
     * Constructor injection
     * @param client - instance of Client
     * @param mainCtrl - the mainCtrl
     */
    @Inject
    public ServerUtils(Client client, MainCtrl mainCtrl){
        this.client=client;
        this.mainCtrl=mainCtrl;
    }

    /**
     * Constructor for testing
     * @param client Instance of Client
     * @param stompSession The stompSession that can be mocked for testing
     */
    public ServerUtils(Client client, StompSession stompSession){
        this.client=client;
        session = stompSession;
    }


    /**
     * Setter for the server
     * @param serverInserted teh new server
     */
    public void setServer(String serverInserted) {
        this.server =serverInserted;
    }

    /**
     * Setter for the session
     * @param connect the new session
     */
    public void setSession(StompSession connect) {
        this.session=connect;
    }


    /**
     * Opens a web socket connection
     */
    public void openSocketConnection() {
        Matcher matcher=pattern.matcher(server);
        if (matcher.matches()){
            String url=matcher.group(4);
            this.setSession(this.connect("ws://"+ url +"/websocket"));
        }
    }

    /**
     * Validates password
     * @param p - password entered
     * @return - boolean whether password is correct
     */
    public Boolean validatePassword(String p) throws PasswordExpiredException {
        testConnection(server);
        if (p.isEmpty()) throw new PasswordExpiredException("Password cannot be empty");

        Response response = client.target(server).path("api/password/validatePassword")
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
        testConnection(server);
        client.target(server).path("api/password/generatePassword")
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
        testConnection(server);
        return client.target(server).path("api/events/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(creatorToTitleDto, APPLICATION_JSON),
                        EventDetailsDto.class);
    }

    /**
     * Creates connection with the server
     * @param url The URL of the endpoint
     * @return A StompSession object
     */
    public StompSession connect(String url) {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        }catch (InterruptedException e){

        }catch (ExecutionException e){
            throw new RuntimeException();
        }
        throw new IllegalStateException();
    }

    /**
     *
     * @param dest The URL of the endpoint
     * @param type The type of the object to be sent
     * @param consumer The consumer
     * @param <T> The generic type
     */
    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }
            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Sends an object to a particular endpoint
     * @param dest The endpoint
     * @param o The object to be sent
     */
    public void send(String dest, Object o){
        session.send(dest, o);
    }

    /**
     * Updates the event name to the server and update the current event name
     * @param id event ID
     * @param newEventName the new event name to change with the corresponding ID
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public void changeEventName(long id, String newEventName) throws JsonProcessingException {
        testConnection(server);
        // Create HTTP request body
        ObjectMapper objectMapper = new ObjectMapper();
        EventTitleDto eventTitleDto = new EventTitleDto(newEventName);
        String requestBody = objectMapper.writeValueAsString(eventTitleDto);

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "api/events/" + id))
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
        testConnection(server);
        return client
                .target(server).path("/api/events/" + id)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(EventDetailsDto.class);
    }

    /**
     * Get all events
     * @return all events
     */
    public List<EventOverviewDto> getAllEvents() {
        testConnection(server);
        return client
                .target(server).path("/api/events")
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
                .uri(new URI(server + "api/events/" + eventId))
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
     * Get the participants of a specific event
     * @param eventId id of the event
     * @return List of participants as List<UserNameDto>
     */
    public List<ParticipantNameDto> getParticipantsByEvent(long eventId) {
        return client
                .target(server).path("/api/events/" + eventId + "/participants")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<ParticipantNameDto>>() {});
    }

    /**
     * Get details of a specific participant
     * @param parId id of the participant
     * @param eventId id of the event that the participant belongs to
     * @return the participant as UserNameDto
     */
    public ParticipantNameDto getParticipantDetails(long parId, long eventId) {
        List<ParticipantNameDto> dtoList = getParticipantsByEvent(eventId);
        return dtoList.stream().filter(d -> d.getId() == parId).findFirst().get();
    }

    /**
     * Delete a participant from an event
     * @param eventId id of the specific event
     * @param participantId id of the specific participant
     */
    public void deleteEventParticipant(long eventId, long participantId) {
        client
                .target(server).path("/api/events/" + eventId + "/participants/" + participantId)
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
                .target(server).path("/api/events/"+inviteCode)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(userId, APPLICATION_JSON));
    }

    /**
     * Creates bank account
     *
     * @param userId      the user id
     * @param requestBody The request body
     * @return the response of the request
     */
    public Response createBankAccount(Long userId,
                                      BankAccountCreationDto requestBody) {

        return client
                .target(server).path("/api/participants/" + userId + "/account")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(requestBody, APPLICATION_JSON));
    }

    /**
     * Creates the user
     * @param requestBody The request body (ParticipantCreationDto)
     * @param eventID The id of the event where the participant belongs to
     * @return The response
     */
    public Optional<HttpResponse<String>> createUser(String requestBody, long eventID) {
        testConnection(server);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(server + "/api/events/" + eventID + "/participants"))
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
     * Adds a new expense to the event
     * @param eventId the id of the event
     * @param expenseCreationDto the details of the expense
     * @return the created expense details
     */
    public ExpenseDetailsDto addExpense(long eventId, ExpenseCreationDto expenseCreationDto) {

        Response expenseCreationResponse = client.target(server)
                .path("/api/expenses/new")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(expenseCreationDto, MediaType.APPLICATION_JSON));

        return expenseCreationResponse.readEntity(ExpenseDetailsDto.class);
    }

    /**
     * Request for editing expenses
     * @param expanse details of the expense
     * @return the edited expense
     */
    public ExpenseDetailsDto editExpense(ExpenseDetailsDto expanse){
        Response response = client.target(server)
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
        client.target(server)
                .path("api/expenses/" + expenseId + "/" + eventId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
    }

    /**
     * Get expenseDetailsDto by id
     * @param expenseId ID of the expense
     * @return the expense details
     */
    public ExpenseDetailsDto getExpenseDetailsById(Long expenseId){
        return client.target(server)
                .path("api/expenses/" + expenseId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get().readEntity(ExpenseDetailsDto.class);
    }

    /**
     * Retreives the bank details of the current user
     * @param userID teh current user id
     * @return the bank details
     */
    public BankAccountDto findBankDetails(long userID) {
        return client.target(server)
                .path("/api/participants/" + userID + "/account")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<BankAccountDto>() {});
    }

    /**
     * Edits the bank account details of the current user
     * @param userId the user id
     * @param bankAccount the bank account
     * @return  the response from the server
     */
    public Optional<HttpResponse<String>> editBankAccount(Long userId,
                                                          BankAccountCreationDto bankAccount) {
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(bankAccount);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/api/participants/" + userId + "/account"))
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

    /**
     * Returns the event details of an event with the invite code
     * @param inviteCode the invite code of the event
     * @return the details
     */
    public EventDetailsDto getEventDetailsByInviteCode(String inviteCode) {
        return client
                .target(server).path("/api/events/invites/" + inviteCode)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(EventDetailsDto.class);
    }

    /**
     * Retrieves all tags from the server.
     *
     * @return List of TagEntity objects representing all tags.
     */
    public List<TagEntity> getAllTags() {
        Response response = client
                .target(server)
                .path("/api/tags/all")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<>() {
            });
        } else {

            return Collections.emptyList();
        }
    }
    /**
     * Edits the information of participant on the server using HTTP request
     * @param participantNameDto the new information of the participant
     */
    public void editParticipant(ParticipantNameDto participantNameDto) {
        // Create HTTP request body
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(participantNameDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server + "/api/participants/" + participantNameDto.getId()))
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
    }

    /**
     * Creates a new tag
     * @param tagDto tagdto
     */
    public void createTag(TagDto tagDto) {
        client.target(server)
                .path("/api/tags/newtag")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tagDto, MediaType.APPLICATION_JSON), String.class);
    }

    /**
     * Retrieves all expenses of an event from the server.
     * @param eventId The ID of the event to retrieve expenses from.
     * @return List of ExpenseEntity objects representing all expenses of the event.
     */
    public List<ExpenseEntity> getAllExpensesOfEvent(long eventId) {
        Response response = client
                .target(server)
                .path("/api/events/" + eventId + "/expenses")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<>() {
            });
        } else {
            // Handle other response statuses, such as error responses
            return Collections.emptyList();
        }
    }

     /**
     *
     * @param to the email to which the invite is sent
     * @param inviteCode the invite code of the event
     */
    public void sendEmail(String to, String inviteCode) {
        client.target(server)
                .path("/send-email/" + to + "/" + inviteCode)
                .request()
                .get(String.class);
    }


    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();


    /**
     * Register for updates for long polling
     * @param consumer The consumer
     */
    public void registerForUpdates(Consumer<EventOverviewDto> consumer) {
        EXEC.submit(() -> {
            while (!Thread.interrupted()){
                var res = client
                        .target(server).path("/api/events/updates")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);

                if (res.getStatus() == 204){
                    continue;
                }
                var q = res.readEntity(new GenericType<EventOverviewDto>() {});
                consumer.accept(q);
            }

        });

    }

    /**
     * Stops the thread
     */
    public void stop(){
        EXEC.shutdownNow();

    }

    /**
     * updates a tag
     * @param tagEntity tagEntity
     */
    public void updateTag(TagEntity tagEntity) {
        client.target(server)
                .path("/api/tags/" + tagEntity.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(tagEntity, MediaType.APPLICATION_JSON));
    }

    /**
     * Tests the connection to the server with the given url
     * @param url the url of the server
     * @return whether the request was successful
     */
    public boolean testConnection(String url) {
        try {
            client
                    .target(url).path("/api/events/connect")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get();
            return true;
        }catch (ProcessingException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(this.mainCtrl.lang.getString("server_header"));
            alert.setContentText(this.mainCtrl.lang.getString("server_message"));
            alert.showAndWait().ifPresent(response -> this.mainCtrl.showStart());
            return false;
        }
    }

    /**
     * Deletes a tag
     * @param tagId id of the tag
     */
    public void deleteTag(Long tagId) {
        client.target(server)
                .path("/api/tags/" + tagId)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    /**
     * Gets the tagentkty from the type
     * @param tagType the tagtype
     * @return the corresponding tagentity
     */
    public TagEntity getTagByTagType(String tagType) {
        List<TagEntity> allTags = getAllTags();
        for (TagEntity tag : allTags) {
            if (tag.getTagType().equals(tagType)) {
                return tag;
            }
        }
        return null; // Tag not found
    }

    /**
     * Deletes the bank account of a participant
     * @param participantId teh participant id
     * @throws URISyntaxException if uri is incorrect
     * @throws IOException if the endpoint is not accessible
     * @throws InterruptedException if the request has been interrupted
     */
    public void deleteBankAccountOf(long participantId) throws URISyntaxException,
            IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(server + "api/participants/" + participantId + "/account"))
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
}
