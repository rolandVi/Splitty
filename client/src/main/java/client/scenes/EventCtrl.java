package client.scenes;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.util.MultiValueMap;


import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.dto.view.EventDetailsDto;
import server.dto.view.EventTitleDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class EventCtrl {
    private final MainCtrl mainCtrl;
    @FXML
    public Label eventNameLabel;
    @FXML
    public Button returnButton;
    @FXML
    public TextField changeTextField;
    @FXML
    public Button changeButton;
    @FXML
    public Label participantsLabel;
    @FXML
    public Label inviteCode;
    @FXML
    public Label expensesLabel;
    @FXML
    public Button addExpenseButton;
    @FXML
    public Button addParticipant;
    private EventDetailsDto eventDetailsDto;
    private final ObjectMapper objectMapper;

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Will update the event name to the server and update the current event name
     * @return HTTP response from the server
     * @throws JsonProcessingException when the objectMapper cannot properly
     * turn the EventTitleDto into Json format string
     */
    public Optional<HttpResponse<String>> changeEventName() throws JsonProcessingException {
        // Todo: replace temporary values with eventDetailsDto.getID() and host selected at start
        long id = 1L;
        String url = "http://localhost:8080";

        // Create HTTP request body
        EventTitleDto eventTitleDto = new EventTitleDto(changeTextField.getText());
        String requestBody = objectMapper.writeValueAsString(eventTitleDto);

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/events/" + id))
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
        return response;
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        mainCtrl.showNewExpense();
    }

    /**
     * Getter for the eventDetailsDto
     * @return the Data transfer object of current event showing
     */
    public EventDetailsDto getEventDetailsDto() {
        return eventDetailsDto;
    }

    /**
     * Setter for the eventDetailsDto
     * @param eventDetailsDto the Data transfer object of current event showing
     */
    public void setEventDetailsDto(EventDetailsDto eventDetailsDto) {
        this.eventDetailsDto = eventDetailsDto;
    }

    /**
     * shows the newParticipant scene
     */
    public void newParticipant(){
        mainCtrl.showNewParticipant();
    }
}
