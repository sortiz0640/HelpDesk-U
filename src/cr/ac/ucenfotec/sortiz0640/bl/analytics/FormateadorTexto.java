package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormateadorTexto {

    private List<String> stopWords = Arrays.asList(
            "el", "la", "los", "las", "de", "del",
            "y", "o", "a", "en", "un", "una", "con", "para", "por", "se", "es", "son",
            "como", "muy"
    );

    public String eliminarCaractEspeciales(String texto) {
        return texto.replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]", "").toLowerCase().trim();
    }

    public List<String> tokenizar(String texto) {
        return Arrays.asList(texto.split("\\s+"));
    }

    public List<String> eliminarStopWords(List<String> tokens) {

        // token: palabra
        // tokens: lista de palabras

        List<String> tokensFiltrados = new ArrayList<>();
        for (String t: tokens) {
            if (!stopWords.contains(t) && t.length() > 2) {
                tokensFiltrados.add(t);
            }
        }

        return tokensFiltrados;
    }

    public List<String> procesarTexto(String texto) {
        String textoLimpio = eliminarCaractEspeciales(texto);
        List<String> tokensFiltrados = tokenizar(textoLimpio);
        return eliminarStopWords(tokensFiltrados);
    }
}
