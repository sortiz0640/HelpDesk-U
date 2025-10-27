package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.ui.ViewTicket;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import org.apache.commons.validator.routines.EmailValidator;
import java.io.IOException;
import java.util.ArrayList;

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
            case 2: actualizarEstado(); break;
            case 3: listarTodosPorDepartamento(); break;
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

            if (asunto.isBlank()) {
                interfaz.imprimirMensaje("El asunto no puede estar vacio!");
            }

        } while (asunto.isBlank());

        do {
            interfaz.imprimirMensaje("Ingrese el descripcion del ticket");
            descripcion = interfaz.leerTexto();

            if (descripcion.isBlank()) {
                interfaz.imprimirMensaje("La descripcion no puede estar vacia!");
            }
        } while (descripcion.isBlank());

        interfaz.imprimirMensaje("Digite el correo del departamento encargado\n");
        cd.listarTodos();

        do {
            interfaz.imprimirMensaje("Ingrese el correo del departamento3 [@gmail.com] : ");
            correoDepartamento = interfaz.leerTexto();

            if (!validator.isValid(correoDepartamento) || correoDepartamento.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correoDepartamento == null || correoDepartamento.isBlank() || !validator.isValid(correoDepartamento));

        interfaz.imprimirMensaje(g.crear(asunto, descripcion, correoDepartamento));

    }


    public void actualizarEstado() throws IOException {
        //todo
    }


    public void listarTodosPorDepartamento() throws IOException {

        GestorDepartamento gestorDepartamento = new GestorDepartamento();

        interfaz.imprimirMensaje("LISTA DE DEPARTAMENTOS: \n");
        ArrayList<String> listaDepartamentos = gestorDepartamento.listarTodos();

        for (String u : listaDepartamentos) {
            interfaz.imprimirMensaje(u);
        }

        interfaz.imprimirMensaje("\nIngrese el correo del departamento para mostrar sus tickets");
        String correo;

        do {
            interfaz.imprimirMensaje("Ingrese el correo del usuario [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        ArrayList<String> listaTickets = g.listarTodosPorDepartamento(correo);

        if (listaTickets == null) {
            interfaz.imprimirMensaje("El departamento no existe/no tiene tickets registrados");
        }

        for (String u : listaTickets) {
            interfaz.imprimirMensaje(u);
        }

    }
}
