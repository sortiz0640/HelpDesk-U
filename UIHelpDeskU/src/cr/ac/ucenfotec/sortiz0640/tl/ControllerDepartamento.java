package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.ui.ViewDepartamento;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerDepartamento {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewDepartamento app = new ViewDepartamento();
    private GestorDepartamento g = new GestorDepartamento();
    EmailValidator validator = EmailValidator.getInstance();


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
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }

    public void registrar() throws IOException {

        String nombre;
        String descripcion;
        String correo;

        do {

            interfaz.imprimirMensaje("Ingrese el nombre del departamento: ");
            nombre = interfaz.leerTexto();

            if (nombre == null ||  nombre.isBlank()) {
                interfaz.imprimirMensaje("El nombre no puede estar vacio. ");
            }

        } while (nombre == null ||  nombre.isBlank());

        do {

            interfaz.imprimirMensaje("Ingrese la descripción del departamento: ");
            descripcion = interfaz.leerTexto();

            if (descripcion == null ||  nombre.isBlank()) {
                interfaz.imprimirMensaje("El nombre no puede estar vacio. ");
            }

        } while (descripcion == null ||  nombre.isBlank());

        do {
            interfaz.imprimirMensaje("Ingrese su correo [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        interfaz.imprimirMensaje(g.agregar(nombre, descripcion, correo));

    }

    public void eliminarPorCorreo() throws IOException {

        String correo;
        do {
            interfaz.imprimirMensaje("Ingrese el correo del departamento para eliminarlo [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        interfaz.imprimirMensaje(g.eliminarPorCorreo(correo));

    }

    public void listarPorCorreo() throws IOException {

        String correo;
        do {
            interfaz.imprimirMensaje("Ingrese el correo del usuario [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        interfaz.imprimirMensaje(g.listarPorCorreo(correo));

    }

    public void listarTodos() {

        ArrayList<String> lista = g.listarTodos();

        if (lista == null || lista.isEmpty()) {
            interfaz.imprimirMensaje("No existen departamentos registrados");
            return;
        }

        for (String u : lista) {
            interfaz.imprimirMensaje(u);
        }
    }
}
