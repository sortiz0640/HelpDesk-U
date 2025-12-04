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

    public DepartamentoDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }

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

    @Override
    public boolean eliminar(String correo) throws SQLException {

        if (!existe(correo)) {
            return false;
        }

        String query  = "DELETE FROM departamentos WHERE correo = '" + correo + "'";
        DATA_ACCESS.ejecutar(query);

        return true;
    }

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

    @Override
    public boolean existe(String correo) throws SQLException {
        String query = "SELECT 1 FROM departamentos WHERE correo = '" + correo + "'";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);
        return res.next();
    }

    public boolean existenDepartamentos() throws SQLException {
        return !obtenerTodos().isEmpty();
    }

}

