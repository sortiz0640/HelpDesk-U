package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data Access Object (DAO) para la gestión de categorías y palabras clave.
 * Se utiliza para recuperar las listas de palabras detonantes técnicas y emocionales
 * desde la base de datos para el módulo de análisis.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class CategoriasDAO {

    private AccessDB DATA_ACCESS;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param driver Driver de la base de datos (e.g., "com.mysql.cj.jdbc.Driver").
     * @param url URL de conexión a la base de datos.
     * @param username Nombre de usuario para la conexión.
     * @param password Contraseña para la conexión.
     * @throws SQLException Si ocurre un error de conexión SQL.
     * @throws ClassNotFoundException Si no se encuentra el driver.
     */
    public CategoriasDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }


    /**
     * Recupera todas las palabras clave técnicas y las organiza por su categoría.
     *
     * @return Un HashMap donde la clave es el nombre de la categoría (String)
     * y el valor es una lista de palabras clave (ArrayList<String>) pertenecientes a esa categoría.
     * @throws SQLException Si ocurre un error al ejecutar la consulta en la base de datos.
     */
    public HashMap<String, ArrayList<String>> obtenerCategoriasTecnicas() throws SQLException {

        HashMap<String, ArrayList<String>> palabrasTecnicas = new HashMap<>();
        String query = "SELECT pt.palabra, c.categoria FROM palabras_tecnicas pt " + "INNER JOIN categorias c ON pt.idCategoria = c.idCategoria";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {

            String palabra = res.getString("palabra");
            String categoria = res.getString("categoria");

            if (!palabrasTecnicas.containsKey(categoria)) {
                palabrasTecnicas.put(categoria, new ArrayList<>());
            }

            palabrasTecnicas.get(categoria).add(palabra);
        }

        return palabrasTecnicas;
    }

    /**
     * Recupera todas las palabras clave emocionales y las organiza por su tipo de emoción.
     *
     * @return Un HashMap donde la clave es el tipo de emoción (String)
     * y el valor es una lista de palabras clave (ArrayList<String>) asociadas a esa emoción.
     * @throws SQLException Si ocurre un error al ejecutar la consulta en la base de datos.
     */
    public HashMap<String, ArrayList<String>> obtenerCategoriasEmocionales() throws SQLException {

        HashMap<String, ArrayList<String>> palabrasEmocionales = new HashMap<>();
        String query = "SELECT palabra, tipo_emocion FROM palabras_emociones";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {

            String palabra = res.getString("palabra");
            String categoria = res.getString("tipo_emocion");

            if (!palabrasEmocionales.containsKey(categoria)) {
                palabrasEmocionales.put(categoria, new ArrayList<>());
            }

            palabrasEmocionales.get(categoria).add(palabra);
        }

        return palabrasEmocionales;
    }
}