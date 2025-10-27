import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorDepartamento;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorTicket;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.sortiz0640.tl.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        GestorUsuario gestorUsuario = new GestorUsuario();
        String status = gestorUsuario.agregar("Sebastian", "Ortiz Vargas", "admin@gmail.com", "adminAdmin", 1);

        GestorDepartamento gestorDepartamento = new GestorDepartamento();
        GestorTicket gestorTicket = new GestorTicket();
        GestorSesion sesion = new GestorSesion(gestorUsuario);
        ControllerUsuario controllerUsuario = new ControllerUsuario(gestorUsuario, sesion);
        ControllerDepartamento controllerDepartamento = new ControllerDepartamento(gestorDepartamento, sesion);
        ControllerTicket controllerTicket = new ControllerTicket(gestorTicket,sesion, controllerDepartamento, gestorDepartamento);
        ControllerApp controllerApp = new ControllerApp(controllerUsuario, controllerTicket, controllerDepartamento, sesion);
        ControllerSesion controllerSesion = new ControllerSesion(sesion, controllerApp);


        controllerSesion.start();
    }
}