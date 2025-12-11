package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.util.DataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Departamento.
 * Gestiona todas las operaciones CRUD de departamentos en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class DepartamentoDAO extends DataAccessObject<Departamento> {

    private AccessDB DATA_ACCESS;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param driver Driver de la base de datos.
     * @param url URL de conexión.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @throws SQLException Si ocurre un error de conexión SQL.
     * @throws ClassNotFoundException Si no se encuentra el driver.
     */
    public DepartamentoDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }

    /**
     * Agrega un nuevo departamento a la tabla de departamentos.
     *
     * @param departamento El objeto Departamento a agregar.
     * @return true si el departamento se agregó, false si ya existía.
     * @throws SQLException Si ocurre un error al ejecutar la inserción.
     */
    @Override
    public boolean agregar(Departamento departamento) throws SQLException {

        if (existe(departamento.getCorreo())) {
            return false;
        }

        String query = "INSERT INTO departamentos (nombre, descripcion, correo) " +
                "VALUES ('" + departamento.getNombre() + "', " +
                "'" + departamento.getDescripcion() + "', " +
                "'" + departamento.getCorreo() + "')";

        DATA_ACCESS.ejecutar(query);
        return true;
    }

    /**
     * Elimina un departamento de la base de datos por su correo.
     *
     * @param correo Correo del departamento a eliminar.
     * @return true si el departamento fue eliminado, false si no existía.
     * @throws SQLException Si ocurre un error al ejecutar la eliminación.
     */
    @Override
    public boolean eliminar(String correo) throws SQLException {

        if (!existe(correo)) {
            return false;
        }

        String query  = "DELETE FROM departamentos WHERE correo = '" + correo + "'";
        DATA_ACCESS.ejecutar(query);

        return true;
    }

    /**
     * Busca y recupera un objeto Departamento por su correo.
     *
     * @param correo Correo del departamento a buscar.
     * @return El objeto Departamento si se encuentra, o null en caso contrario.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public Departamento buscar(String correo) throws SQLException {

        Departamento departamento = new Departamento();
        String query = "SELECT * FROM departamentos WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        if (res.next()) {
            departamento.setNombre(res.getString("nombre"));
            departamento.setDescripcion(res.getString("descripcion"));
            departamento.setCorreo(res.getString("correo"));
            return departamento;
        }
        return null;
    }

    /**
     * Obtiene una lista con todos los departamentos registrados en la base de datos.
     *
     * @return ArrayList de objetos Departamento.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public ArrayList<Departamento> obtenerTodos() throws SQLException {

        ArrayList<Departamento> departamentos = new ArrayList<>();
        String query = "SELECT * FROM departamentos";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {

            Departamento departamento = new Departamento();
            departamento.setNombre(res.getString("nombre"));
            departamento.setDescripcion(res.getString("descripcion"));
            departamento.setCorreo(res.getString("correo"));
            departamentos.add(departamento);
        }

        return departamentos;
    }

    /**
     * Verifica la existencia de un departamento por su correo.
     *
     * @param correo Correo del departamento a verificar.
     * @return true si el departamento existe, false si no.
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public boolean existe(String correo) throws SQLException {
        String query = "SELECT 1 FROM departamentos WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }

    /**
     * Verifica si existen departamentos registrados en el sistema.
     *
     * @return true si la lista de departamentos no está vacía, false si no hay departamentos.
     * @throws SQLException Si ocurre un error al obtener todos los departamentos.
     */
    public boolean existenDepartamentos() throws SQLException {
        return !obtenerTodos().isEmpty();
    }

}