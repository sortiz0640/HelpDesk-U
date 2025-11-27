package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Departamento;
import cr.ac.ucenfotec.sortiz0640.dl.template.IDao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Departamento.
 * Gestiona todas las operaciones CRUD de departamentos en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class DepartamentoDAO implements IDao<Departamento> {

    @Override
    public boolean insertar(Departamento departamento) throws SQLException {
        String sql = "INSERT INTO departamentos (correo, nombre) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departamento.getCorreo());
            stmt.setString(2, departamento.getNombre());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al insertar departamento: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizar(Departamento departamento) throws SQLException {
        String sql = "UPDATE departamentos SET nombre = ? WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departamento.getNombre());
            stmt.setString(2, departamento.getCorreo());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar departamento: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminar(String correo) throws SQLException {
        String sql = "DELETE FROM departamentos WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar departamento: " + e.getMessage());
        }
    }

    @Override
    public Departamento buscarPorId(String correo) throws SQLException {
        String sql = "SELECT * FROM departamentos WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirDepartamento(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new SQLException("Error al buscar departamento: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Departamento> listarTodos() throws SQLException {
        ArrayList<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM departamentos ORDER BY nombre";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departamentos.add(construirDepartamento(rs));
            }

            return departamentos;

        } catch (SQLException e) {
            throw new SQLException("Error al listar departamentos: " + e.getMessage());
        }
    }

    @Override
    public boolean existe(String correo) throws SQLException {
        return buscarPorId(correo) != null;
    }

    /**
     * Verifica si existe un departamento con el nombre especificado.
     *
     * @param nombre Nombre del departamento a verificar
     * @return true si existe, false en caso contrario
     * @throws SQLException si hay error en la consulta
     */
    public boolean existePorNombre(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM departamentos WHERE nombre = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

            return false;

        } catch (SQLException e) {
            throw new SQLException("Error al verificar departamento por nombre: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para construir un objeto Departamento desde un ResultSet.
     *
     * @param rs ResultSet con los datos del departamento
     * @return Objeto Departamento construido
     * @throws SQLException si hay error al leer los datos
     */
    private Departamento construirDepartamento(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setCorreo(rs.getString("correo"));
        departamento.setNombre(rs.getString("nombre"));
        return departamento;
    }
}