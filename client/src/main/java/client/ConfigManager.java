package client;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class ConfigManager {

    private String configFilePath;
//    private final String CONFIG_FILE_PATH="client/src/main/resources/config.properties";

//    private ConfigManager instance;

    private Properties properties;

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
     * Loads the contents of the config file into the properties object
     */
    public void loadConfig(){
        try (FileReader reader = new FileReader(configFilePath)){
            properties.load(reader);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Writes the contents of the properties object into the config file
     */
    public void saveConfig(){
        try (FileWriter writer = new FileWriter(configFilePath)){
            properties.store(writer, "Application configuration");
        }catch (IOException e){
            e.printStackTrace();
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
