package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;

import java.util.ArrayList;

public class DataTicket {

    private ArrayList<Ticket> listaTickets;

    public DataTicket() {
        this.listaTickets = new ArrayList<>();
    }

    public boolean agregar(Ticket ticket) {
        if (ticket == null) {
            return false;
        }

        listaTickets.add(ticket);
        return true;
    }

    public boolean eliminarPorId(String ticketId) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                listaTickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    public boolean actualizarEstado(String ticketId, EstadoTicket nuevoEstado) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                ticket.setEstado(nuevoEstado);
                return true;
            }
        }
        return false;
    }

    public Ticket buscarPorId(String ticketId) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    public boolean existePorId(String ticketId) {
        return buscarPorId(ticketId) != null;
    }

    public ArrayList<String> listarTodos() {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            lista.add(ticket.toString());
        }

        return lista;
    }

    public ArrayList<String> listarPorDepartamento(String correoDepartamento) {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            if (ticket.getDepartamento() != null &&
                    ticket.getDepartamento().getCorreo().equals(correoDepartamento)) {
                lista.add(ticket.toString());
            }
        }

        return lista;
    }

    public ArrayList<String> listarPorUsuario(String correoUsuario) {
        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            if (ticket.getUsuario() != null &&
                    ticket.getUsuario().getCorreo().equals(correoUsuario)) {
                lista.add(ticket.toString());
            }
        }

        return lista;
    }

    public void eliminarPorCorreoDepartamento(String correoDepartamento) {
        listaTickets.removeIf(t -> t.getDepartamento().getCorreo().equals(correoDepartamento));
    }

    public ArrayList<Ticket> obtenerTickets() {
        return listaTickets;
    }

    public boolean existenTickets() {
        return !listaTickets.isEmpty();
    }
}