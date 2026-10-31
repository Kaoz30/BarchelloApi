package com.barchello.servicios;

/**
 *
 * @author Jhones
 */

import com.barchello.dao.UsuarioDAO;
import com.barchello.modelo.UsuarioVO;
import com.barchello.util.HashUtil;
import java.sql.SQLException;

public class UsuarioServicio {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registrarUsuario(UsuarioVO usuario) {
        // Validaciones básicas
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) return false;
        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) return false;
        if (usuario.getClave() == null || usuario.getClave().length() < 4) return false;

        try {
            // ¡AQUÍ ESTÁ LA MAGIA! Encriptamos antes de guardar
            String claveSegura = HashUtil.sha256(usuario.getClave());
            usuario.setClave(claveSegura);
            return usuarioDAO.crearUsuario(usuario);
        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    public UsuarioVO login(String email, String clave) {
        if (email == null || clave == null) return null;
        try {
            UsuarioVO usuarioBD = usuarioDAO.buscarPorEmail(email);
            if (usuarioBD != null) {
                // Encriptamos la clave que llega y la comparamos con la de la BD
                String claveIngresadaHash = HashUtil.sha256(clave);
                if (usuarioBD.getClave().equals(claveIngresadaHash)) {
                    return usuarioBD; // Login exitoso
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en login: " + e.getMessage());
        }
        return null; // Login fallido
    }
}
