package cr.ac.ucenfotec.sortiz0640.bl.entities;

import java.util.Objects;

/**
 * Representa un departamento del sistema HelpDesk.
 * Los departamentos son las unidades que reciben y gestionan los tickets.
 * Cada departamento es identificado de forma única por su correo electrónico.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class Departamento {

    private String nombre;
    private String descripcion;
    private String correo;

    /**
     * Constructor vacío por defecto.
     */
    public Departamento() {

    }

    /**
     * Constructor para crear un nuevo departamento.
     *
     * @param nombre Nombre del departamento (ej: "Soporte Técnico").
     * @param descripcion Descripción de las funciones y responsabilidades del departamento.
     * @param correo Correo electrónico único que identifica al departamento.
     */
    public Departamento(String nombre, String descripcion, String correo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
    }

    /**
     * Obtiene el nombre del departamento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del departamento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del departamento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna la descripción del departamento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el correo electrónico del departamento.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Asigna el correo electrónico del departamento.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Genera una representación en texto del departamento.
     *
     * @return String con formato [Nombre: X][Email: Y][Desc: Z]
     */
    @Override
    public String toString() {
        return "[Nombre: " + getNombre() +"][Email: " + getCorreo() + "][Desc: "+ getDescripcion() +"]";
    }

    /**
     * Compara este departamento con otro objeto para determinar igualdad.
     * Dos departamentos son considerados iguales si tienen el mismo correo electrónico.
     *
     * @param o Objeto a comparar con este departamento.
     * @return true si los departamentos son iguales (mismo correo), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departamento d = (Departamento) o;
        return Objects.equals(correo, d.correo);
    }

    /**
     * Genera el código hash del departamento basado en su correo.
     *
     * @return Código hash calculado a partir del correo del departamento.
     */
    @Override
    public int hashCode() {
        return Objects.hash(correo);
    }
}