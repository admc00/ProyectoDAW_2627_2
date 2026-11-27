/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.model.Reseña;
import org.mindrot.jbcrypt.BCrypt;
import daw.model.Usuarios;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author admc0
 */
@WebServlet(name = "UsuariosController", urlPatterns = {"/users/*"})
public class UsuariosController extends HttpServlet {

    @PersistenceContext(unitName = "ProyectoDAW_2627PU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(UsuariosController.class.getName());

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vista = "home";

        String accion = "/users";
        if (request.getServletPath().equals(accion)) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            }
        }

        switch (accion) {

            case "/login":
                vista = "Login";
                break;

            case "/register":
                vista = "register";
                break;

            case "/logout":
                HttpSession sesion = request.getSession();

                if (sesion != null) {
                    sesion.invalidate();

                }
                break;

            case "/perfil":

                HttpSession sesionPerfil = request.getSession(false);
                if (sesionPerfil != null) {
                    Usuarios usuario = (Usuarios) sesionPerfil.getAttribute("usuarioLogueado");
                    if (usuario != null) {
                        
                        TypedQuery<Reseña> queryResenasUsuario = em.createNamedQuery("Resena.findByUsuarioId", Reseña.class);
                        queryResenasUsuario.setParameter("idUsuario", usuario.getId());
                        List<Reseña> listaResenasUsuario = queryResenasUsuario.getResultList();
                        
                        sesionPerfil.setAttribute("resenasUsuario", listaResenasUsuario);
                        
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/daw.views/perfil.jsp");
                        rd.forward(request, response);
                        return;
                    } else {

                        sesionPerfil.setAttribute("mensajeError", "Debes iniciar sesión para ver tu perfil.");
                        response.sendRedirect(request.getContextPath() + "/users/login");
                        return;
                    }

                }

            default:
                vista = "error";
                break;

        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/daw.views/" + vista + ".jsp");
        rd.forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getPathInfo();

        if (accion != null) {

            try {
                switch (accion) {
                    case "/login":
                        iniciarsesion(request, response);
                        break;

                    case "/register":
                        crearusuario(request, response);
                        break;

                    case "/comprobarEmail":
                        comprobarEmail(request, response);
                        break;
                }
            } catch (Exception e) {
                request.setAttribute("msg", "Error: datos no válidos");
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/daw.views/error.jsp");
                rd.forward(request, response);

            }

        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/daw.views/error.jsp");
            rd.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void iniciarsesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contrasena = request.getParameter("contrasenaLogin");
        String email = request.getParameter("emailLogin");
        HttpSession sesion = request.getSession();

        if (email != null) {
            email = email.toLowerCase();
        }

        try {
            if (contrasena == null || email == null) {
                throw new IllegalArgumentException("Datos no válidos");
            }
            TypedQuery<Usuarios> queryUsuarios = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
            queryUsuarios.setParameter("email", email);

            Usuarios resultado = queryUsuarios.getSingleResult();

            if (email.equals(resultado.getEmail())) {
                Usuarios u = resultado;

                if (verificarPassword(contrasena, u.getContraseña())) {

                    sesion.setAttribute("usuarioLogueado", u);
                    response.sendRedirect(request.getContextPath());
                } else {
                    throw new SecurityException("No coinciden las contraseñas");
                }
            } else {
                throw new NoResultException();
            }

        } catch (Exception e) {

            System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());

            sesion.setAttribute("mensajeError", "El email/contraseña no son correctos.Vuelva a intentarlo.");

            response.sendRedirect(request.getContextPath() + "/users/login");
        }

    }

    private void crearusuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contraseña = request.getParameter("contrasena");
        String contraseña2 = request.getParameter("contrasena2");

        if (email != null) {
            email = email.toLowerCase();
        }

        System.out.println("DEBUG -> Nombre: " + name + " | Email: " + email + " | Pass1: " + contraseña + " | Pass2: " + contraseña2);

        try {

            if (name == null || email == null || contraseña == null || contraseña.length() < 4 || contraseña2 == null || contraseña2.length() < 4 || name.length() < 3) {
                throw new IllegalArgumentException("Datos no válidos.");
            }

            if (!contraseña.equals(contraseña2)) {
                throw new IllegalArgumentException("Las dos contraseñas no coinciden.");
            }

            if (!email.matches("^[-\\w.%+]{1,64}@(?:[a-zA-Z0-9-]{1,63}\\.){1,125}[a-zA-Z]{2,63}")) {
                throw new IllegalArgumentException("El correo no es válido.");
            }

            System.out.println(email);

            TypedQuery<Long> queryEmail = em.createQuery("SELECT COUNT(u) FROM Usuarios u WHERE u.email = :email", Long.class);
            queryEmail.setParameter("email", email);
            if (queryEmail.getSingleResult() > 0) {
                throw new EntityExistsException("Ya existe el correo electrónico.");
            }

            Usuarios nuevoUsuario = new Usuarios(name, email, hashPassword(contraseña), "Usuario");

            save(nuevoUsuario);

            System.out.println("DEBUG -> ¡Usuario guardado con éxito! Redirigiendo...");

            response.sendRedirect(request.getContextPath() + "/users/login");

        } catch (Exception e) {

            System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/daw.views/register.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void save(Usuarios u) {
        Long id = u.getId();
        try {
            utx.begin();
            if (id == null) {
                em.persist(u);
                Log.log(Level.INFO, "Usuario creado");
            } else {
                Log.log(Level.INFO, "Usuario {0} actualizado", id);
                em.merge(u);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "Error en la transaccion", e);
            throw new RuntimeException(e);
        }

    }

    private boolean verificarPassword(String passwordIngresada, String hashAlmacenado) {
        try {
            return BCrypt.checkpw(passwordIngresada, hashAlmacenado);
        } catch (IllegalArgumentException e) {
            Log.log(java.util.logging.Level.SEVERE, "Error al verificar hash", e);
            return false;
        }

    }

    private String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt(12));

    }

    private void comprobarEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        String respuesta;

        if (email != null) {

            email = email.toLowerCase();

            if (email.matches("^[-\\w.%+]{1,64}@(?:[a-zA-Z0-9-]{1,63}\\.){1,125}[a-zA-Z]{2,63}$")) {

                TypedQuery<Usuarios> q = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
                q.setParameter("email", email);
                List<Usuarios> lu = q.getResultList();

                if (!lu.isEmpty()) {
                    respuesta = "El email ya existe";
                } else {
                    respuesta = "Ok";
                }

                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println(respuesta);
                }

            }

        }

    }

}
