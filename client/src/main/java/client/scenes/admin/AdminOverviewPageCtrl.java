package client.scenes.admin;


import client.ConfigManager;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import dto.view.EventOverviewDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;


public class AdminOverviewPageCtrl implements Initializable {
    private final AdminMainCtrl adminMainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public MenuButton orderButton;
    @FXML
    public Button restore;

    @FXML
    public Text copyConfirmation;

    @FXML
    private VBox eventContainer;

    private Image titleImg;
    private Image lastModifiedImg;
    private Image creationTimeImg;

    private Image downloadImg;

    private Image copyImg;

    private Image deleteImg;

    private String currentOrder = "title";

    public ConfigManager config;

    private ObservableList<EventOverviewDto> events;


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
     * Initialize method
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        List<EventOverviewDto> evs = serverUtils.getAllEvents();
        events = FXCollections.observableList(evs);
        serverUtils.registerForUpdates(q -> {
            Platform.runLater(() -> {
                events.add(q);
                loadEvents();
            });
        });

        deleteImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/delete.png")),
                20, 20, true, true);

        copyImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/copy.png")),
                20, 20, true, true);

        downloadImg = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/images/download.png")),
                20, 20, true, true);

        loadOrder();
        loadEvents();
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

        orderButton.getItems().clear();
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
     * Stops the thread
     */
    public void stop(){
        serverUtils.stop();
    }

    /**
     * Loads the events and displays them on the page
     */
    public void loadEvents() {
        copyConfirmation.setOpacity(0);

        if (currentOrder.equals("title")) {
            events.sort(Comparator.comparing(EventOverviewDto::getTitle));
        } else if (currentOrder.equals("creation")) {
            events.sort(Comparator.comparing(EventOverviewDto::getCreationDate));
        } else if (currentOrder.equals("last modified")) {
            events.sort(Comparator.comparing(EventOverviewDto::getLastModifiedDate).reversed());
        } else {
            throw new RuntimeException("Impossible ordering");
        }

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

            Text dateText = (Text) currentNode.lookup("#dateText");
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String creationTime = sdf.format(events.get(i).getCreationDate());
            String lastModifiedTime = sdf.format(events.get(i).getLastModifiedDate());
            dateText.setText("Creation: " + creationTime + " Last Modified: " + lastModifiedTime);

            Button downloadBtn = (Button) currentNode.lookup("#downloadButton");
            downloadBtn.setGraphic(new ImageView(downloadImg));
            downloadBtn.setOnAction(e -> downloadJSON(event.getId()));

            Button copyBtn=(Button) currentNode.lookup("#jsonBtn");
            copyBtn.setGraphic(new ImageView(copyImg));
            copyBtn.setOnAction(e -> getJSON(event.getId()));

            Button deleteBtn= (Button) currentNode.lookup("#deleteBtn");
            deleteBtn.setGraphic(new ImageView(deleteImg));
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

    /**
     * Download the JSON
     * @param id The id of the json
     */
    private void downloadJSON(long id) {

        try {
            URL url = new URL(config.getProperty("serverURL") + "/api/events/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine = "";
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                String messageJSON = response.toString();

                String fileName = "JSON_dump_id_#" + id + ".json";

                String filePath = "client/src/main/resources/json/" + fileName;

                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(messageJSON);
                    copyConfirmation.setText("JSON OF EVENT #" + id + " DOWNLOADED!");
                    copyConfirmation.setOpacity(1);
                } catch (IOException e) {
                    System.out.println("Error writing JSON file: " + e.getMessage());
                }

            } else {
                System.out.println("Error dumping database: " + con.getResponseMessage());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes the event
     * @param id the id of the event
     */
    private void deleteEvent(long id) throws IOException, URISyntaxException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(adminMainCtrl.lang.getString("admin_delete_alert_header"));
        alert.setContentText(adminMainCtrl.lang.getString("admin_delete_alert_content"));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    serverUtils.deleteEvent(id);
                    refresh();
                } catch (IOException | URISyntaxException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
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
                String messageJSON = response.toString();

                content.putString(messageJSON);
                clipboard.setContent(content);

                copyConfirmation.setText("JSON OF EVENT #" + id + " COPIED TO CLIPBOARD!");
                copyConfirmation.setOpacity(1);
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
        copyConfirmation.setOpacity(0);

        events.clear();

        List<EventOverviewDto> updatedEvents = serverUtils.getAllEvents();

        events.addAll(updatedEvents);

        loadEvents();
    }

    /**
     * Shows the restoring page for an admin to restore the JSON dump of an event
     */
    public void showRestore(){
        adminMainCtrl.showRestore();
    }

}
