package cr.ac.ucenfotec.sortiz0640.tl;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.ui.ViewTicket;
import cr.ac.ucenfotec.sortiz0640.util.UI;
import cr.ac.ucenfotec.sortiz0640.util.Validations;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerTicket {

    private UI interfaz = new UI();
    private ViewTicket app = new ViewTicket();
    private GestorTicket g;
    private GestorSesion sesion;
    private GestorDepartamento departamento;
    private ControllerDepartamento controllerDepartamento;
    Validations validator = new Validations();

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
            case 2: actualizarEstado(); break;
            case 3: listarTodosPorDepartamento(); break;
            case 0: break;
            default: interfaz.imprimirMensaje("Opción no válida. Intente nuevamente! \n");
        }
    }

    public void crear() throws IOException {

        String asunto = validator.asunto();
        String descripcion = validator.descripcion();

        interfaz.imprimirMensaje("Digite el correo del departamento encargado\n");;
        String correoDepartamento = validator.correo();

        controllerDepartamento.listarTodos();

        interfaz.imprimirMensaje(g.crear(asunto, descripcion, correoDepartamento));

    }


    public void actualizarEstado() throws IOException {
        //todo
    }


    public void listarTodosPorDepartamento() throws IOException {

        interfaz.imprimirMensaje("LISTA DE DEPARTAMENTOS: \n");
        ArrayList<String> listaDepartamentos = departamento.listarTodos();

        for (String u : listaDepartamentos) {
            interfaz.imprimirMensaje(u);
        }

        interfaz.imprimirMensaje("\nIngrese el correo del departamento para mostrar sus tickets");
        String correo = validator.correo();

        ArrayList<String> listaTickets = g.listarTodosPorDepartamento(correo);

        if (listaTickets == null) {
            interfaz.imprimirMensaje("El departamento no existe/no tiene tickets registrados");
        }

        for (String u : listaTickets) {
            interfaz.imprimirMensaje(u);
        }

    }
}
