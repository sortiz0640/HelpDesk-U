package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.ui.ViewDepartamento;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import cr.ac.ucenfotec.sortiz0640.util.Validations;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerDepartamento {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewDepartamento app = new ViewDepartamento();
    private GestorDepartamento g;
    private GestorSesion sesion;
    private Validations validator = new Validations();

    public ControllerDepartamento(GestorDepartamento g, GestorSesion sesion) {
        this.g = g;
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
            case 1: registrar(); break;
            case 2: eliminarPorCorreo(); break;
            case 3: listarPorCorreo(); break;
            case 4: listarTodos(); break;
            case 0: break;
            default: interfaz.imprimirMensaje("[INFO] Opción no válida. Intente nuevamente! \n");
        }
    }

    public void registrar() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        String nombre = validator.nombre();
        String descripcion = validator.descripcion();
        String correo = validator.correo();

        interfaz.imprimirMensaje(g.agregar(nombre, descripcion, correo));

    }

    public void eliminarPorCorreo() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        String correo = validator.correo();
        interfaz.imprimirMensaje(g.eliminarPorCorreo(correo));

    }

    public void listarPorCorreo() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        String correo = validator.correo();
        interfaz.imprimirMensaje("\n" +g.listarPorCorreo(correo));

    }

    public void listarTodos() {

        ArrayList<String> lista = g.listarTodos();

        if (lista == null || lista.isEmpty()) {
            interfaz.imprimirMensaje("[INFO] No existen departamentos registrados\n");
            return;
        }

        interfaz.imprimirMensaje("[INFO] Lista de departamentos:\n");
        for (String u : lista) {
            interfaz.imprimirMensaje(u);
        }
    }

}
