package cr.ac.ucenfotec.sortiz0640.bl.logic;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;

import java.util.ArrayList;

public class GestorTicket {

    GestorDepartamento gd;

    public GestorTicket() {
        gd = new GestorDepartamento();
    }

    public String crear(String asunto, String descripcion, String correoDepartamento) {

        Ticket tmpTicket = new Ticket(asunto, descripcion);

        if (!gd.existePorCorreo(correoDepartamento)) {
            return "El correo especificado no pertenece a ningún departamento. Intente nuevamente";
        }

        // El departamento guarda el ticket creado
        agregarTicketDepartamento(tmpTicket, correoDepartamento);
        return "El ticket ha sido creado con éxito.\n" + tmpTicket.toString() ;

    }

    public void agregarTicketDepartamento(Ticket tmpTicket, String correoDepartamento) {
        gd.agregarTicket(tmpTicket, correoDepartamento);
    }

    //public String actualizarPorId(String id, int estado) {

        //todo

    //}

    public ArrayList<String> listarTodosPorDepartamento(String correo) {

        GestorDepartamento gestorDepartamento = new GestorDepartamento();
        Departamento tmpDepartamento = gestorDepartamento.buscarPorCorreo(correo);

        if (tmpDepartamento != null) {
            return tmpDepartamento.listarTickets();
        }

        return null;

    }
}
