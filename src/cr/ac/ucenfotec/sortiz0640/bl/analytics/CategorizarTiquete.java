package cr.ac.ucenfotec.sortiz0640.bl.analytics;

public class CategorizarTiquete {

    private ClasificadorTiquete clasificador;

    public CategorizarTiquete() {
        this.clasificador = new ClasificadorTiquete();
    }

    public String[] inicCategorizacion(String descripcionTiquete) {
        String categoriaTecnica = clasificador.establecerCategoriaTecnica(descripcionTiquete);
        String estadoEmocional = clasificador.establecerCategoriaEmocional(descripcionTiquete);

        return new String[]{categoriaTecnica, estadoEmocional};
    }
}