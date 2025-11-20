package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import cr.ac.ucenfotec.sortiz0640.dl.DataUsuario;

import java.util.ArrayList;

public class GestorUsuario {

    private DataUsuario db;

    public GestorUsuario() {
        db = new DataUsuario();
    }

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

    public String eliminarPorCorreo(String correo) {
        boolean resultado = db.eliminarPorCorreo(correo);

        if (!resultado) {
            return "[ERR] El usuario especificado no existe";
        }

        return "[INFO] Usuario eliminado correctamente";
    }

    public String listarPorCorreo(String correo) {
        String resultado = db.listarPorCorreo(correo);

        if (resultado == null) {
            return "[ERR] El usuario especificado no existe";
        }

        return resultado;
    }

    public ArrayList<String> listarTodos() {
        return db.listarTodos();
    }

    public Usuario buscarPorCorreo(String correo) {
        return db.buscarPorCorreo(correo);
    }

    public boolean existePorCorreo(String correo) {
        return db.existePorCorreo(correo);
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return db.obtenerUsuarios();
    }
}