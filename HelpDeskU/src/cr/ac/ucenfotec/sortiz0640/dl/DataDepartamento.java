package cr.ac.ucenfotec.sortiz0640.dl;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;

import java.util.ArrayList;

public class DataDepartamento {

    private ArrayList<Departamento> departamentos;

    public DataDepartamento() {
        departamentos = new ArrayList<>();
    }

    // regresa true si el departamento se agrega correctamente
    public boolean agregar(Departamento departamento) {
        return departamentos.add(departamento);
    }

    // regresa true si el departamento se elimina correctamente
    public boolean eliminarPorNombre(String nombre) {
        return departamentos.removeIf(departamento -> departamento.getNombre().equals(nombre));
    }

    // Regresa un arreglo de objetos tipo String, siendo cada objeto el toString de cada departamento
    public ArrayList<String> getDepartamentos() {

        ArrayList<String> lista = new ArrayList<>();
        for (Departamento d : departamentos) {
            lista.add(d.toString());
        }
        return lista;
    }
}
