package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.dl.DataTicket;

import java.util.ArrayList;

public class GestorTicket {

    private DataTicket db;

    public GestorTicket() {
        db = new DataTicket();
    }

    public String agregar(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        Ticket nuevoTicket = new Ticket(asunto, descripcion, usuario, departamento);
        boolean resultado = db.agregar(nuevoTicket);

        if (!resultado) {
            return "[ERR] Error al crear el ticket";
        }

        return "[INFO] El ticket ha sido creado con éxito.\n" + nuevoTicket.toString();
    }

    public String eliminarPorId(String ticketId) {
        boolean resultado = db.eliminarPorId(ticketId);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente";
        }

        return "[INFO] Ticket eliminado correctamente";
    }

    public String actualizarEstado(String ticketId, EstadoTicket nuevoEstado) {
        boolean resultado = db.actualizarEstado(ticketId, nuevoEstado);

        if (!resultado) {
            return "[ERR] El ticket especificado no existe. Intente nuevamente.";
        }

        return "[INFO] Ticket actualizado correctamente";
    }

    public ArrayList<String> listarTodos() {
        return db.listarTodos();
    }

    public ArrayList<String> listarPorDepartamento(String correoDepartamento) {
        return db.listarPorDepartamento(correoDepartamento);
    }

    public ArrayList<String> listarPorUsuario(String correoUsuario) {
        return db.listarPorUsuario(correoUsuario);
    }

    public Ticket buscarPorId(String ticketId) {
        return db.buscarPorId(ticketId);
    }

    public boolean existePorId(String ticketId) {
        return db.existePorId(ticketId);
    }

    public boolean existenTickets() {
        return db.existenTickets();
    }
    public void eliminarPorCorreoDepartamento(String correoDepartamento) {
        db.eliminarPorCorreoDepartamento(correoDepartamento);
    }
}