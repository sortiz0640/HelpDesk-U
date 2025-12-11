package cr.ac.ucenfotec.sortiz0640.bl.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para leer configuraciones desde un archivo .properties.
 * Se encarga de cargar y proveer acceso a credenciales y parámetros de la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class ConfigPropertiesReader {

    private Properties properties;
    private String FILE_PATH = "C:\\Users\\sorti\\OneDrive\\Escritorio\\HelpDeskU Sebastian Ortiz\\HelpDeskU Project\\HelpDeskU\\src\\cr\\ac\\ucenfotec\\sortiz0640\\config.properties";

    /**
     * Constructor que carga el archivo de propiedades definido en la ruta.
     * Si falla la lectura, imprime el error en la consola.
     */
    public ConfigPropertiesReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("[ERR]" + e.getMessage());
        }
    }

    /**
     * Obtiene la URL de conexión a la base de datos.
     * @return String con la URL JDBC.
     */
    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    /**
     * Obtiene el usuario de la base de datos.
     * @return String con el nombre de usuario.
     */
    public String getDbUser() {
        return properties.getProperty("db.user");
    }

    /**
     * Obtiene la contraseña de la base de datos.
     * @return String con la contraseña.
     */
    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    /**
     * Obtiene el nombre del driver JDBC a utilizar.
     * @return String con el nombre de la clase del driver.
     */
    public String getDbDriver() {
        return properties.getProperty("db.driver");
    }

}