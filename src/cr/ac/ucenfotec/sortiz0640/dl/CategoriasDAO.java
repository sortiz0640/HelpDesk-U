package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.dbaccess.AccessDB;
import cr.ac.ucenfotec.dbaccess.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoriasDAO {

    private AccessDB DATA_ACCESS;

    public CategoriasDAO(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
        this.DATA_ACCESS = Connector.getDataAccess(driver, url, username, password);
    }


    public HashMap<String, ArrayList<String>> obtenerCategoriasTecnicas() throws SQLException {

        HashMap<String, ArrayList<String>> palabrasTecnicas = new HashMap<>();
        String query = "SELECT pt.palabra, c.categoria FROM palabras_tecnicas pt " +
                "INNER JOIN categorias c ON pt.idCategoria = c.idCategoria";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {

            String palabra = res.getString("palabra");
            String categoria = res.getString("categoria");

            if (!palabrasTecnicas.containsKey(categoria)) {
                palabrasTecnicas.put(categoria, new ArrayList<>());
            }

            palabrasTecnicas.get(categoria).add(palabra);
        }

        return palabrasTecnicas;
    }

    public HashMap<String, ArrayList<String>> obtenerCategoriasEmocionales() throws SQLException {

        HashMap<String, ArrayList<String>> palabrasEmocionales = new HashMap<>();
        String query = "SELECT palabra, tipo_emocion FROM palabras_emociones";
        ResultSet res = DATA_ACCESS.ejectuarRS(query);

        while (res.next()) {

            String palabra = res.getString("palabra");
            String categoria = res.getString("tipo_emocion");

            if (!palabrasEmocionales.containsKey(categoria)) {
                palabrasEmocionales.put(categoria, new ArrayList<>());
            }

            palabrasEmocionales.get(categoria).add(palabra);
        }

        return palabrasEmocionales;
    }
}
