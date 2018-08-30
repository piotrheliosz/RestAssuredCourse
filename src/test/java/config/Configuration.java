package config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static File configFile = new File("config.properties");

    public static String getToken() {
        try {
            FileReader fileReader = new FileReader(configFile);
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
