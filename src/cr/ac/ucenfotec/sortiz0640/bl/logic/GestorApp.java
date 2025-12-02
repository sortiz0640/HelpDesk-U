package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Ticket;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import java.io.IOException;
import java.lang.reflect.Array;
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

    public String[] obtenerDetallesUsuario(String correoUsuario) {
        Usuario u = gestorUsuario.buscarPorCorreo(correoUsuario);

        return new String[]{
                u.getNombre(),
                u.getApellidos(),
                u.getCorreo(),
                u.getRol().toString(),
        };
    }

    public ArrayList<String[]> obtenerTodosUsuariosFormato() {
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        return convertirUsuariosArray(usuarios);
    }

    public ArrayList<String[]> convertirUsuariosArray(ArrayList<Usuario> usuarios) {
        ArrayList<String[]> resultado = new ArrayList<>();

        if (usuarios == null || usuarios.isEmpty()) {
            return resultado;
        }

        for (Usuario u : usuarios) {
            String[] datos = {
                u.getNombre(),
                u.getApellidos(),
                u.getCorreo(),
                u.getRol().toString(),
            };
            resultado.add(datos);
        }

        return resultado;
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

    public ArrayList<String> obtenerDepartamentos() {

        ArrayList<String> departamentos = new ArrayList<>();
        for (Departamento d: gestorDepartamento.obtenerDepartamentos()) {
            departamentos.add(d.getCorreo());
        }
        return departamentos;
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

    public String[] obtenerDetallesDepartamento(String correoDepartamento) {
        Departamento d = gestorDepartamento.buscarPorCorreo(correoDepartamento);

        return new String[]{
            d.getNombre(),
            d.getCorreo(),
            d.getDescripcion(),
        };
    }

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() {
        ArrayList<Departamento> departamentos = gestorDepartamento.obtenerDepartamentos();
        return convertirDepartamentosArray(departamentos);
    }

    public ArrayList<String[]> convertirDepartamentosArray(ArrayList<Departamento> departamentos){
        ArrayList<String[]> resultado = new ArrayList<>();

        if (departamentos == null || departamentos.isEmpty()) {
            return resultado;
        }

        for (Departamento d : departamentos) {
            String[] datos = {
                d.getNombre(),
                d.getCorreo(),
                d.getDescripcion(),
            };
            resultado.add(datos);
        }

        return resultado;
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

    public boolean crearTicket(String asunto, String descripcion, String correoDepartamento) {
        if (!gestorDepartamento.existePorCorreo(correoDepartamento)) {
            return false;
        }

        Departamento departamento = buscarDepartamentoPorCorreo(correoDepartamento);
        boolean resultado = gestorTicket.agregar(asunto, descripcion, getUsuarioActual(), departamento);

        return resultado;
    }


    public ArrayList<String[]> obtenerTodosTicketsFormato() {
        ArrayList<Ticket> tickets = gestorTicket.obtenerTickets(); // O el método que tengas
        return convertirTicketsAArray(tickets);
    }

    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) {
        ArrayList<Ticket> tickets = gestorTicket.obtenerTiquetesPorUsuario(correoUsuario);
        return convertirTicketsAArray(tickets);
    }

    private ArrayList<String[]> convertirTicketsAArray(ArrayList<Ticket> tickets) {
        ArrayList<String[]> resultado = new ArrayList<>();

        if (tickets == null || tickets.isEmpty()) {
            return resultado;
        }

        for (Ticket ticket : tickets) {
            String[] datos = {
                ticket.getId(),
                ticket.getAsunto(),
                ticket.getDepartamento().getCorreo(),
                ticket.getUsuario().getCorreo(),
                ticket.getCategoriaTecnica(),
                ticket.getCategoriaEmocional(),
                ticket.getEstado().toString()
            };
            resultado.add(datos);
        }

        return resultado;
    }

    public String eliminarTicket(String ticketId) {
        return gestorTicket.eliminarPorId(ticketId);
    }

    public String[] obtenerDetallesTicket(String ticketId) {
        Ticket t = gestorTicket.buscarPorId(ticketId);

        return new String[]{
                t.getId(),
                t.getAsunto(),
                t.getDescripcion(),
                t.getDepartamento().getCorreo(),
                t.getUsuario().getCorreo(),
                t.getCategoriaTecnica(),
                t.getCategoriaEmocional(),
                t.getEstado().toString(),
                t.getEstado().toString()
        };
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