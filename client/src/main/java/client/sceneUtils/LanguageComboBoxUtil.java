package client.sceneUtils;

import client.ConfigManager;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;


import java.util.ArrayList;
import java.util.Locale;

public class LanguageComboBoxUtil {
    static private final ConfigManager configManager =
            new ConfigManager("client/src/main/resources/config.properties");

    /**
     * Configures the ComboBox (Dropdown menu)
     * - Add supported languages
     * - Sets the starting value based on config file
     * @param comboBox the dropdown menu to configure
     */
    static public void updateLanguageComboBox(ComboBox<String> comboBox) {
        // Creates the list of possible languages
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("en-GB");
        arrayList.add("nl-NL");
        comboBox.setItems(FXCollections.observableArrayList(arrayList));
        comboBox.setValue(configManager.getProperty("language") +
                "-" + configManager.getProperty("country"));
    }

    /**
     * Retrieves locale from config file
     * @return locale (language) saved in config file
     */
    static public Locale getLocaleFromConfig() {
        String language = configManager.getProperty("language");
        String country = configManager.getProperty("country");
        return Locale.of(language, country);
    }

    /**
     * Updates the config file with new locale
     * @param language language of locale
     * @param country country of locale
     */
    static public void setLocaleFromConfig(String language, String country) {
        configManager.setProperty("language", language);
        configManager.setProperty("country", country);
    }
}
