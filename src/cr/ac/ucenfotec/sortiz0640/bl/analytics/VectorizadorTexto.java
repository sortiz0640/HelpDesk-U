package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VectorizadorTexto {

    private Map<String, Integer> vocabularioTecnico;
    private Map<String, Integer> vocabularioEmocional;

    private ListaPalabrasEmociones emociones;
    private ListaPalabrasTecnicas tecnicas;

    // CORRECCIÓN: Inicializar en el constructor
    public VectorizadorTexto() {
        this.emociones = new ListaPalabrasEmociones();
        this.tecnicas = new ListaPalabrasTecnicas();
        inicVocabularios();  // IMPORTANTE: Inicializar automáticamente
    }

    private void inicVocabularios() {
        vocabularioTecnico = new HashMap<>();
        vocabularioEmocional = new HashMap<>();

        String[] listaEmociones = emociones.getPalabrasEmociones();
        String[] listaTecnicas = tecnicas.getPalabrasTecnicas();

        for (int i = 0; i < listaEmociones.length; i++) {
            vocabularioEmocional.put(listaEmociones[i], i);
        }

        for (int i = 0; i < listaTecnicas.length; i++) {
            vocabularioTecnico.put(listaTecnicas[i], i);
        }
    }

    public int[] vectorizarEmocional(List<String> tokens) {
        int[] vector = new int[vocabularioEmocional.size()];
        for (String token : tokens) {
            if (vocabularioEmocional.containsKey(token)) {
                int index = vocabularioEmocional.get(token);
                vector[index]++;
            }
        }
        return vector;
    }

    public int[] vectorizarTecnicas(List<String> tokens) {
        int[] vector = new int[vocabularioTecnico.size()];
        for (String token : tokens) {
            if (vocabularioTecnico.containsKey(token)) {
                int index = vocabularioTecnico.get(token);
                vector[index]++;
            }
        }
        return vector;
    }
}