package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestor principal de la aplicación HelpDesk.
 * Actúa como FACHADA y ÚNICO INTERMEDIARIO entre todos los gestores.
 *
 * PRINCIPIO DE DISEÑO:
 * - Ningún gestor conoce a otro gestor
 * - GestorApp es el único que coordina las operaciones entre gestores
 * - Cada gestor trabaja solo con su entidad
 * - GestorApp pasa las dependencias como parámetros cuando es necesario
 *
 * @author Sebastian Ortiz
 * @version 2.0
 * @since 2025
 */

public class GestorApp {

    private GestorUsuario gestorUsuario;
    private GestorDepartamento gestorDepartamento;
    private GestorTicket gestorTicket;
    private GestorSesion gestorSesion;

    /**
     * Constructor que inicializa todos los gestores del sistema de forma independiente.
     */

    public GestorApp() throws SQLException, ClassNotFoundException {
        this.gestorUsuario = new GestorUsuario();
        this.gestorDepartamento = new GestorDepartamento();
        this.gestorTicket = new GestorTicket();
        this.gestorSesion = new GestorSesion();
    }

    // ============================================
    // OPERACIONES DE USUARIO
    // ============================================

    public String agregarUsuario(String nombre, String apellidos, String correo, String password, int rol) throws SQLException {
        return gestorUsuario.agregar(nombre, apellidos, correo, password, rol);
    }

    public String eliminarUsuario(String correo) throws SQLException {
        return gestorUsuario.eliminarPorCorreo(correo);
    }

    public String[] obtenerDetallesUsuario(String correoUsuario) throws SQLException {
        return gestorUsuario.obtenerDetallesUsuario(correoUsuario);
    }

    public ArrayList<String[]> obtenerTodosUsuariosFormato() throws SQLException {
        return gestorUsuario.obtenerTodosUsuariosFormato();
    }

    // ============================================
    // OPERACIONES DE DEPARTAMENTO
    // ============================================

    public String agregarDepartamento(String nombre, String descripcion, String correo) throws SQLException {
        return gestorDepartamento.agregar(nombre, descripcion, correo);
    }

    public String eliminarDepartamento(String correo) throws SQLException {
        // Coordinación entre gestores: primero elimina tickets relacionados
        gestorTicket.eliminarPorCorreoDepartamento(correo);
        // Luego elimina el departamento
        return gestorDepartamento.eliminarPorCorreo(correo);
    }

    public ArrayList<String> obtenerDepartamentos() throws SQLException {
        return gestorDepartamento.obtenerCorreosDepartamentos();
    }

    public String[] obtenerDetallesDepartamento(String correoDepartamento) throws SQLException {
        return gestorDepartamento.obtenerDetallesDepartamento(correoDepartamento);
    }

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() throws SQLException {
        return gestorDepartamento.obtenerTodosDepartamentosFormato();
    }

    // ============================================
    // OPERACIONES DE TICKET
    // ============================================


    public boolean crearTicket(String asunto, String descripcion, String correoDepartamento) throws SQLException, ClassNotFoundException {
        // Valida que el departamento exista
        if (!gestorDepartamento.existePorCorreo(correoDepartamento)) {
            return false;
        }

        // Obtiene el departamento de GestorDepartamento
        Departamento departamento = gestorDepartamento.buscarPorCorreo(correoDepartamento);

        // Obtiene el usuario actual de GestorSesion
        Usuario usuarioActual = gestorSesion.obtenerUsuarioActual();

        // Coordina la creación del ticket pasando las entidades como parámetros
        return gestorTicket.agregar(asunto, descripcion, usuarioActual, departamento);
    }

    public String eliminarTicket(String ticketId) throws SQLException {
        return gestorTicket.eliminarPorId(ticketId);
    }

//    public String[] obtenerDetallesTicket(String ticketId) {
//        return gestorTicket.obtenerDetallesTicket(ticketId);
//    }

    public ArrayList<String[]> obtenerTodosTicketsFormato() throws SQLException {
        return gestorTicket.obtenerTodosTicketsFormato();
    }

    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) throws SQLException {
        return gestorTicket.obtenerTicketsDelUsuarioFormato(correoUsuario);
    }

    public String actualizarEstadoTicket(String ticketId, int estado) throws SQLException {
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

    // ============================================
    // OPERACIONES DE SESIÓN
    // ============================================

    public boolean iniciarSesion(String correo, String password) throws SQLException {
        // Obtiene la lista de usuarios de GestorUsuario
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        // Coordina el inicio de sesión pasando los usuarios a GestorSesion
        return gestorSesion.iniciarSesion(correo, password, usuarios);
    }

    public void cerrarSesion() throws IOException {
        gestorSesion.cerrarSesion();
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
}