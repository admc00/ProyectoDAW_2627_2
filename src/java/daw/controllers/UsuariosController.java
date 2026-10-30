/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        String contrasena = request.getParameter("contrasena");
        String email = request.getParameter("email");

        try {
            if (contrasena == null || email == null) {
                throw new IllegalArgumentException("Datos no válidos");
            }
            TypedQuery<Usuarios> queryUsuarios = em.createNamedQuery("Usuarios.findByEmail", Usuarios.class);
            queryUsuarios.setParameter("email", email);
            if (email.equals(queryUsuarios.getSingleResult().getEmail())) {
                Usuarios u = queryUsuarios.getSingleResult();

                if (verificarPassword(contrasena, u.getContraseña())) {
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioLogueado", u);
                    response.sendRedirect(request.getContextPath());
                } else {
                    throw new SecurityException("No coinciden las contraseñas");
                }
            } else {
                throw new NoResultException();
            }

        } catch (Exception e) {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/daw.views/login.jsp");
            dispatcher.forward(request, response);
        }

    }

    private void crearusuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contraseña = request.getParameter("contrasena");
        String contraseña2 = request.getParameter("contrasena2");

        System.out.println("DEBUG -> Nombre: " + name + " | Email: " + email + " | Pass1: " + contraseña + " | Pass2: " + contraseña2);

        try {

            if (name == null || email == null || !email.contains("@") || contraseña == null || contraseña.length() < 4 || contraseña2 == null || contraseña2.length() < 4) {
                throw new IllegalArgumentException("Datos no válidos.");
            }

            if (!contraseña.equals(contraseña2)) {
                throw new IllegalArgumentException("Las dos contraseñas no coinciden.");
            }

            TypedQuery<Integer> queryEmail = em.createQuery("SELECT COUNT(u) FROM Usuarios u WHERE u.email = :email", Integer.class);
            queryEmail.setParameter("email", email);
            if (queryEmail.getSingleResult() > 0) {
                throw new EntityExistsException("Ya existe el correo electrónico.");
            }

            Usuarios nuevoUsuario = new Usuarios(name, email, hashPassword(contraseña));

            save(nuevoUsuario);

            System.out.println("DEBUG -> ¡Usuario guardado con éxito! Redirigiendo...");

            response.sendRedirect(request.getContextPath() + "/users/login");

        } catch (Exception e) {

            System.err.println("ERROR CAPTURADO EN EL CATCH:");
            e.printStackTrace();

            request.setAttribute("error", e.getMessage());
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

}
