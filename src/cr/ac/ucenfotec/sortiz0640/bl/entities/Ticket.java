package cr.ac.ucenfotec.sortiz0640.bl.entities;

import cr.ac.ucenfotec.sortiz0640.bl.util.EstadoTicket;

import java.util.Objects;
import java.util.Random;

/**
 * Representa un ticket de soporte en el sistema HelpDesk.
 * Los tickets son solicitudes o problemas reportados por usuarios que deben ser
 * atendidos por un departamento específico. Cada ticket tiene un ID único y
 * transita por diferentes estados durante su ciclo de vida.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class Ticket {

    private String id;
    private String asunto;
    private String descripcion;
    private EstadoTicket estado;
    private Usuario usuario;
    private Departamento departamento;
    private CategoriaTicket tecnica;
    private CategoriaTicket emocion;

    /**
     * Constructor vacío por defecto.
     */
    public Ticket() {

    }

    /**
     * Constructor para crear un nuevo ticket.
     * El ticket se crea automáticamente con un ID único generado y en estado NUEVO.
     *
     * @param asunto Asunto o título breve del ticket.
     * @param descripcion Descripción detallada del problema o solicitud.
     * @param usuario Usuario que crea el ticket.
     * @param departamento Departamento al que se asigna el ticket.
     */
    public Ticket(String asunto, String descripcion, Usuario usuario, Departamento departamento) {
        this.id = generarId();
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = EstadoTicket.NUEVO;
        this.usuario = usuario;
        this.departamento = departamento;
    }

    /**
     * Obtiene el asunto del ticket.
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * Asigna un nuevo asunto al ticket.
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * Obtiene la descripción del ticket.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna una nueva descripción al ticket.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado actual del ticket.
     */
    public EstadoTicket getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado del ticket.
     */
    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    /**
     * Genera un ID único aleatorio para el ticket.
     * @return ID generado (ej: TI-1234599).
     */
    private String generarId() {
        long timestamp = System.currentTimeMillis() % 100000;
        int randomPart = new Random().nextInt(90) + 10;
        return "TI-" + timestamp + randomPart;
    }

    /**
     * Obtiene el ID único del ticket.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el departamento asignado al ticket.
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Asigna el departamento responsable del ticket.
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene el usuario creador del ticket.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna el usuario creador del ticket.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Asigna el ID del ticket manualmente.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la categoría técnica asignada.
     */
    public CategoriaTicket getTecnica() {
        return tecnica;
    }

    /**
     * Asigna la categoría técnica del ticket.
     */
    public void setTecnica(CategoriaTicket tecnica) {
        this.tecnica = tecnica;
    }

    /**
     * Obtiene la categoría emocional asignada.
     */
    public CategoriaTicket getEmocion() {
        return emocion;
    }

    /**
     * Asigna la categoría emocional del ticket.
     */
    public void setEmocion(CategoriaTicket emocion) {
        this.emocion = emocion;
    }

    /**
     * Genera una representación en texto del ticket con toda su información.
     *
     * @return String con formato [ID: X][Asunto: Y][Descripción: Z][Estado: W][Correo Creador: V]
     */
    @Override
    public String toString() {
        return "[ID: " + getId() + "]"
                + "[Asunto: " + getAsunto() + "]"
                + "[Descripción: " + getDescripcion() + "]"
                + "[Estado: " + getEstado() + "]"
                + "[Correo Creador: " + getUsuario().getCorreo() + "]";

    }

    /**
     * Compara este ticket con otro objeto para determinar igualdad.
     * Dos tickets son considerados iguales si tienen el mismo ID.
     *
     * @param o Objeto a comparar con este ticket.
     * @return true si los tickets son iguales (mismo ID), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket t = (Ticket) o;
        return Objects.equals(id, t.id);
    }

    /**
     * Genera el código hash del ticket basado en su ID.
     *
     * @return Código hash calculado a partir del ID del ticket.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}