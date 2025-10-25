package cr.ac.ucenfotec.sortiz0640.tl;

import cr.ac.ucenfotec.sortiz0640.ui.ViewApp;
import cr.ac.ucenfotec.sortiz0640.ui.ViewUsuario;
import cr.ac.ucenfotec.sortiz0640.util.UI;

import java.io.IOException;

public class ControllerUsuario {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewUsuario app = new ViewUsuario();

    public void start() throws IOException {
        int opcion = -1;
        do {
            app.mostrarMenu();
            opcion = interfaz.leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    public void procesarOpcion(int opcion) throws IOException {
        switch (opcion) {
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }
}
