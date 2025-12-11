package cr.ac.ucenfotec.sortiz0640.bl.entities;

import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;

import java.util.ArrayList;

/**
 * Entidad que representa una categoría asignada a un ticket tras su análisis.
 * Contiene el nombre de la categoría detectada (ej. "Hardware", "Enojo") y las palabras
 * clave que detonaron esta clasificación.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class CategoriaTicket {

    private String nombre;
    private ArrayList<String> palabrasDetonantes;
    private TipoCategoria tipo;

    /**
     * Constructor para crear una nueva categoría con sus palabras clave.
     *
     * @param nombre Nombre de la categoría (ej: "Redes").
     * @param palabrasDetonantes Lista de palabras encontradas en el texto.
     */
    public CategoriaTicket(String nombre, ArrayList<String> palabrasDetonantes) {
        this.nombre = nombre;
        this.palabrasDetonantes = palabrasDetonantes;
    }

    /**
     * Obtiene el nombre de la categoría.
     * @return Nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la categoría.
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de palabras que causaron la clasificación.
     * @return Lista de palabras detonantes.
     */
    public ArrayList<String> getPalabrasDetonantes() {
        return palabrasDetonantes;
    }

    /**
     * Asigna la lista de palabras detonantes.
     * @param palabrasDetonantes Lista de palabras.
     */
    public void setPalabrasDetonantes(ArrayList<String> palabrasDetonantes) {
        this.palabrasDetonantes = palabrasDetonantes;
    }

    /**
     * Obtiene el tipo de categoría (Técnica o Emocional).
     * @return Tipo de categoría.
     */
    public TipoCategoria getTipo() {
        return tipo;
    }

    /**
     * Asigna el tipo de categoría.
     * @param tipo Tipo de categoría.
     */
    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }
}