package com.barchello.dao;
/**
 *
 * @author Jhones
 */

import com.barchello.config.ConexionBD;
import com.barchello.modelo.UsuarioVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Guardar un nuevo usuario (usado en el Registro)
    public boolean crearUsuario(UsuarioVO usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, password, estado, rol_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getClave());
            ps.setString(4, "activo");
            ps.setInt(5, usuario.getRol());
            return ps.executeUpdate() > 0;
        }
    }

    // Buscar por email (usado en el Login)
    public UsuarioVO buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioVO u = new UsuarioVO();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setClave(rs.getString("password"));
                    u.setRol(rs.getInt("rol_id"));
                    return u;
                }
            }
            return null;
        }
    }
}