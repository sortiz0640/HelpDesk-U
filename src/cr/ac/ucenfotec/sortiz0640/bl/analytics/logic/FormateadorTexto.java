package cr.ac.ucenfotec.sortiz0640.bl.analytics.logic;

import java.util.ArrayList;

public class FormateadorTexto {

    private String[] stopWords = {
            "el", "la", "los", "las", "de", "del",
            "y", "o", "a", "en", "un", "una", "con",
            "para", "por", "se", "es", "son", "como", "muy"
    };

    // Método principal que procesa el texto completo
    public String[] procesarTexto(String texto) {
        String textoLimpio = limpiarTexto(texto);
        String[] tokens = dividirEnPalabras(textoLimpio);
        String[] tokensFiltrados = eliminarStopWords(tokens);

        return tokensFiltrados;
    }

    // Limpia caracteres especiales y convierte a minúsculas
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

    // Divide el texto en palabras individuales
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

    // Elimina palabras comunes sin significado (stop words)
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

    // Convierte ArrayList a array normal
    private String[] convertirListaAArray(ArrayList<String> lista) {
        String[] array = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i);
        }

        return array;
    }
}