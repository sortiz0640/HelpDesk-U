package cr.ac.ucenfotec.sortiz0640.bl.analytics;

public class ListaPalabrasEmociones {

    private String[] palabrasEmociones = {
            // MOLESTO
            "frustrado", "molesto", "enojado", "preocupado", "estresado",
            "urgente", "emergencia", "problema", "error", "falla",
            "mal", "terrible", "pesimo", "horrible",

            // FELIZ
            "gracias", "excelente", "bueno", "funciona", "perfecto",
            "satisfecho", "genial", "bien", "contento", "agradecido"
    };

    public String[] getPalabrasEmociones() {
        return palabrasEmociones;
    }
}