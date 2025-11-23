package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import java.util.ArrayList;

/**
 * Capa de acceso a datos para la gestión de usuarios.
 * Administra el almacenamiento en memoria de los usuarios del sistema mediante ArrayList.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para usuarios.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class DataUsuario {

    private ArrayList<Usuario> listaUsuarios;

    /**
     * Constructor que inicializa la lista de usuarios vacía.
     */

    public DataUsuario() {
        this.listaUsuarios = new ArrayList<>();
    }

    /**
     * Agrega un nuevo usuario al almacenamiento.
     * Valida que no exista otro usuario con el mismo correo electrónico.
     *
     * @param usuario Objeto Usuario a agregar
     * @return true si el usuario fue agregado exitosamente,
     *         false si ya existe un usuario con el mismo correo
     */

    public boolean agregar(Usuario usuario) {
        // Verificar si ya existe un usuario con el mismo correo
        for (Usuario u : listaUsuarios) {
            if (u.getCorreo().equals(usuario.getCorreo())) {
                return false;
            }
        }

        listaUsuarios.add(usuario);
        return true;
    }

    /**
     * Elimina un usuario del almacenamiento por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a eliminar
     * @return true si el usuario fue eliminado exitosamente,
     *         false si no se encontró un usuario con ese correo
     */

    public boolean eliminarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                listaUsuarios.remove(usuario);
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene la representación en texto de un usuario por su correo.
     *
     * @param correo Correo electrónico del usuario a buscar
     * @return String con la representación del usuario (toString()),
     *         o null si no se encuentra el usuario
     */

    public String listarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario.toString();
            }
        }
        return null;
    }

    /**
     * Obtiene una lista con la representación en texto de todos los usuarios.
     *
     * @return ArrayList con el toString() de cada usuario almacenado
     */

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            lista.add(usuario.toString());
        }

        return lista;
    }

    /**
     * Busca y retorna un objeto Usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a buscar
     * @return Objeto Usuario si se encuentra, null en caso contrario
     */

    public Usuario buscarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Verifica si existe un usuario con el correo especificado.
     *
     * @param correo Correo electrónico a verificar
     * @return true si existe un usuario con ese correo, false en caso contrario
     */

    public boolean existePorCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }

    /**
     * Obtiene la lista completa de objetos Usuario almacenados.
     *
     * @return ArrayList con todos los usuarios del sistema
     */

    public ArrayList<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }
}