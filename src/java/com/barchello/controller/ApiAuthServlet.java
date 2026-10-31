package com.barchello.controller;

/**
 *
 * @author Jhones
 */

import com.barchello.modelo.UsuarioVO;
import com.barchello.servicios.UsuarioServicio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Esta anotación es la URL que usarás en Postman: /api/auth
@WebServlet(name = "ApiAuthServlet", urlPatterns = {"/api/auth"})
public class ApiAuthServlet extends HttpServlet {

    private final UsuarioServicio usuarioServicio = new UsuarioServicio();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Configurar la respuesta para que Postman sepa que es un JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        // 2. Recibir los parámetros
        String accion = request.getParameter("accion"); 
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            if ("login".equals(accion)) {
                // --- FLUJO DE LOGIN ---
                UsuarioVO usuario = usuarioServicio.login(email, password);
                if (usuario != null) {
                    response.setStatus(200); // OK
                    out.print("{\"status\":\"success\", \"mensaje\":\"Autenticación satisfactoria\", \"rol\":\"" + usuario.getRol() + "\"}");
                } else {
                    response.setStatus(401); // Unauthorized
                    out.print("{\"status\":\"error\", \"mensaje\":\"Error en la autenticación: Credenciales incorrectas\"}");
                }
                
            } else if ("registro".equals(accion)) {
                // --- FLUJO DE REGISTRO ---
                String nombre = request.getParameter("nombre");
                int rol = Integer.parseInt(request.getParameter("rol")); 
                
                UsuarioVO nuevoUsuario = new UsuarioVO(nombre, email, password, rol);
                boolean creado = usuarioServicio.registrarUsuario(nuevoUsuario);
                
                if (creado) {
                    response.setStatus(201); // Created
                    out.print("{\"status\":\"success\", \"mensaje\":\"Usuario registrado exitosamente\"}");
                } else {
                    response.setStatus(400); // Bad Request
                    out.print("{\"status\":\"error\", \"mensaje\":\"No se pudo registrar. Verifique los datos.\"}");
                }
            } else {
                response.setStatus(400);
                out.print("{\"status\":\"error\", \"mensaje\":\"Acción no válida. Use 'login' o 'registro'\"}");
            }
        } catch (Exception e) {
            response.setStatus(500); // Internal Server Error
            out.print("{\"status\":\"error\", \"mensaje\":\"Error interno: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
            out.close();
        }
    }
}
