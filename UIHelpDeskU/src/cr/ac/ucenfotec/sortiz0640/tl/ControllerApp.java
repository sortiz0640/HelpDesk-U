package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.ui.ViewApp;
import cr.ac.ucenfotec.sortiz0640.util.UI;

import java.io.IOException;

public class ControllerApp {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewApp app = new ViewApp();

    private ControllerUsuario usuario = new ControllerUsuario();
    private ControllerTicket ticket = new ControllerTicket();
    private ControllerDepartamento departamento = new ControllerDepartamento();

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
            case 1: usuario.start(); break;
            case 2: departamento.start(); break;
            case 3: ticket.start(); break;
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }
}
