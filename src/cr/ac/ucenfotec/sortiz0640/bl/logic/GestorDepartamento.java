package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.dl.DepartamentoDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de departamentos.
 * Trabaja exclusivamente con la entidad Departamento.
 * No tiene dependencias de otros gestores.
 *
 * @author Sebastian Ortiz
 * @version 2.0
 * @since 2025
 */
public class GestorDepartamento {

    private DepartamentoDAO db;
    ConfigPropertiesReader config;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     *
     * @throws SQLException Si ocurre un error de conexión con la base de datos.
     * @throws ClassNotFoundException Si no se encuentra el driver de la base de datos.
     */
    public GestorDepartamento() throws SQLException, ClassNotFoundException {
        config = new ConfigPropertiesReader();
        db = new DepartamentoDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    /**
     * Agrega un nuevo departamento al sistema.
     *
     * @param nombre Nombre del departamento.
     * @param descripcion Descripción de las funciones del departamento.
     * @param correo Correo electrónico único del departamento.
     * @return Mensaje indicando si se agregó correctamente o si hubo un error (duplicado).
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public String agregar(String nombre, String descripcion, String correo) throws SQLException {
        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean resultado = db.agregar(tmpDepartamento);

        if (!resultado) {
            return "[ERR] El correo/nombre del departamento ya se encuentra registrado";
        }

        return "[INFO] Departamento agregado correctamente";
    }

    /**
     * Elimina un departamento del sistema utilizando su correo electrónico.
     *
     * @param correo Correo del departamento a eliminar.
     * @return Mensaje indicando si se eliminó correctamente o si el departamento no existía.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public String eliminarPorCorreo(String correo) throws SQLException {
        boolean resultado = db.eliminar(correo);

        if (!resultado) {
            return "[ERR] El departamento especificado no existe";
        }

        return "[INFO] Se ha eliminado el departamento correctamente";
    }

    /**
     * Verifica si existe un departamento registrado con el correo proporcionado.
     *
     * @param correo Correo a verificar.
     * @return true si el departamento existe, false en caso contrario.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public boolean existePorCorreo(String correo) throws SQLException {
        return db.existe(correo);
    }

    /**
     * Busca y recupera un departamento específico por su correo.
     *
     * @param correo Correo del departamento a buscar.
     * @return Objeto Departamento con sus datos, o null si no se encuentra.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public Departamento buscarPorCorreo(String correo) throws SQLException {
        return db.buscar(correo);
    }

    /**
     * Obtiene una lista con todos los objetos Departamento registrados en el sistema.
     *
     * @return ArrayList conteniendo todos los departamentos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public ArrayList<Departamento> obtenerDepartamentos() throws SQLException {
        return db.obtenerTodos();
    }

    /**
     * Obtiene una lista simple con solo los correos electrónicos de todos los departamentos.
     * Útil para poblar listas desplegables o validaciones rápidas.
     *
     * @return ArrayList de Strings con los correos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public ArrayList<String> obtenerCorreosDepartamentos() throws SQLException {
        ArrayList<String> correos = new ArrayList<>();
        for (Departamento d: obtenerDepartamentos()) {
            correos.add(d.getCorreo());
        }
        return correos;
    }

    /**
     * Obtiene los detalles de un departamento específico en un formato de arreglo de texto.
     *
     * @param correoDepartamento Correo del departamento.
     * @return Arreglo de Strings con [Nombre, Correo, Descripción], o null si no existe.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */

    public String[] obtenerDetallesDepartamento(String correoDepartamento) throws SQLException {
        Departamento d = buscarPorCorreo(correoDepartamento);

        if (d == null) {
            return null;
        }

        return new String[]{
                d.getNombre(),
                d.getCorreo(),
                d.getDescripcion(),
        };
    }

    /**
     * Obtiene la información de todos los departamentos formateada para mostrar en tablas.
     *
     * @return ArrayList de arreglos de Strings, donde cada arreglo representa una fila de datos.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() throws SQLException {
        ArrayList<Departamento> departamentos = obtenerDepartamentos();
        ArrayList<String[]> resultado = new ArrayList<>();

        if (departamentos == null || departamentos.isEmpty()) {
            return resultado;
        }

        for (Departamento d : departamentos) {
            String[] datos = {
                    d.getNombre(),
                    d.getCorreo(),
                    d.getDescripcion(),
            };
            resultado.add(datos);
        }

        return resultado;
    }
}