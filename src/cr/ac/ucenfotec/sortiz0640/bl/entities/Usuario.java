package cr.ac.ucenfotec.sortiz0640.bl.entities;

import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;
import java.util.Objects;

/**
 * Representa un usuario del sistema HelpDesk.
 * Cada usuario tiene credenciales únicas (correo) y un rol asignado que determina sus permisos.
 * Los usuarios pueden crear tickets y gestionar el sistema según su rol.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class Usuario {

    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private ListaRoles rol;

    /**
     * Constructor vacío por defecto.
     */
    public Usuario() {

    }

    /**
     * Constructor para crear un nuevo usuario del sistema.
     *
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param correo Correo electrónico único que identifica al usuario.
     * @param password Contraseña de acceso al sistema.
     * @param rol Rol asignado que determina los permisos (ADMIN, ESTUDIANTE, FUNCIONARIO).
     */
    public Usuario(String nombre, String apellidos, String correo, String password, ListaRoles rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Obtiene el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Asigna los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Asigna el correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Asigna la contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el rol del usuario.
     */
    public ListaRoles getRol() {
        return rol;
    }

    /**
     * Asigna el rol del usuario.
     */
    public void setRol(ListaRoles rol) {
        this.rol = rol;
    }

    /**
     * Genera una representación en texto del usuario.
     *
     * @return String con formato [Nombre: X][Correo: Y][ROL]
     */
    @Override
    public String toString() {
        return "[Nombre: " + getNombre() + " "+ getApellidos() +"][Correo: " + getCorreo() + "]" +"["+ getRol() +"]";
    }

    /**
     * Compara este usuario con otro objeto para determinar igualdad.
     * Dos usuarios son considerados iguales si tienen el mismo correo electrónico.
     *
     * @param o Objeto a comparar con este usuario.
     * @return true si los usuarios son iguales (mismo correo), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario u = (Usuario) o;
        return Objects.equals(correo, u.correo);
    }

    /**
     * Genera el código hash del usuario basado en su correo.
     *
     * @return Código hash calculado a partir del correo del usuario.
     */
    @Override
    public int hashCode() {
        return Objects.hash(correo);
    }
}