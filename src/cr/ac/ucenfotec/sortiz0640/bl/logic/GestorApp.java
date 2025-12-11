package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestor principal de la aplicación HelpDesk.
 * Actúa como FACHADA y ÚNICO INTERMEDIARIO entre todos los gestores.
 * <p>
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
     *
     * @throws SQLException Si ocurre un error de conexión con la base de datos.
     * @throws ClassNotFoundException Si no se encuentra el driver de base de datos.
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

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param correo Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param rol Rol del usuario (1: ADMIN, 2: ESTUDIANTE, 3: FUNCIONARIO).
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws NoSuchAlgorithmException Si falla el algoritmo de encriptación.
     */
    public String agregarUsuario(String nombre, String apellidos, String correo, String password, int rol) throws SQLException, NoSuchAlgorithmException {
        return gestorUsuario.agregar(nombre, apellidos, correo, password, rol);
    }

    /**
     * Elimina un usuario del sistema por su correo.
     *
     * @param correo Correo del usuario a eliminar.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String eliminarUsuario(String correo) throws SQLException {
        return gestorUsuario.eliminarPorCorreo(correo);
    }

    /**
     * Obtiene una lista formateada de todos los usuarios registrados.
     *
     * @return Lista de arreglos de Strings con los datos de los usuarios.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTodosUsuariosFormato() throws SQLException {
        return gestorUsuario.obtenerTodosUsuariosFormato();
    }

    // ============================================
    // OPERACIONES DE DEPARTAMENTO
    // ============================================

    /**
     * Registra un nuevo departamento en el sistema.
     *
     * @param nombre Nombre del departamento.
     * @param descripcion Descripción del departamento.
     * @param correo Correo de contacto del departamento.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String agregarDepartamento(String nombre, String descripcion, String correo) throws SQLException {
        return gestorDepartamento.agregar(nombre, descripcion, correo);
    }

    /**
     * Elimina un departamento del sistema por su correo.
     *
     * @param correo Correo del departamento a eliminar.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String eliminarDepartamento(String correo) throws SQLException {
        return gestorDepartamento.eliminarPorCorreo(correo);
    }

    /**
     * Obtiene la lista de correos de todos los departamentos.
     *
     * @return Lista de correos electrónicos.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String> obtenerDepartamentos() throws SQLException {
        return gestorDepartamento.obtenerCorreosDepartamentos();
    }

    /**
     * Obtiene una lista formateada de todos los departamentos.
     *
     * @return Lista de arreglos de Strings con los datos de los departamentos.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTodosDepartamentosFormato() throws SQLException {
        return gestorDepartamento.obtenerTodosDepartamentosFormato();
    }

    // ============================================
    // OPERACIONES DE TICKET
    // ============================================

    /**
     * Crea un nuevo ticket en el sistema, validando el departamento y asignando el usuario actual.
     *
     * @param asunto Asunto del ticket.
     * @param descripcion Descripción detallada del problema.
     * @param correoDepartamento Correo del departamento destino.
     * @return true si el ticket se creó correctamente, false si el departamento no existe.
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws ClassNotFoundException Si ocurre un error interno de clases.
     */
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

    /**
     * Elimina un ticket del sistema por su ID.
     *
     * @param ticketId Identificador único del ticket.
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public String eliminarTicket(String ticketId) throws SQLException {
        return gestorTicket.eliminarPorId(ticketId);
    }

    /**
     * Obtiene una lista formateada de todos los tickets en el sistema.
     *
     * @return Lista de arreglos de Strings con los datos de los tickets.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTodosTicketsFormato() throws SQLException {
        return gestorTicket.obtenerTodosTicketsFormato();
    }

    /**
     * Obtiene los tickets asociados a un usuario específico.
     *
     * @param correoUsuario Correo del usuario.
     * @return Lista de arreglos de Strings con los datos de los tickets del usuario.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) throws SQLException {
        return gestorTicket.obtenerTicketsDelUsuarioFormato(correoUsuario);
    }

    /**
     * Actualiza el estado de un ticket existente.
     *
     * @param ticketId Identificador del ticket.
     * @param estado Nuevo estado (1: EN_PROGRESO, 2: RESUELTO).
     * @return Mensaje de éxito o error.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
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

    /**
     * Recupera las palabras clave técnicas detectadas en un ticket.
     *
     * @param ticketId Identificador del ticket.
     * @return Lista de palabras detonantes técnicas.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String> obtenerPalabrasDetonantesTecnicas(String ticketId) throws SQLException {
        return gestorTicket.obtenerPalabrasDetonantes(ticketId, TipoCategoria.TECNICA);
    }

    /**
     * Recupera las palabras clave emocionales detectadas en un ticket.
     *
     * @param ticketId Identificador del ticket.
     * @return Lista de palabras detonantes emocionales.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public ArrayList<String> obtenerPalabrasDetonantesEmocionales(String ticketId) throws SQLException {
        return gestorTicket.obtenerPalabrasDetonantes(ticketId, TipoCategoria.EMOCIONAL);
    }

    // ============================================
    // OPERACIONES DE SESIÓN
    // ============================================

    /**
     * Inicia la sesión de un usuario verificando sus credenciales.
     *
     * @param correo Correo electrónico.
     * @param password Contraseña.
     * @return true si las credenciales son correctas, false si no.
     * @throws SQLException Si ocurre un error en la base de datos.
     * @throws NoSuchAlgorithmException Si falla la verificación de contraseña.
     */
    public boolean iniciarSesion(String correo, String password) throws SQLException, NoSuchAlgorithmException {
        // Obtiene la lista de usuarios de GestorUsuario
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        // Coordina el inicio de sesión pasando los usuarios a GestorSesion
        return gestorSesion.iniciarSesion(correo, password, usuarios);
    }

    /**
     * Cierra la sesión activa actual.
     *
     * @throws IOException Si ocurre un error al cerrar la sesión.
     */
    public void cerrarSesion() throws IOException {
        gestorSesion.cerrarSesion();
    }

    /**
     * Verifica si existe una sesión activa actualmente.
     *
     * @return true si hay sesión activa, false si no.
     */
    public boolean esSesionActiva() {
        return gestorSesion.esSesionActiva();
    }

    /**
     * Verifica si el usuario en sesión tiene rol de administrador.
     *
     * @return true si es administrador, false si no.
     */
    public boolean tienePermisosAdmin() {
        return gestorSesion.tienePermisosAdmin();
    }

    /**
     * Obtiene el correo del usuario que tiene la sesión activa.
     *
     * @return Correo del usuario actual.
     */
    public String obtenerCorreoUsuarioActual() {
        return gestorSesion.obtenerCorreo();
    }
}