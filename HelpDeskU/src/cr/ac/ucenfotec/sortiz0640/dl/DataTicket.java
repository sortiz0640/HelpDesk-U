package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import java.util.ArrayList;

public class DataTicket {

    private ArrayList<Ticket> tickets;

    public DataTicket() {
        tickets = new ArrayList<>();
    }

    // agrega ticket a un arreglo de tickets
    public void agregar(Ticket ticket) {
        tickets.add(ticket);
    }

    // regresa true si el ticket se elimina correctamente
    public boolean eliminarPorId(String id) {

        return tickets.removeIf(ticket -> ticket.getId().equals(id));
    }

    // Regresa un arreglo de objetos tipo String, siendo cada objeto el toString de cada ticket
    public ArrayList<String> getTickets() {

        ArrayList<String> lista = new ArrayList<>();
        for (Ticket t : tickets) {
            lista.add(t.toString());
        }
        return lista;
    }
}
