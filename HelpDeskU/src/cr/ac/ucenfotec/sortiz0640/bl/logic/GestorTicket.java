package cr.ac.ucenfotec.sortiz0640.bl.logic;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import java.util.ArrayList;

public class GestorTicket {

    GestorDepartamento gd;

    public GestorTicket(GestorDepartamento gd) {
        this.gd = gd;
    }

    public String crear(String asunto, String descripcion, String correoDepartamento, String correoUsuarioCreador) {

        Ticket tmpTicket = new Ticket(asunto, descripcion, correoUsuarioCreador);

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

        Departamento tmpDepartamento = gd.buscarPorCorreo(correo);

        if (tmpDepartamento != null) {
            return tmpDepartamento.listarTickets();
        }

        return null;
    }

    public ArrayList<String> listarMisTickets(String correoUsuarioCreador) {
        return gd.listarTicketsPorCorreo(correoUsuarioCreador);
    }

    public String eliminar(String ticketId) {

        boolean estado = gd.eliminarTicket();

        if (!estado) {
            return "No se ha logrado eliminar el ticket";
        }

        return  "Ticket eliminado correctamente";
    }
}
