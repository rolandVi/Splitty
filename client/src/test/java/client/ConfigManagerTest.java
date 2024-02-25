package client;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    private static final String TEST_CONFIG_PATH = "src/test/resources/testConfig.properties";

    private ConfigManager configManager;

    @BeforeEach
    // We create a new test file before each test
    void setup(){
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
    void getPropertyTest() {
        try(FileWriter writer = new FileWriter(TEST_CONFIG_PATH);){
            writer.write("property1=value1\nproperty2=value2");
        }catch(IOException e){
            e.printStackTrace();
        }

        configManager = new ConfigManager(TEST_CONFIG_PATH);

        assertEquals("value1", configManager.getProperty("property1"));
        assertEquals("value2", configManager.getProperty("property2"));
        assertNull(configManager.getProperty("property3"));
    }

    @Test
    void setPropertyTest() {
        configManager = new ConfigManager(TEST_CONFIG_PATH);

        configManager.setProperty("property1", "value1");
        configManager.setProperty("property2", "value2");
        assertEquals("value1", configManager.getProperty("property1"));
        assertEquals("value2", configManager.getProperty("property2"));

        configManager.setProperty("property1", "value3");
        assertEquals("value3", configManager.getProperty("property1"));
    }

    @Test
    void getPropertyNamesTest() {
        configManager = new ConfigManager(TEST_CONFIG_PATH);

        Set<String> expected = new HashSet<>();
        expected.add("property1");
        expected.add("property2");
        expected.add("property3");

        configManager.setProperty("property1", "value1");
        configManager.setProperty("property2", "value2");
        configManager.setProperty("property3", "value3");

        assertEquals(3, expected.size());
        assertTrue(expected.containsAll(List.of("property1", "property2", "property3")));
    }

    @Test
    void loadConfigTest() {
        configManager = new ConfigManager(TEST_CONFIG_PATH);

        try(FileWriter writer = new FileWriter(TEST_CONFIG_PATH);){
            writer.write("property1=value1\nproperty2=value2");
        }catch(IOException e){
            e.printStackTrace();
        }

        assertNull(configManager.getProperty("property1"));
        assertNull(configManager.getProperty("property2"));
        assertEquals(0, configManager.getPropertyNames().size());

        configManager.loadConfig();

        assertEquals(2, configManager.getPropertyNames().size());
        assertEquals("value1", configManager.getProperty("property1"));
        assertEquals("value2", configManager.getProperty("property2"));
    }

    @Test
    void saveConfigTest() {
        configManager = new ConfigManager(TEST_CONFIG_PATH);

        configManager.setProperty("property1", "value1");
        configManager.setProperty("property2", "value2");

        // Had to reset the file, as setProperty already has a call to saveConfig inside
        try{
            File testFile = new File(TEST_CONFIG_PATH);
            if(testFile.exists()){
                testFile.delete();
            }
            testFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        try(Scanner scanner = new Scanner(new File(TEST_CONFIG_PATH))){
            assertFalse(scanner.hasNext());
        }catch(IOException e){
            e.printStackTrace();
        }

        configManager.saveConfig();

        try(Scanner scanner = new Scanner(new File(TEST_CONFIG_PATH))){
            // These four lines skip the initial 2 lines of the config file,
            // containing the comment on the file and the date/time when the file has been updated
            assertTrue(scanner.hasNextLine());
            scanner.nextLine();
            assertTrue(scanner.hasNextLine());
            scanner.nextLine();

            assertTrue(scanner.hasNext());
            assertEquals("property1=value1", scanner.next());
            assertTrue(scanner.hasNext());
            assertEquals("property2=value2", scanner.next());
            assertFalse(scanner.hasNext());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}