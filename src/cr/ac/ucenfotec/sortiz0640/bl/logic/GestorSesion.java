package cr.ac.ucenfotec.sortiz0640.bl.logic;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Gestor de sesión del sistema HelpDesk.
 * Administra la autenticación de usuarios y mantiene el estado de la sesión actual.
 * Controla quién está logueado y sus permisos basados en el rol.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class GestorSesion {

    private Usuario usuarioActual;

    /**
     * Constructor que inicializa el gestor sin usuario logueado.
     */

    public GestorSesion() {
        this.usuarioActual = null;
    }

    /**
     * Intenta iniciar sesión en el sistema con las credenciales proporcionadas.
     * Busca el usuario en la lista y valida correo y contraseña.
     *
     * @param correo Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @param usuarios Lista de usuarios registrados en el sistema
     * @return true si las credenciales son válidas y la sesión se inició correctamente,
     *         false si las credenciales son incorrectas
     */

    public boolean iniciarSesion(String correo, String password, ArrayList<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo)) {
                BCrypt.Result result = BCrypt.verifyer().verify(
                        password.toCharArray(),
                        u.getPassword()
                );
                if (result.verified) {
                    usuarioActual = u;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cierra la sesión actual del usuario.
     * Elimina la referencia al usuario logueado.
     *
     * @throws IOException Si ocurre un error al cerrar la sesión
     */

    public void cerrarSesion() throws IOException {
        usuarioActual = null;
    }

    /**
     * Obtiene el correo electrónico del usuario actualmente logueado.
     *
     * @return Correo del usuario actual, o null si no hay sesión activa
     */

    public String obtenerCorreo() {
        if (usuarioActual == null) {
            return null;
        }
        return usuarioActual.getCorreo();
    }

    /**
     * Verifica si hay una sesión activa en el sistema.
     *
     * @return true si hay un usuario logueado, false en caso contrario
     */

    public boolean esSesionActiva() {
        return usuarioActual != null;
    }

    /**
     * Verifica si el usuario actual tiene permisos de administrador.
     *
     * @return true si el usuario logueado tiene rol ADMIN, false en caso contrario
     *         o si no hay sesión activa
     */

    public boolean tienePermisosAdmin() {
        if (!esSesionActiva()) {
            return false;
        }
        return usuarioActual.getRol() == ListaRoles.ADMIN;
    }

    /**
     * Obtiene el objeto Usuario completo del usuario actualmente logueado.
     *
     * @return Objeto Usuario de la sesión actual, o null si no hay sesión activa
     */

    public Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }
}