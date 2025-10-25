package cr.ac.ucenfotec.sortiz0640.bl.entities;

import java.util.Random;

public class Departamento {

    private String id; // Formato "DE-0000"
    private String nombre;
    private String descripcion;
    private String correo;

    public Departamento(String nombre, String descripcion, String correo) {
        this.id = generarId();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    // Función generada por IA para la creación de id's totalmente aleatorios y no repetibles
    private String generarId() {
        long timestamp = System.currentTimeMillis() % 100000; // últimos 5 dígitos del tiempo
        int randomPart = new Random().nextInt(90) + 10; // 2 dígitos aleatorios
        return "DE-" + timestamp + randomPart;
    }

    @Override
    public String toString() {
        return "[Nombre: " + getNombre() +"]["+ "ID: " + getId() + "][Email: " + getCorreo() + "][ "+ getDescripcion() +"]";
    }
}
