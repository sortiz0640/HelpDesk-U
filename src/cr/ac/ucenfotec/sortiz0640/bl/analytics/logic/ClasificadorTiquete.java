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

/**
 * Clase responsable de clasificar los tokens de un ticket contra los diccionarios
 * (palabras detonantes) de la base de datos para determinar la categoría
 * de mayor frecuencia.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class ClasificadorTiquete {

    private CategoriasDAO db;
    ConfigPropertiesReader config;

    /**
     * Constructor que inicializa la capa de acceso a datos para las categorías.
     *
     * @throws SQLException Si ocurre un error de conexión SQL.
     * @throws ClassNotFoundException Si no se encuentra el driver de la BD.
     */
    public ClasificadorTiquete() throws SQLException, ClassNotFoundException {
        config = new ConfigPropertiesReader();
        db = new CategoriasDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    /**
     * Clasifica un conjunto de tokens (palabras limpias) con respecto a un diccionario
     * de categorías (ej. técnicas o emocionales).
     *
     * @param tokens Array de palabras clave del ticket.
     * @param diccionario HashMap donde la clave es el nombre de la categoría y el valor es la lista de palabras detonantes.
     * @return HashMap resultado donde la clave es la categoría y el valor es la lista de tokens que pertenecen a esa categoría.
     */
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

    /**
     * Busca un token específico dentro de las listas de palabras de todas las categorías del diccionario.
     *
     * @param token Palabra a buscar.
     * @param diccionario Diccionario de categorías y palabras.
     * @return El nombre de la categoría a la que pertenece el token, o null si no se encuentra.
     */
    private String buscarCategoriaEnHashMap(String token, HashMap<String, ArrayList<String>> diccionario) {
        // Recorre todas las llaves (Categorias) del mapa una por una
        for (String key : diccionario.keySet()) {
            // hm.get(key) obtiene la lista de palabras de esa categoría
            if (diccionario.get(key).contains(token)) {
                return key; // returns nombre categoria
            }
        }
        return null;
    }

    /**
     * Determina la CategoríaTicket de mayor frecuencia a partir del resultado de la clasificación.
     * Elige la categoría que haya tenido más tokens asociados.
     *
     * @param res HashMap de categorías y tokens asociados.
     * @return Objeto CategoriaTicket con el nombre de la categoría de mayor frecuencia y sus tokens.
     */
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

    /**
     * Obtiene la categoría técnica dominante para un conjunto de tokens.
     * Llama al DAO para obtener el diccionario técnico y luego determina la frecuencia.
     *
     * @param tokens Array de tokens limpios del ticket.
     * @return CategoriaTicket con el resultado técnico.
     */
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

    /**
     * Obtiene la categoría emocional dominante para un conjunto de tokens.
     * Llama al DAO para obtener el diccionario emocional y luego determina la frecuencia.
     *
     * @param tokens Array de tokens limpios del ticket.
     * @return CategoriaTicket con el resultado emocional.
     */

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