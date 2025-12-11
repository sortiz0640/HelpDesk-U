package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.ClasificadorTiquete;
import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.FormateadorTexto;
import cr.ac.ucenfotec.sortiz0640.bl.entities.CategoriaTicket;

import java.sql.SQLException;

/**
 * Clase principal del módulo de análisis de texto.
 * Actúa como fachada para el proceso de categorización de un ticket.
 * Utiliza un Formateador de Texto y un Clasificador para obtener las categorías
 * técnica y emocional a partir de la descripción de un ticket.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class CategorizarTiquete {

    private ClasificadorTiquete clasificador;
    private FormateadorTexto formateador;

    /**
     * Constructor que inicializa las dependencias (Clasificador y Formateador).
     *
     * @throws SQLException Si ocurre un error al inicializar el Clasificador (acceso a la BD).
     * @throws ClassNotFoundException Si no se encuentra el driver de la BD.
     */
    public CategorizarTiquete() throws SQLException, ClassNotFoundException {
        this.clasificador = new ClasificadorTiquete();
        this.formateador = new FormateadorTexto();
    }

    /**
     * Procesa la descripción del ticket, la tokeniza y la clasifica para obtener la Categoría Técnica.
     *
     * @param descripcionTiquete El texto completo del ticket.
     * @return Objeto CategoriaTicket con el nombre de la categoría técnica detectada y sus palabras detonantes.
     */
    public CategoriaTicket obtenerCategoriaTecnica(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.getCategoriaTecnica(tokens);

    }

    /**
     * Procesa la descripción del ticket, la tokeniza y la clasifica para obtener la Categoría Emocional.
     *
     * @param descripcionTiquete El texto completo del ticket.
     * @return Objeto CategoriaTicket con el nombre de la emoción detectada y sus palabras detonantes.
     */
    public CategoriaTicket obtenerCategoriaEmocional(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.getCategoriaEmocional(tokens);
    }
}