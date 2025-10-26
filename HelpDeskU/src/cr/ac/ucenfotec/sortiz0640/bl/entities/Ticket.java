package cr.ac.ucenfotec.sortiz0640.bl.entities;
import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;

import java.util.Random;

public class Ticket {

    private String id; // Formato "TI-0000"
    private String asunto;
    private String descripcion;
    private EstadoTicket estado;
    private String correoDepartamento;

    public Ticket(String asunto, String descripcion, String correoDepartamento) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = EstadoTicket.NUEVO; // todos los tickets se crean por default con el estado NUEVO
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    // Función generada por IA para la creación de id's totalmente aleatorios y no repetibles
    private String generarId() {
        long timestamp = System.currentTimeMillis() % 100000; // últimos 5 dígitos del tiempo
        int randomPart = new Random().nextInt(90) + 10; // 2 dígitos aleatorios
        return "TI-" + timestamp + randomPart;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[ID: " + getId() + "]" + "[Asunto: " + getAsunto() + "]" +  "[Descripción: " + getDescripcion() + "]" + "[Estado: " + getEstado() + "]";
    }
}
