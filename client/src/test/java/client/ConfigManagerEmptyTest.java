package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigManagerEmptyTest {
    private static final String TEST_CONFIG_PATH = "src/test/resources/testConfigEmpty.properties";

    @BeforeEach
        // We create a new test file before each test
    void setup() {
        try{
            File testFile = new File(TEST_CONFIG_PATH);
            if(testFile.exists()){
                testFile.delete();
            }
            testFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void loadConfigNullTest() {
        try(FileWriter writer = new FileWriter(TEST_CONFIG_PATH)){
            writer.write("");
        }catch(IOException e){
            e.printStackTrace();
        }
        ConfigManager configManager = new ConfigManager(TEST_CONFIG_PATH);
        configManager.loadConfig();
        assertNull(configManager.getProperty("property1"));
        assertNull(configManager.getProperty("property2"));
        assertEquals(0, configManager.getPropertyNames().size());
    }
}
