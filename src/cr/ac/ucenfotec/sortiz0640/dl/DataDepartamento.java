package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la gestión de departamentos.
 * Administra el almacenamiento en memoria de los departamentos del sistema mediante ArrayList.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para departamentos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class DataDepartamento {

    private ArrayList<Departamento> listaDepartamentos;

    /**
     * Constructor que inicializa la lista de departamentos vacía.
     */

    public DataDepartamento() {
        this.listaDepartamentos = new ArrayList<>();
    }

    /**
     * Agrega un nuevo departamento al almacenamiento.
     * Valida que no exista otro departamento con el mismo correo o nombre.
     *
     * @param departamento Objeto Departamento a agregar
     * @return true si el departamento fue agregado exitosamente,
     *         false si ya existe un departamento con el mismo correo o nombre
     */

    public boolean agregar(Departamento departamento) {
        // Verificar si ya existe un departamento con el mismo correo o nombre
        for (Departamento d : listaDepartamentos) {
            if (d.getCorreo().equals(departamento.getCorreo()) ||
                    d.getNombre().equals(departamento.getNombre())) {
                return false;
            }
        }

        listaDepartamentos.add(departamento);
        return true;
    }

    /**
     * Elimina un departamento del almacenamiento por su correo electrónico.
     *
     * @param correo Correo electrónico del departamento a eliminar
     * @return true si el departamento fue eliminado exitosamente,
     *         false si no se encontró un departamento con ese correo
     */

    public boolean eliminarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                listaDepartamentos.remove(departamento);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si existe un departamento con el correo especificado.
     *
     * @param correo Correo electrónico a verificar
     * @return true si existe un departamento con ese correo, false en caso contrario
     */

    public boolean existePorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la representación en texto de un departamento por su correo.
     *
     * @param correo Correo electrónico del departamento a buscar
     * @return String con la representación del departamento (toString()),
     *         o null si no se encuentra el departamento
     */

    public String listarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return departamento.toString();
            }
        }
        return null;
    }

    /**
     * Obtiene una lista con la representación en texto de todos los departamentos.
     *
     * @return ArrayList con el toString() de cada departamento almacenado
     */

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Departamento departamento : listaDepartamentos) {
            lista.add(departamento.toString());
        }

        return lista;
    }

    /**
     * Busca y retorna un objeto Departamento por su correo electrónico.
     *
     * @param correo Correo electrónico del departamento a buscar
     * @return Objeto Departamento si se encuentra, null en caso contrario
     */

    public Departamento buscarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return departamento;
            }
        }
        return null;
    }

    /**
     * Verifica si existen departamentos registrados en el almacenamiento.
     *
     * @return true si hay al menos un departamento, false si la lista está vacía
     */

    public boolean existenDepartamentos() {
        return !listaDepartamentos.isEmpty();
    }

    /**
     * Obtiene la lista completa de objetos Departamento almacenados.
     *
     * @return ArrayList con todos los departamentos del sistema
     */

    public ArrayList<Departamento> obtenerDepartamentos() {
        return listaDepartamentos;
    }
}