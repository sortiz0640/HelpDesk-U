package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import java.util.List;

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

        // Contar palabras emocionales
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

    // CORRECCIÓN: Ajustar índices según el orden en ListaPalabrasTecnicas
    // El array tiene 39 palabras en total
    private int sumarPalabrasRedes(int[] vector) {
        // Índices 0-5: wifi, red, conexion, internet, redes, router
        int suma = 0;
        for (int i = 0; i <= 5 && i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    private int sumarPalabrasPlataforma(int[] vector) {
        // Índices 6-10: moodle, plataforma, virtual, curso, aula (solo hay 5, no 11)
        int suma = 0;
        for (int i = 6; i <= 10 && i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    private int sumarPalabrasCorreo(int[] vector) {
        // Índices 11-15: correo, email, outlook, contraseña, login
        int suma = 0;
        for (int i = 11; i <= 15 && i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    private int sumarPalabrasSoftware(int[] vector) {
        // Índices 16-20: software, programa, aplicacion, instalar, actualizar
        int suma = 0;
        for (int i = 16; i <= 20 && i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    private int sumarPalabrasHardware(int[] vector) {
        // Índices 21-25: hardware, computadora, teclado, mouse, monitor
        // NOTA: El array tiene más palabras después (impresora, laboratorio, etc.)
        int suma = 0;
        for (int i = 21; i <= 25 && i < vector.length; i++) {
            suma += vector[i];
        }
        // Agregar también: impresora (26)
        if (vector.length > 26) {
            suma += vector[26];
        }
        return suma;
    }
}
