package cr.ac.ucenfotec.sortiz0640.bl.logic;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.dl.DataDepartamento;
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

    private DataDepartamento db;

    /**
     * Constructor que inicializa el gestor y su capa de datos.
     */

    public GestorDepartamento() {
        db = new DataDepartamento();
    }

    /**
     * Agrega un nuevo departamento al sistema.
     *
     * @param nombre Nombre del departamento
     * @param descripcion Descripción de las funciones del departamento
     * @param correo Correo electrónico único del departamento
     * @return Mensaje indicando éxito o error en la operación
     */

    public String agregar(String nombre, String descripcion, String correo) {
        Departamento tmpDepartamento = new Departamento(nombre, descripcion, correo);
        boolean resultado = db.agregar(tmpDepartamento);

        if (!resultado) {
            return "[ERR] El correo/nombre del departamento ya se encuentra registrado";
        }

        return "[INFO] Departamento agregado correctamente";
    }

    /**
     * Elimina un departamento del sistema por su correo electrónico.
     *
     * @param correo Correo del departamento a eliminar
     * @return Mensaje indicando éxito o error en la operación
     */

    public String eliminarPorCorreo(String correo) {
        boolean resultado = db.eliminarPorCorreo(correo);

        if (!resultado) {
            return "[ERR] El departamento especificado no existe";
        }

        return "[INFO] Se ha eliminado el departamento correctamente";
    }

    /**
     * Verifica si existe un departamento con el correo especificado.
     *
     * @param correo Correo a verificar
     * @return true si el departamento existe, false en caso contrario
     */

    public boolean existePorCorreo(String correo) {
        return db.existePorCorreo(correo);
    }

    /**
     * Obtiene la información de un departamento específico por su correo.
     *
     * @param correo Correo del departamento a buscar
     * @return Representación en texto del departamento o mensaje de error si no existe
     */

    public String listarPorCorreo(String correo) {
        String resultado = db.listarPorCorreo(correo);

        if (resultado == null) {
            return "[ERR] El departamento especificado no existe";
        }

        return resultado;
    }

    /**
     * Obtiene una lista con todos los departamentos registrados en el sistema.
     *
     * @return ArrayList con la representación en texto de todos los departamentos,
     *         o null si no hay departamentos registrados
     */

    public ArrayList<String> listarTodos() {
        if (!db.existenDepartamentos()) {
            return null;
        }

        return db.listarTodos();
    }

    /**
     * Busca y retorna un objeto Departamento por su correo electrónico.
     *
     * @param correo Correo del departamento a buscar
     * @return Objeto Departamento si existe, null si no se encuentra
     */

    public Departamento buscarPorCorreo(String correo) {
        return db.buscarPorCorreo(correo);
    }

    /**
     * Verifica si existen departamentos registrados en el sistema.
     *
     * @return true si hay al menos un departamento, false si está vacío
     */

    public boolean existenDepartamentos() {
        return db.existenDepartamentos();
    }

    /**
     * Obtiene la lista completa de objetos Departamento almacenados.
     *
     * @return ArrayList con todos los departamentos del sistema
     */

    public ArrayList<Departamento> obtenerDepartamentos() {
        return db.obtenerDepartamentos();
    }

    /**
     * Obtiene una lista con los correos de todos los departamentos.
     *
     * @return ArrayList con los correos de los departamentos
     */

    public ArrayList<String> obtenerCorreosDepartamentos() {
        ArrayList<String> correos = new ArrayList<>();
        for (Departamento d: obtenerDepartamentos()) {
            correos.add(d.getCorreo());
        }
        return correos;
    }

    /**
     * Obtiene los detalles de un departamento específico en formato de array.
     *
     * @param correoDepartamento Correo del departamento
     * @return Array con [nombre, correo, descripcion] o null si no existe
     */

    public String[] obtenerDetallesDepartamento(String correoDepartamento) {
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

    /**
     * Convierte la lista de departamentos a formato de array para tablas.
     *
     * @return ArrayList de arrays con los datos de cada departamento
     */

    public ArrayList<String[]> obtenerTodosDepartamentosFormato() {
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