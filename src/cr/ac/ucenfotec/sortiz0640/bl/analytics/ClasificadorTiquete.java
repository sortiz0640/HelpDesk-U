package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import java.util.List;
import java.util.Vector;

public class ClasificadorTiquete {

    private FormateadorTexto formateador;
    private VectorizadorTexto vectorizador;

    public ClasificadorTiquete() {
        this.formateador = new FormateadorTexto();
        this.vectorizador = new VectorizadorTexto();
    }

    public String establecerCategoriaTecnica(String descripcion) {
        var tokens = formateador.procesarTexto(descripcion);
        var vector = vectorizador.vectorizarTecnicas(tokens);

        return clasificarCategoriaTecnica(vector, tokens);
    }

    public String establecerCategoriaEmocional(String descripcion) {
        var tokens = formateador.procesarTexto(descripcion);
        var vector = vectorizador.vectorizarEmocional(tokens);

        return clasificarCategoriaEmocional(vector, tokens);
    }

    public String clasificarCategoriaTecnica(int[] vector, List<String> tokens) {
        int contadorRedes = sumarPalabrasRedes(vector);
        int contadorPlataforma = sumarPalabrasPlataforma(vector);
        int contadorCorreo = sumarPalabrasCorreo(vector);
        int contadorSoftware = sumarPalabrasSoftware(vector);
        int contadorHardware = sumarPalabrasHardware(vector);

        // Determinar categoría con mayor puntuación
        int maxScore = Math.max(Math.max(contadorRedes, contadorPlataforma),
                Math.max(contadorCorreo, Math.max(contadorSoftware, contadorHardware)));

        if (maxScore == 0) return "OTROS";

        if (contadorRedes == maxScore) return "REDES_CONECTIVIDAD";
        if (contadorPlataforma == maxScore) return "PLATAFORMA_VIRTUAL";
        if (contadorCorreo == maxScore) return "CORREO_ELECTRONICO";
        if (contadorSoftware == maxScore) return "SOFTWARE_APLICACIONES";
        if (contadorHardware == maxScore) return "HARDWARE_EQUIPOS";

        return "OTROS";
    }

    private String clasificarCategoriaEmocional(int[] vector, List<String> tokens) {
        int emocionPositiva = 0;
        int emocionNegativa = 0;
        int urgencia = 0;

        // Contar palabras emocionales (simplificado)
        for (String token : tokens) {
            switch (token) {
                case "urgente", "emergencia", "inmediato", "rapido" -> urgencia++;
                case "frustrado", "molesto", "enojado", "preocupado", "estresado" -> emocionNegativa++;
                case "gracias", "excelente", "bueno", "funciona", "perfecto", "satisfecho" -> emocionPositiva++;
            }
        }

        if (urgencia > 2 || emocionNegativa > 1) return "URGENTE_NEGATIVO";
        if (emocionPositiva > 1) return "POSITIVO";
        if (emocionNegativa > 0) return "LEVEMENTE_NEGATIVO";
        if (urgencia > 0) return "NEUTRO_URGENTE";

        return "NEUTRO";
    }
    // Métodos auxiliares para sumar palabras por categoría
    private int sumarPalabrasRedes(int[] vector) {
        // Índices de palabras relacionadas con redes
        return vector[0] + vector[1] + vector[2] + vector[3] + vector[4] + vector[5];
    }

    private int sumarPalabrasPlataforma(int[] vector) {
        // Índices de palabras relacionadas con plataforma virtual
        return vector[6] + vector[7] + vector[8] + vector[9] + vector[10];
    }

    // Implementar métodos similares para otras categorías...
    private int sumarPalabrasCorreo(int[] vector) {
        return vector[11] + vector[12] + vector[13] + vector[14] + vector[15];
    }

    private int sumarPalabrasSoftware(int[] vector) {
        return vector[16] + vector[17] + vector[18] + vector[19] + vector[20];
    }

    private int sumarPalabrasHardware(int[] vector) {
        return vector[21] + vector[22] + vector[23] + vector[24] + vector[25];
    }
}

