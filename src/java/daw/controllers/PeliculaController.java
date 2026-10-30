/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.model.Pelicula;
import daw.model.Usuarios;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
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
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author FX506
 */
@WebServlet(name = "PeliculaController", urlPatterns = {"/pelicula/*"})
public class PeliculaController extends HttpServlet {

    @PersistenceContext(unitName = "ProyectoDAW_2627PU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(PeliculaController.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        String accion = "/pelicula";

        if (request.getServletPath().equals(accion)) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            }
        }

        switch (accion) {

            case "/gestion":

                HttpSession sesion = request.getSession();

                if (sesion != null) {

                    Usuarios usuario = (Usuarios) sesion.getAttribute("usuarioLogueado");

                    if (usuario != null) {

                        if (usuario.getEmail().equals("admc0000@gmail.com")) {

                            TypedQuery<Pelicula> query = em.createNamedQuery("Peliculas.findAll", Pelicula.class);
                            List<Pelicula> listaPeliculas = query.getResultList();

                            request.setAttribute("peliculas", listaPeliculas);
                            vista = "gestionPeliculas";
                        }

                    } else {

                        vista = "home";
                    }

                } else {

                    vista = "home";
                }
                break;

            case "/buscar":

            case "/detalles":

            case "/guardar":

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

        String accion = "/pelicula";

        if (request.getServletPath().equals(accion)) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            }
        }

        if (accion != null) {

            switch (accion) {

                case "/añadir":

                case "/editar":

                case "/eliminar":

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

}
