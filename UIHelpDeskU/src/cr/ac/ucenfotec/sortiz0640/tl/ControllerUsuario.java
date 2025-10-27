package cr.ac.ucenfotec.sortiz0640.tl;

import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.sortiz0640.ui.ViewUsuario;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import cr.ac.ucenfotec.sortiz0640.util.Validations;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerUsuario {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewUsuario app = new ViewUsuario();
    private GestorUsuario g;
    private GestorSesion sesion;
    Validations validator = new Validations();

    public ControllerUsuario(GestorUsuario g, GestorSesion sesion) {
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
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }

    public void registrar() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("El usuario no tiene permisos para ejecutar esta opción");
            return;
        }

        String nombre = validator.nombre();
        String apellidos = validator.nombre();
        String correo = validator.correo();
        String password = validator.password();
        int rol = validator.rol();

        interfaz.imprimirMensaje(g.agregar(nombre, apellidos, correo, password, rol));

    }

    public void eliminarPorCorreo() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("El usuario no tiene permisos para ejecutar esta opción");
            return;
        }

        String correo = validator.correo();
        interfaz.imprimirMensaje(g.eliminarPorCorreo(correo));

    }

    public void listarPorCorreo() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("El usuario no tiene permisos para ejecutar esta opción");
            return;
        }

        String correo = validator.correo();
        interfaz.imprimirMensaje(g.listarPorCorreo(correo));

    }

    public void listarTodos() {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("El usuario no tiene permisos para ejecutar esta opción");
            return;
        }

        ArrayList<String> lista = g.listarTodos();

        if (lista == null) {
            interfaz.imprimirMensaje("No existen usuarios registrados");
            return;
        }

        for (String u : lista) {
            interfaz.imprimirMensaje(u);
        }
    }
}
