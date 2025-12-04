package cr.ac.ucenfotec.sortiz0640.bl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesReader {

    private Properties properties;
    private String FILE_PATH = "C:\\Users\\sorti\\OneDrive\\Escritorio\\HelpDeskU Sebastian Ortiz\\HelpDeskU Project\\HelpDeskU\\src\\cr\\ac\\ucenfotec\\sortiz0640\\config.properties";

    public ConfigPropertiesReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("[ERR]" + e.getMessage());
        }
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUser() {
        return properties.getProperty("db.user");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getDbDriver() {
        return properties.getProperty("db.driver");
    }

}
