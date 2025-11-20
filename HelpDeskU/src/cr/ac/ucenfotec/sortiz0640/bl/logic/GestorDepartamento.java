package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.dl.DataDepartamento;

import java.util.ArrayList;

public class GestorDepartamento {

    private DataDepartamento db;

    public GestorDepartamento() {
        db = new DataDepartamento();
    }

    public String agregar(String nombre, String descripcion, String correo) {
        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean resultado = db.agregar(tmpDepartamento);

        if (!resultado) {
            return "[ERR] El correo/nombre del departamento ya se encuentra registrado";
        }

        return "[INFO] Departamento agregado correctamente";
    }

    public String eliminarPorCorreo(String correo) {
        boolean resultado = db.eliminarPorCorreo(correo);

        if (!resultado) {
            return "[ERR] El departamento especificado no existe";
        }

        return "[INFO] Se ha eliminado el departamento correctamente";
    }

    public boolean existePorCorreo(String correo) {
        return db.existePorCorreo(correo);
    }

    public String listarPorCorreo(String correo) {
        String resultado = db.listarPorCorreo(correo);

        if (resultado == null) {
            return "[ERR] El departamento especificado no existe";
        }

        return resultado;
    }

    public ArrayList<String> listarTodos() {
        if (!db.existenDepartamentos()) {
            return null;
        }

        return db.listarTodos();
    }

    public Departamento buscarPorCorreo(String correo) {
        return db.buscarPorCorreo(correo);
    }

    public boolean existenDepartamentos() {
        return db.existenDepartamentos();
    }

    public ArrayList<Departamento> obtenerDepartamentos() {
        return db.obtenerDepartamentos();
    }
}