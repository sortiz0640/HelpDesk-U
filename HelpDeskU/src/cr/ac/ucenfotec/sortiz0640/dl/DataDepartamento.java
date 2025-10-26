package cr.ac.ucenfotec.sortiz0640.dl;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;

import java.util.ArrayList;

public class DataDepartamento {

    private ArrayList<Departamento> departamentos;

    public DataDepartamento() {
        departamentos = new ArrayList<>();
    }

    // regresa true si el departamento se agrega correctamente
    public boolean agregar(Departamento registro) {

        for (Departamento d : departamentos) {
            if (d.getCorreo().equals(registro.getCorreo())) {
                return false;
            }

            if (d.getNombre().equals(registro.getNombre())) {
                return false;
            }
        }

        return departamentos.add(registro);
    }

    // regresa true si el departamento se elimina correctamente
    public boolean eliminarPorCorreo(String correo) {
        return departamentos.removeIf(departamento -> departamento.getCorreo().equals(correo));
    }

    public String listarPorCorreo(String correo) {
        for  (Departamento d : departamentos) {
            if (d.getCorreo().equals(correo)) {
                return d.toString();
            }
        }
        return null;
    }

    // Regresa un arreglo de objetos tipo String, siendo cada objeto el toString de cada departamento
    public ArrayList<String> getDepartamentos() {

        ArrayList<String> lista = new ArrayList<>();
        for (Departamento d : departamentos) {
            lista.add(d.toString());
        }
        return lista;
    }

    // Regresa un true si el correo pertenece a un departamento registrado
    public boolean existePorCorreo(String correo) {
        for (Departamento d : departamentos) {
            return d.getCorreo().equals(correo);
        }

        return false;
    }


    // Devuelve un objeto tipo Departamento según el correo proporcionado
    public Departamento getDepartamentoPorCorreo(String correo) {
        for (Departamento d : departamentos) {
            if (d.getCorreo().equals(correo)) {
                return d;
            }
        }
        return null;
    }

    // Agrega un nuevo ticket a un Departamento según su correo
    public void agregarTicket(Ticket ticket, String correo) {

        Departamento tmpDepartamento = getDepartamentoPorCorreo(correo);

        if (tmpDepartamento == null) {
            return;
        }

        tmpDepartamento.agregarTicket(ticket);

    }
}
