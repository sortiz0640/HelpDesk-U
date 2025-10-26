package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.ui.ViewTicket;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.nio.channels.FileLock;

public class ControllerTicket {

    private UI interfaz = new UI(); // Clase de métodos para lectura y escritura en consola
    private ViewTicket app = new ViewTicket();
    private GestorTicket g = new GestorTicket();
    private EmailValidator validator = EmailValidator.getInstance();

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
            case 1: crear(); break;
            case 2: eliminarPorId(); break;
            case 3: actualizarEstado(); break;
            case 4: listarPorId(); break;
            case 5: listarTodos(); break;
            case 6: listarTodosPorDepartamento(); break;
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }

    public void crear() throws IOException {

        String asunto;
        String descripcion;
        String correoDepartamento;
        ControllerDepartamento cd = new ControllerDepartamento();

        do {
            interfaz.imprimirMensaje("Ingrese el asunto del ticket");
            asunto = interfaz.leerTexto();

            if (asunto.isBlank() || asunto == null) {
                interfaz.imprimirMensaje("El asunto no puede estar vacio!");
            }

        } while (asunto.isBlank() || asunto == null);

        do {
            interfaz.imprimirMensaje("Ingrese el descripcion del ticket");
            descripcion = interfaz.leerTexto();

            if (descripcion.isBlank() || descripcion == null) {
                interfaz.imprimirMensaje("La descripcion no puede estar vacia!");
            }
        } while (descripcion.isBlank() || descripcion == null);

        interfaz.imprimirMensaje("Digite el correo del departamento encargado\n");
        cd.listarTodos();

        do {
            interfaz.imprimirMensaje("Ingrese su correo [@gmail.com] : ");
            correoDepartamento = interfaz.leerTexto();

            if (!validator.isValid(correoDepartamento) || correoDepartamento.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correoDepartamento == null || correoDepartamento.isBlank() || !validator.isValid(correoDepartamento));

        interfaz.imprimirMensaje(g.crear(asunto, descripcion, correoDepartamento));

    }

    public void eliminarPorId() throws IOException{

    }

    public void actualizarEstado() throws IOException {

    }

    public void listarPorId() throws IOException {

    }

    public void listarTodos() throws IOException {

    }

    public void listarTodosPorDepartamento() throws IOException {

    }
}
