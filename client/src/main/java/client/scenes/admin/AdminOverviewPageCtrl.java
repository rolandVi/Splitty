package client.scenes.admin;


import client.utils.ServerUtils;
import com.google.inject.Inject;
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
import server.dto.view.EventOverviewDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Comparator;


public class AdminOverviewPageCtrl {

    private final AdminMainCtrl adminMainCtrl;
    private final ServerUtils serverUtils;
    @FXML
    public MenuButton orderButton;

    @FXML
    private VBox eventContainer;

    private Image titleImg;
    private Image lastModifiedImg;
    private Image creationTimeImg;

    private String currentOrder = "title";


    /**
     * Injector for EventOverviewCtrl
     * @param adminMainCtrl the admin controller
     * @param serverUtils   the server utilities
     */
    @Inject
    public AdminOverviewPageCtrl( AdminMainCtrl adminMainCtrl, ServerUtils serverUtils){
        this.adminMainCtrl = adminMainCtrl;
        this.serverUtils=serverUtils;

    }

    /**
     * Loads the order menu
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
        //TODO: get the JSON dump
        clipboard.setContent(content);
    }

    /**
     * Refreshes the page()
     * //todo: remove this when web sockets are implemented
     */
    public void refresh(){
        adminMainCtrl.showAdminOverview();
    }


}
