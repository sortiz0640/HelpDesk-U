package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import java.io.IOException;
import java.util.ArrayList;

public class GestorSesion {

    private Usuario usuarioActual;

    public GestorSesion() {
        this.usuarioActual = null;
    }

    public boolean iniciarSesion(String correo, String password, ArrayList<Usuario> usuarios) {
        // Buscar el usuario en la lista proporcionada
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo) && u.getPassword().equals(password)) {
                usuarioActual = u;
                return true;
            }
        }

        return false;
    }

    public void cerrarSesion() throws IOException {
        usuarioActual = null;
    }

    public String obtenerCorreo() {
        if (usuarioActual == null) {
            return null;
        }
        return usuarioActual.getCorreo();
    }

    public boolean esSesionActiva() {
        return usuarioActual != null;
    }

    public boolean tienePermisosAdmin() {
        if (!esSesionActiva()) {
            return false;
        }
        return usuarioActual.getRol() == ListaRoles.ADMIN;
    }

    public Usuario obtenerUsuarioActual() {
        return usuarioActual;
    }
}