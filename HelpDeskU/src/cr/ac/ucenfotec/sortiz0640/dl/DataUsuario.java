package cr.ac.ucenfotec.sortiz0640.dl;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import java.util.ArrayList;

public class DataUsuario {

    private ArrayList<Usuario> usuarios;

    public DataUsuario() {
        usuarios = new ArrayList<>();
    }

    // regresa true si el usuario se agrega correctamente
    public boolean agregar(Usuario registro) {

        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(registro.getCorreo())) {
                return false;
            }
        }

        return usuarios.add(registro);
    }

    // regresa true si es usuario se elimina correctamente
    public boolean eliminarPorCorreo(String correo) {

        for (Usuario u: usuarios) {
            if (u.getCorreo().equals(correo)) {
                return usuarios.remove(u);
            }
        }
        return false;
    }

    public String listarPorCorreo(String correo) {
        for (Usuario u: usuarios) {
            if (u.getCorreo().equals(correo)) {
                return u.toString();
            }
        }
        return null;
    }

    // Regresa un arreglo de objetos tipo String, siendo cada objeto el toString de cada usuario
    public ArrayList<String> listarTodos() {

        if (usuarios.isEmpty()) {
            return null;
        }

        ArrayList<String> lista = new ArrayList<>();
        for (Usuario u : usuarios) {
            lista.add(u.toString());
        }
        return lista;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

}
