package cr.ac.ucenfotec.sortiz0640.dl;

import cr.ac.ucenfotec.sortiz0640.bl.entities.Usuario;
import cr.ac.ucenfotec.sortiz0640.dl.template.IDao;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementación DAO para la entidad Usuario.
 * Gestiona todas las operaciones CRUD de usuarios en la base de datos.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */

public class UsuarioDAO implements IDao<Usuario> {

    @Override
    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (correo, nombre, apellido, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellidos());
            stmt.setString(4, usuario.getPassword());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al insertar usuario: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, password = ? WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getCorreo());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminar(String correo) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            throw new SQLException("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario buscarPorId(String correo) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new SQLException("Error al buscar usuario: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Usuario> listarTodos() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nombre";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(construirUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new SQLException("Error al listar usuarios: " + e.getMessage());
        }
    }

    @Override
    public boolean existe(String correo) throws SQLException {
        return buscarPorId(correo) != null;
    }

    /**
     * Método auxiliar para construir un objeto Usuario desde un ResultSet.
     *
     * @param rs ResultSet con los datos del usuario
     * @return Objeto Usuario construido
     * @throws SQLException si hay error al leer los datos
     */
    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setCorreo(rs.getString("correo"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setPassword(rs.getString("password"));
        return usuario;
    }
}