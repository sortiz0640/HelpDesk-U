package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.ui.ViewTicket;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import cr.ac.ucenfotec.sortiz0640.util.Validations;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ControllerTicket {

    private UI interfaz = new UI();
    private ViewTicket app = new ViewTicket();
    private GestorTicket g;
    private GestorSesion sesion;
    private GestorDepartamento departamento;
    private ControllerDepartamento controllerDepartamento;
    private Validations validator = new Validations();

    public ControllerTicket(GestorTicket g, GestorSesion sesion, ControllerDepartamento controllerDepartamento, GestorDepartamento departamento) {
        this.g = g;
        this.sesion = sesion;
        this.controllerDepartamento = controllerDepartamento;
        this.departamento = departamento;
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
            case 1: crear(); break;
            case 2: misTickets(); break;
            case 3: eliminar(); break;
            case 4: actualizarEstado(); break;
            case 5: listarTodosPorDepartamento(); break;
            case 0: break;
            default: interfaz.imprimirMensaje("[INFO] Opción no válida. Intente nuevamente! \n");
        }
    }

    public void crear() throws IOException {

        if (!departamento.existenDepartamentos()) {
            interfaz.imprimirMensaje("[INFO] No existen departamentos registrados. No es posible crear un Ticket en este momento.\n");
            return;
        }

        String asunto = validator.asunto();
        String descripcion = validator.descripcion();

        controllerDepartamento.listarTodos();

        interfaz.imprimirMensaje("\nDigite el correo del departamento encargado");;
        String correoDepartamento = validator.correo();

        interfaz.imprimirMensaje(g.crear(asunto, descripcion, correoDepartamento, sesion.getCorreo()));

    }

    public void misTickets() throws IOException {

        ArrayList<String> misTickets = g.listarMisTickets(sesion.getCorreo());

        if (misTickets == null || misTickets.isEmpty()) {
            interfaz.imprimirMensaje("[INFO] No existen tickets registrados\n");
            return;
        }

        interfaz.imprimirMensaje("[INFO] Lista de tickets: \n");
        for (String t : misTickets) {
            interfaz.imprimirMensaje(t);
        }

    }

    public void eliminar() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        String ticketId = validator.ticketId();
        interfaz.imprimirMensaje(g.eliminar(ticketId));
    }

    public void actualizarEstado() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        String ticketId = validator.ticketId();
        int estado = validator.estado();
        interfaz.imprimirMensaje(g.actualizarEstado(ticketId, estado));
    }


    public void listarTodosPorDepartamento() throws IOException {

        if (!sesion.tienePermisosAdmin()) {
            interfaz.imprimirMensaje("[INFO] El usuario no tiene permisos para ejecutar esta opción\n");
            return;
        }

        ArrayList<String> listaDepartamentos = departamento.listarTodos();

        interfaz.imprimirMensaje("[INFO] Lista de departamentos: \n");
        for (String u : listaDepartamentos) {
            interfaz.imprimirMensaje(u);
        }

        interfaz.imprimirMensaje("\nIngrese el correo del departamento para mostrar sus tickets");
        String correo = validator.correo();


        ArrayList<String> listaTickets = g.listarTodosPorDepartamento(correo);

        if (listaTickets == null || listaTickets.isEmpty()) {
            interfaz.imprimirMensaje("[INFO] El departamento no existe/no tiene tickets registrados\n");
            return;
        }

        interfaz.imprimirMensaje("[INFO] Lista de tickets: \n");
        for (String u : listaTickets) {
            interfaz.imprimirMensaje(u);
        }

    }
}
