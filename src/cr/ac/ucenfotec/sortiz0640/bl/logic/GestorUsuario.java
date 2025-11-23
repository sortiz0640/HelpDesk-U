package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.dl.DataUsuario;
import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de usuarios.
 * Maneja todas las operaciones relacionadas con usuarios del sistema,
 * incluyendo creación, eliminación, búsqueda y listado.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class GestorUsuario {

    private DataUsuario db;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     */

    public GestorUsuario() {
        db = new DataUsuario();
    }

    /**
     * Agrega un nuevo usuario al sistema.
     * Valida que el rol sea válido y que el correo no esté registrado previamente.
     *
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico único del usuario
     * @param password Contraseña de acceso
     * @param rolEntrada Código del rol (1: ADMIN, 2: ESTUDIANTE, 3: FUNCIONARIO)
     * @return Mensaje indicando éxito o error en la operación
     */

    public String agregar(String nombre, String apellidos, String correo, String password, int rolEntrada) {
        ListaRoles rol = switch (rolEntrada) {
            case 1 -> ListaRoles.ADMIN;
            case 2 -> ListaRoles.ESTUDIANTE;
            case 3 -> ListaRoles.FUNCIONARIO;
            default -> null;
        };

        if (rol == null) {
            return "[ERR] Rol inválido";
        }

        Usuario tmpUsuario = new Usuario(nombre, apellidos, correo, password, rol);
        boolean resultado = db.agregar(tmpUsuario);

        if (!resultado) {
            return "[ERR] El correo del usuario ya se encuentra registrado!";
        }

        return "[INFO] Usuario agregado correctamente";
    }

    /**
     * Elimina un usuario del sistema por su correo electrónico.
     *
     * @param correo Correo del usuario a eliminar
     * @return Mensaje indicando éxito o error en la operación
     */

    public String eliminarPorCorreo(String correo) {
        boolean resultado = db.eliminarPorCorreo(correo);

        if (!resultado) {
            return "[ERR] El usuario especificado no existe";
        }

        return "[INFO] Usuario eliminado correctamente";
    }

    /**
     * Obtiene la información de un usuario específico por su correo.
     *
     * @param correo Correo del usuario a buscar
     * @return Representación en texto del usuario o mensaje de error si no existe
     */

    public String listarPorCorreo(String correo) {
        String resultado = db.listarPorCorreo(correo);

        if (resultado == null) {
            return "[ERR] El usuario especificado no existe";
        }

        return resultado;
    }

    /**
     * Obtiene una lista con todos los usuarios registrados en el sistema.
     *
     * @return ArrayList con la representación en texto de todos los usuarios
     */

    public ArrayList<String> listarTodos() {
        return db.listarTodos();
    }

    /**
     * Busca y retorna un objeto Usuario por su correo electrónico.
     *
     * @param correo Correo del usuario a buscar
     * @return Objeto Usuario si existe, null si no se encuentra
     */

    public Usuario buscarPorCorreo(String correo) {
        return db.buscarPorCorreo(correo);
    }

    /**
     * Verifica si existe un usuario con el correo especificado.
     *
     * @param correo Correo a verificar
     * @return true si el usuario existe, false en caso contrario
     */

    public boolean existePorCorreo(String correo) {
        return db.existePorCorreo(correo);
    }

    /**
     * Obtiene la lista completa de objetos Usuario almacenados.
     *
     * @return ArrayList con todos los usuarios del sistema
     */

    public ArrayList<Usuario> obtenerUsuarios() {
        return db.obtenerUsuarios();
    }
}