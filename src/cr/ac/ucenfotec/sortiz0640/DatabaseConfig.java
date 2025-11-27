package cr.ac.ucenfotec.sortiz0640;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {

    private static final String FILE_PATH = "src/cr/ac/ucenfotec/sortiz0640/config.properties";

    public static Properties getProperties() {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream(FILE_PATH)) {
            props.load(input);
        } catch (IOException e) {
            System.out.println("[ERR] " + e.getMessage());
        }

        return props;
    }
}
