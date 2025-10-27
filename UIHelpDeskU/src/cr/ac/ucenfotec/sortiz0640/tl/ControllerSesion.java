package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.ui.ViewSesion;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import cr.ac.ucenfotec.sortiz0640.util.Validations;
import java.io.IOException;

public class ControllerSesion {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewSesion app = new ViewSesion();
    private Validations validator = new Validations();
    private GestorSesion sesion;
    private ControllerApp controllerApp;

    public ControllerSesion(GestorSesion sesion, ControllerApp controllerApp) {
        this.sesion = sesion;
        this.controllerApp = controllerApp;
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
            case 1: iniciarSesion(); break;
            case 0: break;
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n"); break;
        }
    }

    public void iniciarSesion() throws IOException {

        String correo = validator.correo();
        String password = validator.password();

        boolean estado = sesion.iniciarSesion(correo, password);

        if (!estado) {
            interfaz.imprimirMensaje("El usuario o la contrasena no son correctos. Intente nuevamente");
            return;
        }

        interfaz.imprimirMensaje("Sesion iniciada correctamente");

        // Arranca el menu principal
        controllerApp.start();
    }
}
