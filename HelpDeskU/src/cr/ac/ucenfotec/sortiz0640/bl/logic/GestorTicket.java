package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.dl.DataTicket;

public class GestorTicket {

    DataTicket db;
    GestorDepartamento gd;

    public GestorTicket() {
        db = new DataTicket();
        gd = new GestorDepartamento();
    }

    public String crear(String asunto, String descripcion, String correoDepartamento) {

        Ticket tmpTicket = new Ticket(asunto, descripcion, correoDepartamento);

        if (!gd.existePorCorreo(correoDepartamento)) {
            return "El correo especificado no pertenece a ningún departamento. Intente nuevamente";
        }

        db.agregar(tmpTicket);
        agregarTicketDepartamento(tmpTicket, correoDepartamento);
        return "El ticket ha sido creado con éxito.\n" + tmpTicket.toString() ;

    }

    public void agregarTicketDepartamento(Ticket tmpTicket, String correoDepartamento) {
        gd.agregarTicket(tmpTicket, correoDepartamento);
    }

    public String eliminarPorId(String id) {

        boolean res = db.eliminarPorId(id);
        if (!res) {
            return "El ticket especificado no existe";
        }
        return "Se ha eliminado el ticket correctamente";
    }

    public String actualizarPorId() {

    }

    public String listarPorId() {

    }

    public String listarTodos() {

    }

    public String listarTodosPorDepartamento() {

    }
}
