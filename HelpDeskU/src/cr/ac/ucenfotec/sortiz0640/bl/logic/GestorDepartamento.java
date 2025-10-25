package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.dl.DataDepartamento;

import java.util.ArrayList;

public class GestorDepartamento {

    private DataDepartamento db;

    public GestorDepartamento() {
        db  = new DataDepartamento();
    }

    public String agregar(String nombre, String descripcion, String correo) {

        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean res = db.agregar(tmpDepartamento);
        if (!res) {
            return "Error al agregar departamento";
        }
        return "Departamento agregado correctamente";

    }

    public String eliminarPorNombre(String nombre) {

        boolean res = db.eliminarPorNombre(nombre);
        if (!res) {
            return "El departamento especificado no existe";
        }
        return "Se ha eliminado el departamento correctamente";

    }

    public ArrayList<String> getDepartamentos() {
        return db.getDepartamentos();
    }



}
