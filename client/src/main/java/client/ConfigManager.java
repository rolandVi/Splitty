package client;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class ConfigManager {

    private final String configFilePath;
//    private final String CONFIG_FILE_PATH="client/src/main/resources/config.properties";

//    private ConfigManager instance;

    private final Properties properties;

//    public ConfigManager getInstance(){
//        if (instance==null){
//            instance=new ConfigManager(CONFIG_FILE_PATH);
//        }
//
//        return this.instance;
//    }

    /**
     * default constructor
     * @param configFilePath - path to the application's config file
     */
    public ConfigManager(String configFilePath){
        properties = new Properties();
        this.configFilePath = configFilePath;
        loadConfig();
    }

    /**
     * Checks if the config file exists or not
     * @param configFilePath relative path to the config file
     *                      with project folder as working directory
     * @return boolean value indicating if config file exists
     */
    public static boolean configFileExists(String configFilePath) {
        File config = new File(configFilePath);
        return config.exists();
    }

    /**
     * Creates a new config file to save locale and user info (incl. ID)
     * @param configFilePath path to where the new config file should be created
     */
    public static void createConfig(String configFilePath) {
        Properties properties = new Properties();
        // Required properties
        properties.setProperty("country", "GB");
        properties.setProperty("language", "en");
        properties.setProperty("userID", "1");
        properties.setProperty("serverURL", "http://localhost:8080");
        // When storing properties, they will be stored in alphabetical order based on key
        try (FileWriter writer = new FileWriter(configFilePath)){
            properties.store(writer, "Application configuration");
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    /**
     * Loads the contents of the config file into the properties object
     */
    public void loadConfig(){
        try (FileReader fileReader = new FileReader(configFilePath)) {
            properties.load(fileReader);
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    /**
     * Writes the contents of the properties object into the config file
     */
    public void saveConfig(){
        try (FileWriter writer = new FileWriter(configFilePath)){
            properties.store(writer, "Application configuration");
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    /**
     * Getter for value of the specific properties
     * @param key - name of the property
     * @return - the value of the property
     */
    public String getProperty(String key){
        return properties.getProperty(key);
    }

    /**
     * Setter for the properties
     * @param key - name of the property
     * @param value - new value of the property
     * @return - old value of the property
     */
    public String setProperty(String key, String value){
        String oldValue = properties.getProperty(key);
        properties.setProperty(key, value);
        saveConfig();
        return oldValue;
    }

    /**
     * @return - a Set object containing names of all the properties present in the config file
     */
    public Set<String> getPropertyNames(){
        return properties.stringPropertyNames();
    }
}
