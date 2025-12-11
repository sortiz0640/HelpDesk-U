package cr.ac.ucenfotec.sortiz0640.bl.util;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase abstracta genérica que define el contrato para los objetos de acceso a datos (DAO).
 * Establece las operaciones CRUD básicas que deben implementar los gestores de persistencia.
 *
 * @param <Object> Tipo de entidad que manejará el DAO.
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public abstract class DataAccessObject<Object> {

    /**
     * Agrega una nueva entidad a la base de datos.
     * @param Entidad Objeto a guardar.
     * @return true si se guardó correctamente, false si hubo conflicto.
     * @throws SQLException Si ocurre error en BD.
     * @throws NoSuchAlgorithmException Si falla encriptación (en caso de usuarios).
     */
    public abstract boolean agregar(Object Entidad) throws SQLException, NoSuchAlgorithmException;

    /**
     * Elimina una entidad basada en su identificador único.
     * @param identificador Clave única (ID o correo).
     * @return true si se eliminó, false si no existía.
     * @throws SQLException Si ocurre error en BD.
     */
    public abstract boolean eliminar(String identificador) throws SQLException;

    /**
     * Busca una entidad por su identificador.
     * @param identificador Clave única.
     * @return La entidad encontrada o null.
     * @throws SQLException Si ocurre error en BD.
     */
    public abstract Object buscar(String identificador) throws SQLException;

    /**
     * Obtiene todas las entidades registradas.
     * @return Lista de todas las entidades.
     * @throws SQLException Si ocurre error en BD.
     */
    public abstract ArrayList<Object> obtenerTodos() throws SQLException;

    /**
     * Verifica si una entidad existe en la base de datos.
     * @param identificador Clave única a verificar.
     * @return true si existe, false si no.
     * @throws SQLException Si ocurre error en BD.
     */
    public abstract boolean existe(String identificador) throws SQLException;

}