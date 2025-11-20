package cr.ac.ucenfotec.sortiz0640.bl.logic;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;

import java.io.IOException;
import java.util.ArrayList;

public class GestorApp {

    private GestorUsuario gestorUsuario;
    private GestorDepartamento gestorDepartamento;
    private GestorTicket gestorTicket;
    private GestorSesion gestorSesion;

    public GestorApp() {
        this.gestorUsuario = new GestorUsuario();
        this.gestorDepartamento = new GestorDepartamento();
        this.gestorTicket = new GestorTicket();
        this.gestorSesion = new GestorSesion();
    }

    // ============================================
    // OPERACIONES DE USUARIO
    // ============================================

    public String agregarUsuario(String nombre, String apellidos, String correo, String password, int rol) {
        return gestorUsuario.agregar(nombre, apellidos, correo, password, rol);
    }

    public String eliminarUsuario(String correo) {
        return gestorUsuario.eliminarPorCorreo(correo);
    }

    public String listarUsuarioPorCorreo(String correo) {
        return gestorUsuario.listarPorCorreo(correo);
    }

    public ArrayList<String> listarTodosUsuarios() {
        return gestorUsuario.listarTodos();
    }

    // ============================================
    // OPERACIONES DE DEPARTAMENTO
    // ============================================

    public String agregarDepartamento(String nombre, String descripcion, String correo) {
        return gestorDepartamento.agregar(nombre, descripcion, correo);
    }

    public String eliminarDepartamento(String correo) {
        gestorTicket.eliminarPorCorreoDepartamento(correo);
        return gestorDepartamento.eliminarPorCorreo(correo);
    }

    public String listarDepartamentoPorCorreo(String correo) {
        return gestorDepartamento.listarPorCorreo(correo);
    }

    public ArrayList<String> listarTodosDepartamentos() {
        return gestorDepartamento.listarTodos();
    }

    public boolean existenDepartamentos() {
        return gestorDepartamento.existenDepartamentos();
    }

    public Departamento buscarDepartamentoPorCorreo(String correo) {
        return gestorDepartamento.buscarPorCorreo(correo);
    }

    // ============================================
    // OPERACIONES DE TICKET
    // ============================================

    public String crearTicket(String asunto, String descripcion, String correoDepartamento) {

        if (!gestorDepartamento.existePorCorreo(correoDepartamento)) {
            return "[ERR] El correo especificado no pertenece a ningún departamento. Intente nuevamente";
        }

        Departamento departamento = buscarDepartamentoPorCorreo(correoDepartamento);
        String resultado = gestorTicket.agregar(asunto, descripcion, getUsuarioActual(), departamento);

        return resultado;
    }

    public ArrayList<String> listarTicketsPorDepartamento(String correoDepartamento) {
        return gestorTicket.listarPorDepartamento(correoDepartamento);
    }

    public ArrayList<String> listarTicketsPorUsuario(String correoUsuario) {
        return gestorTicket.listarPorUsuario(correoUsuario);
    }

    public String eliminarTicket(String ticketId) {
        return gestorTicket.eliminarPorId(ticketId);
    }

    public String actualizarEstadoTicket(String ticketId, int estado) {

        EstadoTicket nuevoEstado = switch (estado) {
            case 1 -> EstadoTicket.EN_PROGRESO;
            case 2 -> EstadoTicket.RESUELTO;
            default -> null;
        };

        if (nuevoEstado == null) {
            return "[ERR] Estado inválido";
        }

        return gestorTicket.actualizarEstado(ticketId, nuevoEstado);
    }

    public ArrayList<String> listarTodosTickets() {
        return gestorTicket.listarTodos();
    }

    public boolean existenTickets() {
        return gestorTicket.existenTickets();
    }

    // ============================================
    // OPERACIONES DE SESIÓN
    // ============================================

    public boolean iniciarSesion(String correo, String password) {
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        return gestorSesion.iniciarSesion(correo, password, usuarios);
    }

    public void cerrarSesion() throws IOException {
        gestorSesion.cerrarSesion();
    }

    public Usuario obtenerUsuarioActual() {
        return gestorSesion.obtenerUsuarioActual();
    }

    public boolean esSesionActiva() {
        return gestorSesion.esSesionActiva();
    }

    public boolean tienePermisosAdmin() {
        return gestorSesion.tienePermisosAdmin();
    }

    public String obtenerCorreoUsuarioActual() {
        return gestorSesion.obtenerCorreo();
    }

    private Usuario getUsuarioActual() {
        return obtenerUsuarioActual();
    }
}