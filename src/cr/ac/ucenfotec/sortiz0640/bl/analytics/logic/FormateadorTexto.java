package cr.ac.ucenfotec.sortiz0640.bl.analytics.logic;

import java.util.ArrayList;

/**
 * Clase responsable de la preparación del texto de un ticket para su análisis.
 * Realiza la limpieza, tokenización y eliminación de "stop words" (palabras comunes)
 * para dejar solo las palabras clave relevantes.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class FormateadorTexto {

    private String[] stopWords = {
            "el", "la", "los", "las", "de", "del",
            "y", "o", "a", "en", "un", "una", "con",
            "para", "por", "se", "es", "son", "como", "muy"
    };

    /**
     * Método principal que orquesta el proceso completo de procesamiento de texto.
     * 1. Limpia caracteres especiales.
     * 2. Divide en palabras (tokens).
     * 3. Elimina "stop words".
     *
     * @param texto El texto completo a procesar (descripción del ticket).
     * @return Un array de Strings con los tokens (palabras clave) filtrados y listos para clasificar.
     */
    public String[] procesarTexto(String texto) {
        String textoLimpio = limpiarTexto(texto);
        String[] tokens = dividirEnPalabras(textoLimpio);
        String[] tokensFiltrados = eliminarStopWords(tokens);

        return tokensFiltrados;
    }

    /**
     * Limpia el texto, manteniendo solo letras y espacios, y convierte todo a minúsculas.
     * Reemplaza cualquier otro caracter con un espacio.
     *
     * @param texto El texto original.
     * @return Texto limpio y en minúsculas.
     */
    private String limpiarTexto(String texto) {
        String textoLimpio = "";

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            // Solo mantener letras y espacios
            if (Character.isLetter(caracter) || caracter == ' ') {
                textoLimpio += Character.toLowerCase(caracter);
            } else {
                textoLimpio += ' ';
            }
        }

        return textoLimpio.trim();
    }

    /**
     * Divide el texto limpio en un array de palabras (tokens) separadas por espacios.
     *
     * @param texto Texto limpio (solo letras y espacios).
     * @return Array de Strings con las palabras individuales.
     */
    private String[] dividirEnPalabras(String texto) {
        ArrayList<String> palabras = new ArrayList<>();
        String palabraActual = "";

        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);

            if (caracter == ' ') {
                if (palabraActual.length() > 0) {
                    palabras.add(palabraActual);
                    palabraActual = "";
                }
            } else {
                palabraActual += caracter;
            }
        }

        // Agregar la última palabra si existe
        if (palabraActual.length() > 0) {
            palabras.add(palabraActual);
        }

        return convertirListaAArray(palabras);
    }

    /**
     * Elimina las palabras de la lista de `stopWords` y las palabras de 2 o menos caracteres.
     *
     * @param tokens Array de palabras.
     * @return Array de palabras filtradas (tokens relevantes).
     */
    private String[] eliminarStopWords(String[] tokens) {
        ArrayList<String> tokensFiltrados = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            boolean esStopWord = false;

            // Verificar si es una stop word
            for (int j = 0; j < stopWords.length; j++) {
                if (token.equals(stopWords[j])) {
                    esStopWord = true;
                    break;
                }
            }

            // Solo agregar si no es stop word y tiene más de 2 caracteres
            if (!esStopWord && token.length() > 2) {
                tokensFiltrados.add(token);
            }
        }

        return convertirListaAArray(tokensFiltrados);
    }

    /**
     * Método auxiliar para convertir un ArrayList<String> a un String[].
     *
     * @param lista La lista de strings.
     * @return El array de strings resultante.
     */
    private String[] convertirListaAArray(ArrayList<String> lista) {
        String[] array = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i);
        }

        return array;
    }
}