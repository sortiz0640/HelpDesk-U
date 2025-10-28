package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.dl.DataDepartamento;

import java.util.ArrayList;

public class GestorDepartamento {

    private DataDepartamento db;

    public GestorDepartamento() {
        db  = new DataDepartamento();
    }

    public String agregar(String nombre, String descripcion, String correo) {

        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean res = db.agregar(tmpDepartamento);
        if (!res) {
            return "[ERR] El correo/nombre del departamento ya se encuentra registrado";
        }
        return "[INFO] Departamento agregado correctamente";

    }

    public String eliminarPorCorreo(String correo) {

        boolean res = db.eliminarPorCorreo(correo);
        if (!res) {
            return "[ERR] El departamento especificado no existe";
        }
        return "[INFO] Se ha eliminado el departamento correctamente";

    }

    public boolean existePorCorreo(String correo) {
        return db.existePorCorreo(correo);
    }

    public String listarPorCorreo(String correo) {

        String res = db.listarPorCorreo(correo);
        if (res == null) {
            return "[ERR] El departamento especificado no existe";
        }
        return res;
    }

    public ArrayList<String> listarTodos() {

        if (!db.existenDepartamentos()) {
            return null;
        }

        return db.listarTodos();
    }

    public Departamento buscarPorCorreo(String correo) {
        return db.getDepartamentoPorCorreo(correo);
    }

    public boolean existenDepartamentos() {

        boolean res = db.existenDepartamentos();
        if (!res) {
            return false;
        }
        return res;
    }

    public void agregarTicket(Ticket ticket, String correo) {
        db.agregarTicket(ticket, correo);
    }

    public ArrayList<String> listarTicketsPorCorreo(String correo) {
        return db.listarTicketsPorCorreo(correo);
    }

    public boolean eliminarTicketPorId(String ticketId) {

        return db.eliminarTicketPorId(ticketId);
    }

    public boolean actualizarEstadoTicket(String correo, EstadoTicket estado) {
        return db.actualizarEstadoTicket(correo, estado);
    }
}
