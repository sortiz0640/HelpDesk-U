package cr.ac.ucenfotec.sortiz0640.bl.analytics;

public class CategorizarTiquete {

    private ClasificadorTiquete clasificador;
    private FormateadorTexto formateador;

    public CategorizarTiquete() {
        this.clasificador = new ClasificadorTiquete();
        this.formateador = new FormateadorTexto();
    }

    // Método principal: categoriza y retorna categoría técnica y emocional
    public String[] inicCategorizacion(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);

        String categoriaTecnica = clasificador.obtenerCategoriaTecnica(tokens);
        String categoriaEmocional = clasificador.obtenerCategoriaEmocional(tokens);

        String categoriaFinal = categoriaTecnica + "-" + categoriaEmocional;

        return new String[]{categoriaTecnica, categoriaEmocional, categoriaFinal};
    }

    // Obtiene solo la categoría técnica
    public String obtenerCategoriaTecnica(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerCategoriaTecnica(tokens);
    }

    // Obtiene solo la categoría emocional
    public String obtenerCategoriaEmocional(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerCategoriaEmocional(tokens);
    }

    // Obtiene las palabras que causaron la clasificación técnica
    public String[] obtenerPalabrasDetonantesTocnicas(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerPalabrasDetonantesTecnicas(tokens);
    }

    // Obtiene las palabras que causaron la clasificación emocional
    public String[] obtenerPalabrasDetonantesEmocionales(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.obtenerPalabrasDetonantesEmocionales(tokens);
    }

}