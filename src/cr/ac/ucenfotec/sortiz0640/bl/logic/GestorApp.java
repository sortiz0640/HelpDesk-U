package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;
import java.io.IOException;
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

    /**
     * Obtiene los detalles de un usuario en formato de array.
     *
     * @param correoUsuario Correo del usuario
     * @return Array con [nombre, apellidos, correo, rol]
     */

    public String[] obtenerDetallesUsuario(String correoUsuario) {
        return gestorUsuario.obtenerDetallesUsuario(correoUsuario);
    }

    /**
     * Obtiene todos los usuarios en formato de array para tablas.
     *
     * @return ArrayList de arrays con los datos de cada usuario
     */

    public ArrayList<String[]> obtenerTodosUsuariosFormato() {
        return gestorUsuario.obtenerTodosUsuariosFormato();
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
     * COORDINACIÓN: GestorApp coordina la eliminación en cascada.
     * Primero elimina los tickets del departamento, luego el departamento.
     *
     * @param correo Correo del departamento a eliminar
     * @return Mensaje indicando éxito o error
     */

    public String eliminarDepartamento(String correo) {
        // Coordinación entre gestores: primero elimina tickets relacionados
        gestorTicket.eliminarPorCorreoDepartamento(correo);
        // Luego elimina el departamento
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
     * Obtiene una lista con los correos de todos los departamentos.
     *
     * @return ArrayList con los correos de los departamentos
     */

    public ArrayList<String> obtenerDepartamentos() {
        return gestorDepartamento.obtenerCorreosDepartamentos();
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
     * COORDINACIÓN: Devuelve el objeto Departamento para que pueda ser usado
     * por otros métodos de GestorApp al coordinar operaciones.
     *
     * @param correo Correo del departamento
     * @return Objeto Departamento si existe, null en caso contrario
     */

    public Departamento buscarDepartamentoPorCorreo(String correo) {
        return gestorDepartamento.buscarPorCorreo(correo);
    }

    /**
     * Obtiene los detalles de un departamento en formato de array.
     *
     * @param correoDepartamento Correo del departamento
     * @return Array con [nombre, correo, descripcion]
     */

    public String[] obtenerDetallesDepartamento(String correoDepartamento) {
        return gestorDepartamento.obtenerDetallesDepartamento(correoDepartamento);
    }

    /**
     * Obtiene todos los departamentos en formato de array para tablas.
     *
     * @return ArrayList de arrays con los datos de cada departamento
     */

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() {
        return gestorDepartamento.obtenerTodosDepartamentosFormato();
    }

    // ============================================
    // OPERACIONES DE TICKET
    // ============================================

    /**
     * Crea un nuevo ticket asignado a un departamento específico.
     * COORDINACIÓN: GestorApp valida la existencia del departamento,
     * obtiene el usuario actual de la sesión, busca el departamento,
     * y coordina la creación del ticket con todos estos datos.
     *
     * @param asunto Asunto del ticket
     * @param descripcion Descripción detallada del problema
     * @param correoDepartamento Correo del departamento al que se asigna
     * @return true si el ticket se creó correctamente, false en caso contrario
     */

    public boolean crearTicket(String asunto, String descripcion, String correoDepartamento) {
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
     * @param ticketId ID del ticket a eliminar
     * @return Mensaje indicando éxito o error
     */

    public String eliminarTicket(String ticketId) {
        return gestorTicket.eliminarPorId(ticketId);
    }

    /**
     * Obtiene los detalles de un ticket en formato de array.
     *
     * @param ticketId ID del ticket
     * @return Array con los detalles del ticket
     */

    public String[] obtenerDetallesTicket(String ticketId) {
        return gestorTicket.obtenerDetallesTicket(ticketId);
    }

    /**
     * Obtiene todos los tickets en formato de array para tablas.
     *
     * @return ArrayList de arrays con los datos de cada ticket
     */

    public ArrayList<String[]> obtenerTodosTicketsFormato() {
        return gestorTicket.obtenerTodosTicketsFormato();
    }

    /**
     * Obtiene los tickets de un usuario específico en formato de array.
     *
     * @param correoUsuario Correo del usuario
     * @return ArrayList de arrays con los datos de los tickets
     */

    public ArrayList<String[]> obtenerTicketsDelUsuarioFormato(String correoUsuario) {
        return gestorTicket.obtenerTicketsDelUsuarioFormato(correoUsuario);
    }

    /**
     * Actualiza el estado de un ticket existente.
     * COORDINACIÓN: GestorApp convierte el código de estado y coordina
     * la actualización con GestorTicket.
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
     * COORDINACIÓN: GestorApp obtiene la lista de usuarios de GestorUsuario
     * y la pasa a GestorSesion para validar las credenciales.
     *
     * @param correo Correo del usuario
     * @param password Contraseña del usuario
     * @return true si las credenciales son válidas, false en caso contrario
     */

    public boolean iniciarSesion(String correo, String password) {
        // Obtiene la lista de usuarios de GestorUsuario
        ArrayList<Usuario> usuarios = gestorUsuario.obtenerUsuarios();
        // Coordina el inicio de sesión pasando los usuarios a GestorSesion
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
}