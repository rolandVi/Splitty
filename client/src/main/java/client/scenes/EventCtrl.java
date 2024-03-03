package client.scenes;

import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.util.MultiValueMap;

import client.view.EventTitleDto;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    /**
     * Injector for Event Controller
     * @param mainCtrl The Main Controller
     */
    @Inject
    public EventCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Will show event overview when the return button is pressed
     */
    public void returnToOverview(){
        mainCtrl.showOverview();
    }

    /**
     * Will update the event name to the server and updates the current event name
     */
    public void changeEventName() throws IOException, InterruptedException {
        long id = 1L; // Todo: replace temporary id

//        String apiUrl = "http://localhost:8080/api/events/1";
//
//        EventTitleDto eventTitleDto = new EventTitleDto(changeTextField.getText());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<EventTitleDto> requestEntity = new HttpEntity<>(eventTitleDto, headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<EventTitleDto> responseEntity = restTemplate.exchange(
//                apiUrl,
//                HttpMethod.PATCH,
//                requestEntity,
//                EventTitleDto.class
//        );

        EventTitleDto eventTitleDto = new EventTitleDto();
        eventTitleDto.setTitle(changeTextField.getText());
        eventTitleDto.setId(1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(eventTitleDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/events/1"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    /**
     * Will show add expense scene, allowing the user to add an expense
     */
    public void addExpense(){
        mainCtrl.showNewExpense();
    }

}
