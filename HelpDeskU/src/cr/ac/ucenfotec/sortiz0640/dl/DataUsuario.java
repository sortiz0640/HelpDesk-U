package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;

import java.util.ArrayList;

public class DataUsuario {

    private ArrayList<Usuario> usuarios;

    public DataUsuario() {
        usuarios = new ArrayList<>();
    }

    // regresa true si el usuario se agrega correctamente
    public boolean agregar(Usuario usuario) {
        return usuarios.add(usuario);
    }

    // regresa true si es usuario se elimina correctamente
    public boolean eliminar(Usuario usuario) {
        return usuarios.remove(usuario);
    }

    // Regresa un arreglo de objetos tipo String, siendo cada objeto el toString de cada usuario
    public ArrayList<String> getUsuarios() {

        ArrayList<String> lista = new ArrayList<>();
        for (Usuario u : usuarios) {
            lista.add(u.toString());
        }
        return lista;
    }

}
