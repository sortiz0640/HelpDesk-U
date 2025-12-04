package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.bl.util.ConfigPropertiesReader;
import cr.ac.ucenfotec.sortiz0640.dl.DepartamentoDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestor de lógica de negocio para la administración de departamentos.
 * Trabaja exclusivamente con la entidad Departamento.
 * No tiene dependencias de otros gestores.
 *
 * @author Sebastian Ortiz
 * @version 2.0
 * @since 2025
 */

public class GestorDepartamento {

    private DepartamentoDAO db;
    ConfigPropertiesReader config;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     */

    public GestorDepartamento() throws SQLException, ClassNotFoundException {
        config = new ConfigPropertiesReader();
        db = new DepartamentoDAO(
                config.getDbDriver(),
                config.getDbUrl(),
                config.getDbUser(),
                config.getDbPassword()
        );
    }

    public String agregar(String nombre, String descripcion, String correo) throws SQLException {
        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean resultado = db.agregar(tmpDepartamento);

        if (!resultado) {
            return "[ERR] El correo/nombre del departamento ya se encuentra registrado";
        }

        return "[INFO] Departamento agregado correctamente";
    }

    public String eliminarPorCorreo(String correo) throws SQLException {
        boolean resultado = db.eliminar(correo);

        if (!resultado) {
            return "[ERR] El departamento especificado no existe";
        }

        return "[INFO] Se ha eliminado el departamento correctamente";
    }

    public boolean existePorCorreo(String correo) throws SQLException {
        return db.existe(correo);
    }

    public Departamento buscarPorCorreo(String correo) throws SQLException {
        return db.buscar(correo);
    }


    public ArrayList<Departamento> obtenerDepartamentos() throws SQLException {
        return db.obtenerTodos();
    }

    public ArrayList<String> obtenerCorreosDepartamentos() throws SQLException {
        ArrayList<String> correos = new ArrayList<>();
        for (Departamento d: obtenerDepartamentos()) {
            correos.add(d.getCorreo());
        }
        return correos;
    }

    public String[] obtenerDetallesDepartamento(String correoDepartamento) throws SQLException {
        Departamento d = buscarPorCorreo(correoDepartamento);

        if (d == null) {
            return null;
        }

        return new String[]{
                d.getNombre(),
                d.getCorreo(),
                d.getDescripcion(),
        };
    }

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() throws SQLException {
        ArrayList<Departamento> departamentos = obtenerDepartamentos();
        ArrayList<String[]> resultado = new ArrayList<>();

        if (departamentos == null || departamentos.isEmpty()) {
            return resultado;
        }

        for (Departamento d : departamentos) {
            String[] datos = {
                    d.getNombre(),
                    d.getCorreo(),
                    d.getDescripcion(),
            };
            resultado.add(datos);
        }

        return resultado;
    }
}