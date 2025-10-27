package cr.ac.ucenfotec.sortiz0640.bl.entities;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Departamento {

    private String nombre;
    private String descripcion;
    private String correo;
    private ArrayList<Ticket> listaTickets;

    public Departamento(String nombre, String descripcion, String correo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
        listaTickets = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void agregarTicket(Ticket ticket) {
        listaTickets.add(ticket);
    }

    public ArrayList<String> listarTickets() {

        ArrayList<String> lista = new ArrayList<>();

        for (Ticket ticket : listaTickets) {
            lista.add(ticket.toString());
        }

        return lista;
    }

    public ArrayList<Ticket> getListaTickets() {
        return listaTickets;
    }

    public boolean eliminarTicket(String ticketId) {
        for (Ticket ticket : listaTickets) {
            if (ticket.getId().equals(ticketId)) {
                listaTickets.remove(ticket);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[Nombre: " + getNombre() +"][Email: " + getCorreo() + "][Desc: "+ getDescripcion() +"]";
    }
}
