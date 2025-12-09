package cr.ac.ucenfotec.sortiz0640.bl.analytics.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.CategoriaTicket;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;
import cr.ac.ucenfotec.sortiz0640.dl.CategoriasDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClasificadorTiquete {

    private CategoriasDAO db;
    ConfigPropertiesReader config;

    public ClasificadorTiquete() throws SQLException, ClassNotFoundException {
        config = new ConfigPropertiesReader();
        db = new CategoriasDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    public HashMap<String, ArrayList<String>> clasificar(String[] tokens, HashMap<String, ArrayList<String>> diccionario) {

        // Hashmap resultado
        HashMap<String, ArrayList<String>> res = new HashMap<>();

        for  (String token : tokens) {
            String categoria = buscarCategoriaEnHashMap(token, diccionario);
            if (categoria != null) {
                if (!res.containsKey(categoria)) {
                    res.put(categoria, new ArrayList<>());
                }
                res.get(categoria).add(token);
            }
        }

        return res;

    }

    private String buscarCategoriaEnHashMap(String token,  HashMap<String, ArrayList<String>> hm) {

        for (String key : hm.keySet()) {
            if (hm.get(key).contains(token)) {
                return key;
            }
        }

        return null;

    }

    public CategoriaTicket getFrecuencia(HashMap<String, ArrayList<String>> res) {

        if (res == null || res.isEmpty()) {
            ArrayList<String> vacio = new ArrayList<>();
            return new CategoriaTicket("OTRO", vacio);
        }

        String[] vectorCategoria = res.keySet().toArray(new String[0]);
        int[] vectorFrecuencias = new int[vectorCategoria.length];

        for (int i = 0; i < vectorCategoria.length; i++) {
            vectorFrecuencias[i] = res.get(vectorCategoria[i]).size();
        }

        int maxFrecuencia = -1;
        int indiceMax = -1;

        for (int i = 0; i < vectorFrecuencias.length; i++) {
            if (vectorFrecuencias[i] > maxFrecuencia) {
                maxFrecuencia = vectorFrecuencias[i];
                indiceMax = i;
            }
        }

        return new CategoriaTicket(
                vectorCategoria[indiceMax],
                res.get(vectorCategoria[indiceMax])
        );
    }


    public CategoriaTicket getCategoriaTecnica(String[] tokens){
        CategoriaTicket categoriaTicket = null;
        try {
            categoriaTicket = getFrecuencia(
                this.clasificar(tokens, db.obtenerCategoriasTecnicas())
            );

            categoriaTicket.setTipo(TipoCategoria.TECNICA);


        }  catch (SQLException ex) {
            Logger.getLogger(ClasificadorTiquete.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categoriaTicket;
    }

    public CategoriaTicket getCategoriaEmocional(String[] tokens){
        CategoriaTicket categoriaTicket = null;
        try {
            categoriaTicket = getFrecuencia(
                this.clasificar(tokens, db.obtenerCategoriasEmocionales())
            );

            categoriaTicket.setTipo(TipoCategoria.EMOCIONAL);


        }  catch (SQLException ex) {
            Logger.getLogger(ClasificadorTiquete.class.getName()).log(Level.SEVERE, null, ex);
        }

        return categoriaTicket;
    }
}