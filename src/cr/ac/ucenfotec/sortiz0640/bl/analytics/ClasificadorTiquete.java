package cr.ac.ucenfotec.sortiz0640.bl.analytics;

import java.util.ArrayList;

public class ClasificadorTiquete {

    private ListaPalabrasTecnicas listaTecnicas;
    private ListaPalabrasEmociones listaEmociones;

    public ClasificadorTiquete() {
        this.listaTecnicas = new ListaPalabrasTecnicas();
        this.listaEmociones = new ListaPalabrasEmociones();
    }

    // ========== CLASIFICACIÓN TÉCNICA ==========

    public String obtenerCategoriaTecnica(String[] tokens) {
        int contadorRedes = contarPalabrasRedes(tokens);
        int contadorSoftware = contarPalabrasSoftware(tokens);
        int contadorHardware = contarPalabrasHardware(tokens);
        int contadorPlataforma = contarPalabrasPlataforma(tokens);
        int contadorCorreo = contarPalabrasCorreo(tokens);
        int contadorImpresion = contarPalabrasImpresion(tokens);
        int contadorLaboratorio = contarPalabrasLaboratorio(tokens);
        int contadorBaseDatos = contarPalabrasBaseDatos(tokens);
        int contadorSeguridad = contarPalabrasSeguridad(tokens);
        int contadorSistemaOperativo = contarPalabrasSistemaOperativo(tokens);

        // Encontrar la categoría con mayor puntaje
        int maxPuntaje = 0;
        String categoria = "OTROS";

        if (contadorRedes > maxPuntaje) {
            maxPuntaje = contadorRedes;
            categoria = "REDES";
        }

        if (contadorSoftware > maxPuntaje) {
            maxPuntaje = contadorSoftware;
            categoria = "SOFTWARE";
        }

        if (contadorHardware > maxPuntaje) {
            maxPuntaje = contadorHardware;
            categoria = "HARDWARE";
        }

        if (contadorPlataforma > maxPuntaje) {
            maxPuntaje = contadorPlataforma;
            categoria = "PLATAFORMA";
        }

        if (contadorCorreo > maxPuntaje) {
            maxPuntaje = contadorCorreo;
            categoria = "CORREO";
        }

        if (contadorImpresion > maxPuntaje) {
            maxPuntaje = contadorImpresion;
            categoria = "IMPRESION";
        }

        if (contadorLaboratorio > maxPuntaje) {
            maxPuntaje = contadorLaboratorio;
            categoria = "LABORATORIO";
        }

        if (contadorBaseDatos > maxPuntaje) {
            maxPuntaje = contadorBaseDatos;
            categoria = "BASE_DATOS";
        }

        if (contadorSeguridad > maxPuntaje) {
            maxPuntaje = contadorSeguridad;
            categoria = "SEGURIDAD";
        }

        if (contadorSistemaOperativo > maxPuntaje) {
            maxPuntaje = contadorSistemaOperativo;
            categoria = "SISTEMA_OPERATIVO";
        }

        return categoria;
    }

    // ========== CLASIFICACIÓN EMOCIONAL ==========

    public String obtenerCategoriaEmocional(String[] tokens) {
        int contadorMolesto = contarPalabrasMolestas(tokens);
        int contadorFeliz = contarPalabrasFelices(tokens);

        if (contadorMolesto > contadorFeliz && contadorMolesto > 0) {
            return "MOLESTO";
        }

        if (contadorFeliz > contadorMolesto && contadorFeliz > 0) {
            return "FELIZ";
        }

        return "NEUTRO";
    }

    // ========== PALABRAS DETONANTES ==========

    public String[] obtenerPalabrasDetonantesTecnicas(String[] tokens) {
        ArrayList<String> palabrasEncontradas = new ArrayList<>();
        String[] palabrasTecnicas = listaTecnicas.getPalabrasTecnicas();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            for (int j = 0; j < palabrasTecnicas.length; j++) {
                if (token.equals(palabrasTecnicas[j])) {
                    palabrasEncontradas.add(token);
                }
            }
        }

        return convertirListaAArray(palabrasEncontradas);
    }

    public String[] obtenerPalabrasDetonantesEmocionales(String[] tokens) {
        ArrayList<String> palabrasEncontradas = new ArrayList<>();
        String[] palabrasEmocionales = listaEmociones.getPalabrasEmociones();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            for (int j = 0; j < palabrasEmocionales.length; j++) {
                if (token.equals(palabrasEmocionales[j])) {
                    palabrasEncontradas.add(token);
                }
            }
        }

        return convertirListaAArray(palabrasEncontradas);
    }

    // ========== CONTADORES POR CATEGORÍA TÉCNICA ==========

    private int contarPalabrasRedes(String[] tokens) {
        String[] palabrasRedes = {"wifi", "red", "conexion", "internet", "redes", "router"};
        return contarCoincidencias(tokens, palabrasRedes);
    }

    private int contarPalabrasSoftware(String[] tokens) {
        String[] palabrasSoftware = {"software", "programa", "aplicacion", "instalar", "actualizar"};
        return contarCoincidencias(tokens, palabrasSoftware);
    }

    private int contarPalabrasHardware(String[] tokens) {
        String[] palabrasHardware = {"hardware", "computadora", "teclado", "mouse", "monitor"};
        return contarCoincidencias(tokens, palabrasHardware);
    }

    private int contarPalabrasPlataforma(String[] tokens) {
        String[] palabrasPlataforma = {"moodle", "plataforma", "virtual", "curso", "aula", "clase"};
        return contarCoincidencias(tokens, palabrasPlataforma);
    }

    private int contarPalabrasCorreo(String[] tokens) {
        String[] palabrasCorreo = {"correo", "email", "outlook", "contraseña", "login"};
        return contarCoincidencias(tokens, palabrasCorreo);
    }

    private int contarPalabrasImpresion(String[] tokens) {
        String[] palabrasImpresion = {"impresora", "imprimir", "papel", "toner", "escaner"};
        return contarCoincidencias(tokens, palabrasImpresion);
    }

    private int contarPalabrasLaboratorio(String[] tokens) {
        String[] palabrasLaboratorio = {"laboratorio", "salon", "proyector", "pantalla"};
        return contarCoincidencias(tokens, palabrasLaboratorio);
    }

    private int contarPalabrasBaseDatos(String[] tokens) {
        String[] palabrasBaseDatos = {"biblioteca", "base", "datos", "sql", "servidor"};
        return contarCoincidencias(tokens, palabrasBaseDatos);
    }

    private int contarPalabrasSeguridad(String[] tokens) {
        String[] palabrasSeguridad = {"virus", "malware", "antivirus", "firewall", "hackeo"};
        return contarCoincidencias(tokens, palabrasSeguridad);
    }

    private int contarPalabrasSistemaOperativo(String[] tokens) {
        String[] palabrasSistema = {"windows", "linux", "mac", "sistema", "operativo"};
        return contarCoincidencias(tokens, palabrasSistema);
    }

    // ========== CONTADORES EMOCIONALES ==========

    private int contarPalabrasMolestas(String[] tokens) {
        String[] palabrasMolestas = {"frustrado", "molesto", "enojado", "preocupado",
                "estresado", "urgente", "emergencia", "problema",
                "error", "falla"};
        return contarCoincidencias(tokens, palabrasMolestas);
    }

    private int contarPalabrasFelices(String[] tokens) {
        String[] palabrasFelices = {"gracias", "excelente", "bueno", "funciona",
                "perfecto", "satisfecho", "genial", "bien"};
        return contarCoincidencias(tokens, palabrasFelices);
    }

    // ========== MÉTODOS AUXILIARES ==========

    private int contarCoincidencias(String[] tokens, String[] palabrasClave) {
        int contador = 0;

        for (int i = 0; i < tokens.length; i++) {
            for (int j = 0; j < palabrasClave.length; j++) {
                if (tokens[i].equals(palabrasClave[j])) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private String[] convertirListaAArray(ArrayList<String> lista) {
        String[] array = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            array[i] = lista.get(i);
        }

        return array;
    }
}