package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;

import java.util.ArrayList;

public class DataUsuario {

    private ArrayList<Usuario> listaUsuarios;

    public DataUsuario() {
        this.listaUsuarios = new ArrayList<>();
    }

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

    public boolean eliminarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                listaUsuarios.remove(usuario);
                return true;
            }
        }
        return false;
    }

    public String listarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario.toString();
            }
        }
        return null;
    }

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Usuario usuario : listaUsuarios) {
            lista.add(usuario.toString());
        }

        return lista;
    }

    public Usuario buscarPorCorreo(String correo) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getCorreo().equals(correo)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean existePorCorreo(String correo) {
        return buscarPorCorreo(correo) != null;
    }

    public ArrayList<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }
}