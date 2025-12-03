package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.ClasificadorTiquete;
import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.FormateadorTexto;

public class CategorizarTiquete {

    private ClasificadorTiquete clasificador;
    private FormateadorTexto formateador;

    public CategorizarTiquete() {
        this.clasificador = new ClasificadorTiquete();
        this.formateador = new FormateadorTexto();
    }

    public String obtenerCategoriaTecnica(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerCategoriaTecnica(tokens);
    }

    public String obtenerCategoriaEmocional(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerCategoriaEmocional(tokens);
    }

    public String[] obtenerPalabrasDetonantesTocnicas(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerPalabrasDetonantesTecnicas(tokens);
    }

    public String[] obtenerPalabrasDetonantesEmocionales(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerPalabrasDetonantesEmocionales(tokens);
    }

}