package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Gestor principal de la aplicación HelpDesk.
 * Actúa como fachada del sistema, coordinando las operaciones entre todos los gestores
 * especializados (Usuario, Departamento, Ticket, Sesión).
 * Proporciona una interfaz unificada para todas las funcionalidades del sistema.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class GestorApp {

    private GestorUsuario gestorUsuario;
    private GestorDepartamento gestorDepartamento;
    private GestorTicket gestorTicket;
    private GestorSesion gestorSesion;

    /**
     * Constructor que inicializa todos los gestores del sistema.
     * Crea las instancias de los gestores de Usuario, Departamento, Ticket y Sesión.
     */

    public GestorApp() {
        this.gestorUsuario = new GestorUsuario();
        this.gestorDepartamento = new GestorDepartamento();
        this.gestorTicket = new GestorTicket();
        this.gestorSesion = new GestorSesion();
    }

    // ============================================
    // OPERACIONES DE USUARIO
    // ============================================

    /**
     * Agrega un nuevo usuario al sistema.
     *
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param correo Correo electrónico único
     * @param password Contraseña de acceso
     * @param rol Código del rol (1: ADMIN, 2: ESTUDIANTE, 3: FUNCIONARIO)
     * @return Mensaje indicando éxito o error
     */

    public String agregarUsuario(String nombre, String apellidos, String correo, String password, int rol) {
        return gestorUsuario.agregar(nombre, apellidos, correo, password, rol);
    }

    /**
     * Elimina un usuario del sistema por su correo.
     *
     * @param correo Correo del usuario a eliminar
     * @return Mensaje indicando éxito o error
     */

    public String eliminarUsuario(String correo) {
        return gestorUsuario.eliminarPorCorreo(correo);
    }

    /**
     * Obtiene la información de un usuario específico.
     *
     * @param correo Correo del usuario a buscar
     * @return Representación en texto del usuario o mensaje de error
     */

    public String listarUsuarioPorCorreo(String correo) {
        return gestorUsuario.listarPorCorreo(correo);
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return ArrayList con la representación de todos los usuarios
     */

    public ArrayList<String> listarTodosUsuarios() {
        return gestorUsuario.listarTodos();
    }

    // ============================================
    // OPERACIONES DE DEPARTAMENTO
    // ============================================

    /**
     * Agrega un nuevo departamento al sistema.
     *
     * @param nombre Nombre del departamento
     * @param descripcion Descripción de sus funciones
     * @param correo Correo electrónico único
     * @return Mensaje indicando éxito o error
     */

    public String agregarDepartamento(String nombre, String descripcion, String correo) {
        return gestorDepartamento.agregar(nombre, descripcion, correo);
    }

    /**
     * Elimina un departamento y todos sus tickets asociados.
     * Implementa eliminación en cascada para mantener integridad referencial.
     *
     * @param correo Correo del departamento a eliminar
     * @return Mensaje indicando éxito o error
     */

    public String eliminarDepartamento(String correo) {
        gestorTicket.eliminarPorCorreoDepartamento(correo);
        return gestorDepartamento.eliminarPorCorreo(correo);
    }

    /**
     * Obtiene la información de un departamento específico.
     *
     * @param correo Correo del departamento a buscar
     * @return Representación en texto del departamento o mensaje de error
     */

    public String listarDepartamentoPorCorreo(String correo) {
        return gestorDepartamento.listarPorCorreo(correo);
    }

    /**
     * Lista todos los departamentos registrados en el sistema.
     *
     * @return ArrayList con la representación de todos los departamentos
     */

    public ArrayList<String> listarTodosDepartamentos() {
        return gestorDepartamento.listarTodos();
    }

    /**
     * Verifica si existen departamentos en el sistema.
     *
     * @return true si hay al menos un departamento registrado
     */

    public boolean existenDepartamentos() {
        return gestorDepartamento.existenDepartamentos();
    }

    /**
     * Busca un departamento por su correo electrónico.
     *
     * @param correo Correo del departamento
     * @return Objeto Departamento si existe, null en caso contrario
     */

    public Departamento buscarDepartamentoPorCorreo(String correo) {
        return gestorDepartamento.buscarPorCorreo(correo);
    }

    // ============================================
    // OPERACIONES DE TICKET
    // ============================================

    /**
     * Crea un nuevo ticket asignado a un departamento específico.
     * Valida que el departamento exista antes de crear el ticket.
     * El ticket se crea automáticamente asociado al usuario de la sesión actual.
     *
     * @param asunto Asunto del ticket
     * @param descripcion Descripción detallada del problema
     * @param correoDepartamento Correo del departamento al que se asigna
     * @return Mensaje indicando éxito con los datos del ticket o error
     */

    public String crearTicket(String asunto, String descripcion, String correoDepartamento) {
        if (!gestorDepartamento.existePorCorreo(correoDepartamento)) {
            return "[ERR] El correo especificado no pertenece a ningún departamento. Intente nuevamente";
        }

        Departamento departamento = buscarDepartamentoPorCorreo(correoDepartamento);
        String resultado = gestorTicket.agregar(asunto, descripcion, getUsuarioActual(), departamento);

        return resultado;
    }

    /**
     * Lista todos los tickets asignados a un departamento específico.
     *
     * @param correoDepartamento Correo del departamento
     * @return ArrayList con los tickets del departamento
     */

    public ArrayList<String> listarTicketsPorDepartamento(String correoDepartamento) {
        return gestorTicket.listarPorDepartamento(correoDepartamento);
    }

    /**
     * Lista todos los tickets creados por un usuario específico.
     *
     * @param correoUsuario Correo del usuario
     * @return ArrayList con los tickets del usuario
     */

    public ArrayList<String> listarTicketsPorUsuario(String correoUsuario) {
        return gestorTicket.listarPorUsuario(correoUsuario);
    }

    /**
     * Elimina un ticket del sistema por su ID.
     *
     * @param ticketId ID del ticket a eliminar
     * @return Mensaje indicando éxito o error
     */

    public String eliminarTicket(String ticketId) {
        return gestorTicket.eliminarPorId(ticketId);
    }

    /**
     * Actualiza el estado de un ticket existente.
     *
     * @param ticketId ID del ticket a actualizar
     * @param estado Código del nuevo estado (1: EN_PROGRESO, 2: RESUELTO)
     * @return Mensaje indicando éxito o error
     */

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

    /**
     * Lista todos los tickets registrados en el sistema.
     *
     * @return ArrayList con todos los tickets
     */

    public ArrayList<String> listarTodosTickets() {
        return gestorTicket.listarTodos();
    }

    /**
     * Verifica si existen tickets en el sistema.
     *
     * @return true si hay al menos un ticket registrado
     */

    public boolean existenTickets() {
        return gestorTicket.existenTickets();
    }

    // ============================================
    // OPERACIONES DE SESIÓN
    // ============================================

    /**
     * Inicia sesión en el sistema con las credenciales proporcionadas.
     *
     * @param correo Correo del usuario
     * @param password Contraseña del usuario
     * @return true si las credenciales son válidas, false en caso contrario
     */

    public boolean iniciarSesion(String correo, String password) {
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        return gestorSesion.iniciarSesion(correo, password, usuarios);
    }

    /**
     * Cierra la sesión actual del usuario.
     *
     * @throws IOException Si ocurre un error al cerrar la sesión
     */

    public void cerrarSesion() throws IOException {
        gestorSesion.cerrarSesion();
    }

    /**
     * Obtiene el usuario actualmente logueado.
     *
     * @return Objeto Usuario de la sesión actual
     */

    public Usuario obtenerUsuarioActual() {
        return gestorSesion.obtenerUsuarioActual();
    }

    /**
     * Verifica si hay una sesión activa.
     *
     * @return true si hay un usuario logueado
     */

    public boolean esSesionActiva() {
        return gestorSesion.esSesionActiva();
    }

    /**
     * Verifica si el usuario actual tiene permisos de administrador.
     *
     * @return true si el usuario tiene rol ADMIN
     */

    public boolean tienePermisosAdmin() {
        return gestorSesion.tienePermisosAdmin();
    }

    /**
     * Obtiene el correo del usuario actualmente logueado.
     *
     * @return Correo del usuario actual
     */

    public String obtenerCorreoUsuarioActual() {
        return gestorSesion.obtenerCorreo();
    }

    /**
     * Método privado auxiliar para obtener el usuario actual.
     *
     * @return Usuario de la sesión actual
     */

    private Usuario getUsuarioActual() {
        return obtenerUsuarioActual();
    }
}