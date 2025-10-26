package cr.ac.ucenfotec.sortiz0640.ui;

import cr.ac.ucenfotec.sortiz0640.util.UI;

public class ViewTicket {

    private UI interfaz = new UI();

    public void mostrarMenu() {
        interfaz.imprimirMensaje("===================================");
        interfaz.imprimirMensaje("HELPDESK U: TICKETS ");
        interfaz.imprimirMensaje("===================================");
        interfaz.imprimirMensaje("[1] Crear ");
        interfaz.imprimirMensaje("[2] Eliminar");
        interfaz.imprimirMensaje("[3] Actualizar Estado");
        interfaz.imprimirMensaje("[4] Listar por Id");
        interfaz.imprimirMensaje("[5] Listar Todos");
        interfaz.imprimirMensaje("[0] Regresar");
        interfaz.imprimirMensaje("===================================\n");
    }
}
