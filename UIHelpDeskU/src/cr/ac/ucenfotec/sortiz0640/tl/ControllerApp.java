package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.ui.ViewApp;
import cr.ac.ucenfotec.sortiz0640.util.UI;

import java.io.IOException;

public class ControllerApp {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewApp app = new ViewApp();

    private ControllerUsuario usuario;
    private ControllerTicket ticket;
    private ControllerDepartamento departamento;
    private GestorSesion sesion;

    public ControllerApp(ControllerUsuario usuario, ControllerTicket ticket, ControllerDepartamento departamento, GestorSesion sesion)  {
        this.usuario = usuario;
        this.ticket = ticket;
        this.departamento = departamento;
        this.sesion = sesion;
    }

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
            case 0: sesion.cerrarSesion(); break;
            default: interfaz.imprimirMensaje("[INFO] Opción no válida. Intente nuevamente! \n");
        }
    }
}
