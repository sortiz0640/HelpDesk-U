package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;

import java.util.ArrayList;

public class DataDepartamento {

    private ArrayList<Departamento> listaDepartamentos;

    public DataDepartamento() {
        this.listaDepartamentos = new ArrayList<>();
    }

    public boolean agregar(Departamento departamento) {
        // Verificar si ya existe un departamento con el mismo correo o nombre
        for (Departamento d : listaDepartamentos) {
            if (d.getCorreo().equals(departamento.getCorreo()) ||
                    d.getNombre().equals(departamento.getNombre())) {
                return false;
            }
        }

        listaDepartamentos.add(departamento);
        return true;
    }

    public boolean eliminarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                listaDepartamentos.remove(departamento);
                return true;
            }
        }
        return false;
    }

    public boolean existePorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    public String listarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return departamento.toString();
            }
        }
        return null;
    }

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Departamento departamento : listaDepartamentos) {
            lista.add(departamento.toString());
        }

        return lista;
    }

    public Departamento buscarPorCorreo(String correo) {
        for (Departamento departamento : listaDepartamentos) {
            if (departamento.getCorreo().equals(correo)) {
                return departamento;
            }
        }
        return null;
    }

    public boolean existenDepartamentos() {
        return !listaDepartamentos.isEmpty();
    }

    public ArrayList<Departamento> obtenerDepartamentos() {
        return listaDepartamentos;
    }
}
