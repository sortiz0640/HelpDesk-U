package cr.ac.ucenfotec.sortiz0640.bl.entities;

import cr.ac.ucenfotec.sortiz0640.bl.util.ListaRoles;

import java.util.Random;

public class Usuario {

    private String id; // Formato "US-0000"
    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private ListaRoles rol;

    public Usuario(String nombre, String apellidos, String correo, String password) {
        this.id = generarId();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ListaRoles getRol() {
        return rol;
    }

    public void setRol(ListaRoles rol) {
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    // Función generada por IA para la creación de id's totalmente aleatorios y no repetibles
    private String generarId() {
        long timestamp = System.currentTimeMillis() % 100000; // últimos 5 dígitos del tiempo
        int randomPart = new Random().nextInt(90) + 10; // 2 dígitos aleatorios
        return "US-" + timestamp + randomPart;
    }

    @Override
    public String toString() {
        return "[Nombre: " + getNombre() + " "+ getApellidos() +"][" + getId() + "][Correo: " + getCorreo() + "]" +"[ "+ getRol() +"]";
    }
}
