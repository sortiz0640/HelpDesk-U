package cr.ac.ucenfotec.sortiz0640.dl.template;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaz genérica para operaciones CRUD en la capa de acceso a datos.
 * Define los métodos básicos que todas las clases DAO deben implementar.
 *
 * @param <T> Tipo de entidad que maneja el DAO
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public interface IDao<T> {

    /**
     * Inserta una nueva entidad en la base de datos.
     *
     * @param entidad Objeto a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     * @throws SQLException si ocurre un error en la base de datos
     */

    boolean insertar(T entidad) throws SQLException;

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param entidad Objeto con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     * @throws SQLException si ocurre un error en la base de datos
     */

    boolean actualizar(T entidad) throws SQLException;

    /**
     * Elimina una entidad de la base de datos por su identificador.
     *
     * @param id Identificador de la entidad a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     * @throws SQLException si ocurre un error en la base de datos
     */

    boolean eliminar(String id) throws SQLException;

    /**
     * Busca y retorna una entidad por su identificador.
     *
     * @param id Identificador de la entidad a buscar
     * @return Objeto encontrado o null si no existe
     * @throws SQLException si ocurre un error en la base de datos
     */

    T buscarPorId(String id) throws SQLException;

    /**
     * Obtiene todas las entidades de la base de datos.
     *
     * @return Lista con todas las entidades
     * @throws SQLException si ocurre un error en la base de datos
     */

    ArrayList<T> listarTodos() throws SQLException;

    /**
     * Verifica si existe una entidad con el identificador especificado.
     *
     * @param id Identificador a verificar
     * @return true si existe, false en caso contrario
     * @throws SQLException si ocurre un error en la base de datos
     */

    boolean existe(String id) throws SQLException;
}