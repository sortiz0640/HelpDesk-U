package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class Conexion {

    private static Properties props = DatabaseConfig.getProperties();
    private static Connection conexion = null;

    private Conexion() {
    }

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe una conexión activa, crea una nueva.
     *
     * @return Objeto Connection activo
     * @throws SQLException si no se puede establecer la conexión
     */

    public static Connection getConexion() throws SQLException {

        String URL = props.getProperty("db.url");
        String USUARIO = props.getProperty("db.user");
        String PASSWORD = props.getProperty("db.password");

        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");

            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver de MySQL no encontrado: " + e.getMessage());
            } catch (SQLException e) {
                throw new SQLException("Error al conectar a la base de datos: " + e.getMessage());
            }
        }

        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos si está activa.
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada exitosamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica si la conexión está activa.
     *
     * @return true si la conexión está activa, false en caso contrario
     */
    public static boolean estaConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}