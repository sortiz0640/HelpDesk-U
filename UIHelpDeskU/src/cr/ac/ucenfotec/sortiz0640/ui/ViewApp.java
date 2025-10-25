package cr.ac.ucenfotec.sortiz0640.ui;

import cr.ac.ucenfotec.sortiz0640.util.UI;

public class ViewApp {

    private UI ui = new UI();

    public void mostrarMenu() {
        ui.imprimirMensaje("===================================");
        ui.imprimirMensaje("HELPDESK U: Menú Principal ");
        ui.imprimirMensaje("===================================");
        ui.imprimirMensaje("[1] Usuarios");
        ui.imprimirMensaje("[2] Departamentos");
        ui.imprimirMensaje("[3] Tickets");
        ui.imprimirMensaje("[0] Salir");
        ui.imprimirMensaje("===================================");
    }
}
