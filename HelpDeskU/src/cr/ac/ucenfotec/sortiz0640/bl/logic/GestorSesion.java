package cr.ac.ucenfotec.sortiz0640.bl.logic;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import java.io.IOException;

public class GestorSesion {

    private Usuario usuario;
    private GestorUsuario gestorUsuario;

    public GestorSesion(GestorUsuario gestor) {
        gestorUsuario = gestor;
    }

    public boolean iniciarSesion(String correo, String password) {

        for(Usuario u : gestorUsuario.getUsuarios()) {

            if(u.getCorreo().equals(correo) && u.getPassword().equals(password)){
                usuario = u;
                return true;
            }
        }

        return  false;
    }

    public void cerrarSesion() throws IOException {
        if (usuario != null) {
            usuario = null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getCorreo() {
        return usuario.getCorreo();
    }

    public boolean isSesionActiva() {
        return usuario != null;
    }

    public boolean tienePermisosAdmin() {
        if (!isSesionActiva()) { return false; }
        return usuario.getRol() == ListaRoles.ADMIN;
    }
}


