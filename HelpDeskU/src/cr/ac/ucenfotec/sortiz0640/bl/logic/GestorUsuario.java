package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.dl.DataUsuario;

import java.util.ArrayList;

public class GestorUsuario {

    private DataUsuario db;

    public GestorUsuario() {
        db  = new DataUsuario();
    }

    public String agregar(String nombre, String apellidos, String correo, String password, int rolEntrada) {

        ListaRoles rol = switch (rolEntrada) {
            case 1 -> ListaRoles.ADMIN;
            case 2 -> ListaRoles.ESTUDIANTE;
            case 3 -> ListaRoles.FUNCIONARIO;
            default -> null;
        };

        Usuario tmpUsuario = new Usuario(nombre, apellidos, correo, password, rol);
        boolean res = db.agregar(tmpUsuario);

        if (!res) {
            return "El correo del usuario ya se encuentra registrado!";
        }
        return "Usuario agregado correctamente";

    }

    public String listarPorCorreo(String correo) {
        String res = db.listarPorCorreo(correo);
        if (res == null) {
            return "El usuario especificado no existe";
        }
        return res;
    }

    public String eliminarPorCorreo(String correo) {

        boolean res = db.eliminarPorCorreo(correo);
        if (!res) {
            return "El usuario especificado no existe";
        }
        return "Usuario eliminado correctamente";

    }

    public ArrayList<Usuario> getUsuarios() {
        return db.getUsuarios();
    }

    public ArrayList<String> listarTodos() {
        return db.listarTodos();
    }
}
