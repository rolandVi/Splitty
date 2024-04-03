package client.scenes.admin;


import client.ConfigManager;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.view.EventOverviewDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class AdminOverviewPageCtrl {
    private final AdminMainCtrl adminMainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public MenuButton orderButton;
    @FXML
    public Button restore;

    @FXML
    private VBox eventContainer;

    private Image titleImg;
    private Image lastModifiedImg;
    private Image creationTimeImg;

    private String currentOrder = "title";

    public ConfigManager config;


    /**
     * Injector for EventOverviewCtrl
     * @param adminMainCtrl the admin controller
     * @param serverUtils   the server utilities
     */
    @Inject
    public AdminOverviewPageCtrl( AdminMainCtrl adminMainCtrl, ServerUtils serverUtils){
        this.adminMainCtrl = adminMainCtrl;
        this.serverUtils=serverUtils;
        this.config = new ConfigManager("client/src/main/resources/config.properties");

    }

    /**
     * Loads the order menu
     * This method should be run only on startup!
     */
    public void loadOrder(){
        currentOrder = "title";
        titleImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/title.png")),
                20, 20, true, true);
        lastModifiedImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/lastModified.png")),
                20, 20, true, true);
        creationTimeImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/creationTime.png")),
                20, 20, true, true);

        orderButton.setGraphic(imageByOrder());

        MenuItem titleMenuItem = new MenuItem("title", new ImageView(titleImg));
        MenuItem creationMenuItem = new MenuItem("creation", new ImageView(creationTimeImg));
        MenuItem lastModifiedMenuItem = new MenuItem("last modified",
                new ImageView(lastModifiedImg));


        titleMenuItem.setOnAction(this::handleOrderMenuItem);
        creationMenuItem.setOnAction(this::handleOrderMenuItem);
        lastModifiedMenuItem.setOnAction(this::handleOrderMenuItem);

        orderButton.getItems().removeAll();
        orderButton.getItems().addAll(titleMenuItem, creationMenuItem, lastModifiedMenuItem);

    }

    /**
     * Handles the correct order
     * @param actionEvent The button pressed
     */
    public void handleOrderMenuItem(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();
        currentOrder = menuItem.getText();

        orderButton.setGraphic(imageByOrder());
        orderButton.setText(currentOrder);

        loadEvents();
    }

    private Node imageByOrder() {
        if (currentOrder.equals("title")){
            return new ImageView(titleImg);
        }

        if (currentOrder.equals("creation")){
            return new ImageView(creationTimeImg);
        }

        if (currentOrder.equals("last modified")){
            return new ImageView(lastModifiedImg);
        }

        throw new RuntimeException("impossible ordering");
    }

    /**
     * Loads the events and displays them on the page
     */
    public void loadEvents() {
        List<EventOverviewDto> events = getEventOverviewDtos();

        Node[] nodes=new Node[events.size()];


        for (int i = 0; i < nodes.length; i++) {
            var loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource(Path.of("client.scenes", "adminEventItem.fxml").toString()));
            try {
                nodes[i]=loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Node currentNode=nodes[i];
            final EventOverviewDto event=events.get(i);

            Button eventButton = (Button) currentNode.lookup("#eventTitle");
            eventButton.setText(events.get(i).getTitle());
            eventButton.setOnAction(e -> showDetails(event.getId()));

            Button inviteBtn=(Button) currentNode.lookup("#jsonBtn");
            inviteBtn.setOnAction(e -> getJSON(event.getId()));

            Button deleteBtn= (Button) currentNode.lookup("#deleteBtn");
            deleteBtn.setOnAction(e -> {
                try {
                    deleteEvent(event.getId());
                } catch (IOException | URISyntaxException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        this.eventContainer.getChildren().clear();
        this.eventContainer.getChildren().addAll(nodes);
    }

    private List<EventOverviewDto> getEventOverviewDtos() {
        List<EventOverviewDto> events = serverUtils.getAllEvents();



        if (currentOrder.equals("title")){
            events.sort(Comparator.comparing(EventOverviewDto::getTitle));
        } else if (currentOrder.equals("creation")){
            events.sort(Comparator.comparing(EventOverviewDto::getCreationDate));
        } else if (currentOrder.equals("last modified")){
            events.sort(Comparator.comparing(EventOverviewDto::getLastModifiedDate).reversed());
        } else {
            throw new RuntimeException("Impossible ordering");
        }


        return events;
    }

    /**
     * Deletes the event
     * @param id the id of the event
     */
    private void deleteEvent(long id) throws IOException, URISyntaxException, InterruptedException {
        serverUtils.deleteEvent(id);
        refresh();
    }

    /**
     * Shows the event details page
     * @param id the id of the event
     */
    private void showDetails(long id) {
        //todo: get admin details?
    }

    /**
     * Copies a json of the chosen event
     * @param id the id of the event
     */
    public void getJSON(Long id){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        try {
            URL url = new URL(config.getProperty("serverURL") + "/api/events/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Set the retrieved JSON content to clipboard
                content.putString(response.toString());
                clipboard.setContent(content);
                System.out.println("JSON copied to clipboard.");
            } else {
                System.out.println("Error dumping database: " + con.getResponseMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Refreshes the page()
     * //todo: remove this when web sockets are implemented
     */
    public void refresh(){
        loadEvents();
    }

    /**
     * Shows the restoring page for an admin to restore the JSON dump of an event
     */
    public void showRestore(){
        adminMainCtrl.showRestore();
    }

}
