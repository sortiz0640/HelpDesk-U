package cr.ac.ucenfotec.sortiz0640.tl;

import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.sortiz0640.ui.ViewUsuario;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerUsuario {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewUsuario app = new ViewUsuario();
    private GestorUsuario g = new GestorUsuario();
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
        String apellidos;
        String correo;
        String password;
        int rol;


        do {

            interfaz.imprimirMensaje("Ingrese su nombre: ");
            nombre = interfaz.leerTexto();

            if (nombre == null ||  nombre.isBlank()) {
                interfaz.imprimirMensaje("El nombre no puede estar vacio. ");
            }

        } while (nombre == null ||  nombre.isBlank());

        do {
            interfaz.imprimirMensaje("Ingrese sus apellidos [ejemplo: Ortiz Vargas]: ");
            apellidos = interfaz.leerTexto();
            if (apellidos == null ||  apellidos.isBlank()) {
                interfaz.imprimirMensaje("El nombre no puede estar vacio. ");
            }

        } while (apellidos == null || apellidos.isBlank());

        do {
            interfaz.imprimirMensaje("Ingrese su correo [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        do {

            interfaz.imprimirMensaje("Seleccione un rol [1 = ADMIN ] [2 = ESTUDIANTE ] [3 = FUNCIONARIO ]  ");
            rol = interfaz.leerOpcion();

            if (rol < 1 || rol > 3) {
                interfaz.imprimirMensaje("Opción invalida. Intente nuevamente ");
            }
        } while (rol < 1 || rol > 3);

        do {
            interfaz.imprimirMensaje("Ingrese su password [mínimo 8 caracteres]");
            password = interfaz.leerTexto();

            if (password == null || password.isBlank() || password.length() < 8) {
                interfaz.imprimirMensaje("El password no puede estar vacio [mínimo 8 caracteres] . ");
            }

        } while (password == null || password.isBlank() || password.length() < 8);

        interfaz.imprimirMensaje(g.agregar(nombre, apellidos, correo, password, rol));

    }

    public void eliminarPorCorreo() throws IOException {

        String correo;
        do {
            interfaz.imprimirMensaje("Ingrese el correo del usuario [@gmail.com] : ");
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

        ArrayList<String> lista = g.getUsuarios();

        if (lista == null) {
            interfaz.imprimirMensaje("No existen usuarios registrados");
            return;
        }

        for (String u : lista) {
            interfaz.imprimirMensaje(u);
        }
    }
}
