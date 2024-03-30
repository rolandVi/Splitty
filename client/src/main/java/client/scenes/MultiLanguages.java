package client.scenes;

import client.ConfigManager;
import client.LanguageCell;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public interface MultiLanguages {
    ConfigManager configManager = new ConfigManager("client/src/main/resources/config.properties");
    String LANGUAGE_PATH = "client/src/main/resources/languages";

    /**
     * A method that changes the content to the right language
     * according to the locale.
     */
    void updateLanguage();

    /**
     * A method that updates the scene.
     * This means the language and possible the language dropbox
     */


    default ArrayList<String> getListOfSupportedLanguages(String path) {
        ArrayList<String> locales = new ArrayList<>();
        // Retrieve array with all the file names in directory found with path
        File directory = new File(path);
        String[] arr = directory.list();

        // If array is null, then there are no files in directory
        // Return empty arraylist
        if (arr == null) return locales;
        for (String s : arr) {
            // Skip over lang.properties, since they are the same as lang_en_GB.properties
            if (!s.contains("lang_")) continue;
            locales.add(s.replace("lang_", "").replace(".properties", ""));
        }

        return locales;
    }

    /**
     * Configures the ComboBox (Dropdown menu)
     * - Add supported languages
     * - Sets the starting value based on config file
     * @param comboBox the dropdown menu to configure
     */
    default void updateLanguageBox(ComboBox<String> comboBox, String language) {
        // Setup cell-factory
        comboBox.setCellFactory(c -> new LanguageCell());
        comboBox.setButtonCell(new LanguageCell());
        // Creates the list of possible languages
        ArrayList<String> locales = getListOfSupportedLanguages(LANGUAGE_PATH);
        // Save it into comboBox
        comboBox.setItems(FXCollections.observableArrayList(locales));
        // Set starting value
        comboBox.setValue(language);
    }

    /**
     * Retrieves locale from config file
     * @return locale (language) saved in config file
     */
    static Locale getLocaleFromConfig() {
        String language = configManager.getProperty("language");
        String country = configManager.getProperty("country");
        return Locale.of(language, country);
    }

    /**
     * Updates the config file with new locale
     * @param language language of locale
     * @param country country of locale
     */
    static void setLocaleFromConfig(String language, String country) {
        configManager.setProperty("language", language);
        configManager.setProperty("country", country);

    }
}
