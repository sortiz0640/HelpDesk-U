
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorSesion;
import cr.ac.ucenfotec.sortiz0640.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.sortiz0640.tl.ControllerSesion;

import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {

        GestorUsuario gestor = new GestorUsuario();
        String status = gestor.agregar("Sebastian", "Ortiz Vargas", "admin@gmail.com", "adminAdmin", 1);

        GestorSesion sesion = new GestorSesion(gestor);

        ControllerSesion app = new ControllerSesion();
        app.start();

    }
}