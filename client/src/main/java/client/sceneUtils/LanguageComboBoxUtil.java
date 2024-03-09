package client.sceneUtils;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

public class LanguageComboBoxUtil {
    private static final Properties config;

    static {
        try {
            config = new Properties();
            config.load(LanguageComboBoxUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public void updateLanguageComboBox(ComboBox<String> comboBox) {
        // Creates the list of possible languages
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("en-GB");
        arrayList.add("nl-NL");
        comboBox.setItems(FXCollections.observableArrayList(arrayList));
        comboBox.setValue(config.getProperty("locale") + "-" + config.getProperty("country"));
    }

    static public Locale getLocaleFromConfig() {
        String language = config.getProperty("locale");
        String country = config.getProperty("country");
        return Locale.of(language, country);
    }

    static public void setLocaleFromConfig(String locale, String country) {
        config.setProperty("locale", locale);
        config.setProperty("country", country);
    }
}
