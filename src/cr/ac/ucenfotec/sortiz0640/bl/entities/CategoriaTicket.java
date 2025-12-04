package cr.ac.ucenfotec.sortiz0640.bl.entities;

import cr.ac.ucenfotec.sortiz0640.bl.util.TipoCategoria;

import java.util.ArrayList;

public class CategoriaTicket {

    private String nombre;
    private ArrayList<String> palabrasDetonantes;
    private TipoCategoria tipo;

    public CategoriaTicket(String nombre, ArrayList<String> palabrasDetonantes) {
        this.nombre = nombre;
        this.palabrasDetonantes = palabrasDetonantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getPalabrasDetonantes() {
        return palabrasDetonantes;
    }

    public void setPalabrasDetonantes(ArrayList<String> palabrasDetonantes) {
        this.palabrasDetonantes = palabrasDetonantes;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }
}
