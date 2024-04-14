package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.nio.file.Path;
import java.util.Objects;

public class CustomTagMainCtrl {

    private Stage customTagStage;
    private Scene customTagScene;
    private CustomTagCtrl customTagCtrl;

    /**
     * the initialization of the scene of customtag
     * @param customTagStage the stage for custom tags
     * @param customTagScene the scene for custom tags
     */
    public void initialize(Stage customTagStage, Pair<CustomTagCtrl, Parent> customTagScene){
        this.customTagStage =customTagStage;
        this.customTagScene = new Scene(customTagScene.getValue());
        this.customTagCtrl= customTagScene.getKey();

        this.customTagScene.getStylesheets().add(
                Objects.requireNonNull(this.getClass().getClassLoader()
                                .getResource(Path.of("stylesheets",
                                        "customTag.css").toString()))
                        .toExternalForm());


        showCustomTag();
        customTagStage.show();
    }

    /**
     * shows the custom tag scene
     */
    public void showCustomTag(){
        customTagStage.setTitle("custom tag");
        customTagStage.setScene(customTagScene);
    }
}
