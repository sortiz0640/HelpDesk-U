package cr.ac.ucenfotec.sortiz0640.bl.util;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DataAccessObject<Object> {

    public abstract boolean agregar(Object Entidad) throws SQLException, NoSuchAlgorithmException;

    public abstract boolean eliminar(String identificador) throws SQLException;

    public abstract Object buscar(String identificador) throws SQLException;

    public abstract ArrayList<Object> obtenerTodos() throws SQLException;

    public abstract boolean existe(String identificador) throws SQLException;

}
