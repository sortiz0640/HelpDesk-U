package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.ClasificadorTiquete;
import cr.ac.ucenfotec.sortiz0640.bl.analytics.logic.FormateadorTexto;
import cr.ac.ucenfotec.sortiz0640.bl.entities.CategoriaTicket;

import java.sql.SQLException;

public class CategorizarTiquete {

    private ClasificadorTiquete clasificador;
    private FormateadorTexto formateador;

    public CategorizarTiquete() throws SQLException, ClassNotFoundException {
        this.clasificador = new ClasificadorTiquete();
        this.formateador = new FormateadorTexto();
    }

    public CategoriaTicket obtenerCategoriaTecnica(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.getCategoriaTecnica(tokens);

    }

    public CategoriaTicket obtenerCategoriaEmocional(String descripcionTiquete) {
        String[] tokens = formateador.procesarTexto(descripcionTiquete);
        return clasificador.getCategoriaEmocional(tokens);
    }




}