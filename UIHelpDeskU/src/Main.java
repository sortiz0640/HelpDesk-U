import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.sortiz0640.tl.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GestorUsuario gestorUsuario = new GestorUsuario();

        // Crea usuarios por defecto con diferentes niveles de acceso para el uso del programa

        String DEFAULT_ADMIN = gestorUsuario.agregar("Administrador", "SYS ADMIN", "admin@ucenfotec.ac.cr", "adminAdmin", 1);
        String DEFAULT_USER = gestorUsuario.agregar("Sebastian", "Ortiz Vargas", "sortiz0640@ucenfotec.ac.cr", "abc123456", 3);

        GestorDepartamento gestorDepartamento = new GestorDepartamento();

        String DEFAULT_DEPARTAMENTO = gestorDepartamento.agregar("Escuela de Ingeniería", "Gestion y administración de carreras informáticas", "escuelaingenieria@ucenfotec.ac.cr");
        String DEFAULT_DEPARTAMENTO2 = gestorDepartamento.agregar("Escuela de Administracion", "Gestion y administración de carreras administrativas", "escuelaadministracion@ucenfotec.ac.cr");


        // Inyección de instancias a los controllers y gestores para trabar con una única instancia de cada uno.

        GestorTicket gestorTicket = new GestorTicket(gestorDepartamento);
        GestorSesion sesion = new GestorSesion(gestorUsuario);
        ControllerUsuario controllerUsuario = new ControllerUsuario(gestorUsuario, sesion);
        ControllerDepartamento controllerDepartamento = new ControllerDepartamento(gestorDepartamento, sesion);
        ControllerTicket controllerTicket = new ControllerTicket(gestorTicket,sesion, controllerDepartamento, gestorDepartamento);
        ControllerApp controllerApp = new ControllerApp(controllerUsuario, controllerTicket, controllerDepartamento, sesion);
        ControllerSesion controllerSesion = new ControllerSesion(sesion, controllerApp);


        // Inicia el programa mostrando el menu de inicio de sesión

        controllerSesion.start();
    }
}