/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package daw.controllers;

import daw.model.Pelicula;
import daw.model.Reseña;
import daw.model.Usuarios;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author admc0
 */
@WebServlet(name = "ResenaController", urlPatterns = {"/resena/*"})
public class ResenaController extends HttpServlet {

    @PersistenceContext(unitName = "ProyectoDAW_2627PU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(ResenaController.class.getName());

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

        HttpSession sesion = request.getSession();

        if (sesion != null) {

            Usuarios usuario = (Usuarios) sesion.getAttribute("usuarioLogueado");

            String accion = "/resena";

            if (request.getServletPath().equals(accion)) {
                if (request.getPathInfo() != null) {
                    accion = request.getPathInfo();
                }
            }

            if (accion != null) {

                switch (accion) {

                    case "/anadir":

                        if (usuario != null) {

                            String idPeliculaParam = request.getParameter("idPelicula");
                            String texto = request.getParameter("texto");
                            String puntuacionParam = request.getParameter("puntuacion");

                            if (idPeliculaParam == null || texto == null || texto.isEmpty() || puntuacionParam == null) {
                                sesion.setAttribute("mensajeError", "Datos no válidos.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                                return;
                            }

                            try {
                                Long idPelicula = Long.parseLong(idPeliculaParam);
                                float puntuacion = Float.parseFloat(puntuacionParam);

                                TypedQuery<Pelicula> queryPelicula = em.createNamedQuery("Peliculas.findByID", Pelicula.class);
                                queryPelicula.setParameter("id", idPelicula);
                                List<Pelicula> peliculas = queryPelicula.getResultList();

                                if (peliculas.isEmpty()) {
                                    sesion.setAttribute("mensajeError", "La película no existe.");
                                    response.sendRedirect(request.getContextPath());
                                    return;
                                }

                                Pelicula p = peliculas.get(0);

                                Reseña r = new Reseña();
                                r.setTexto(texto);
                                r.setPuntuacion(puntuacion);
                                r.setFecha_reseña(LocalDate.now().toString());
                                r.setPelicula(p);
                                r.setUsuario(usuario);

                                utx.begin();
                                em.persist(r);
                                utx.commit();

                                response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPelicula);

                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());
                                sesion.setAttribute("mensajeError", "No se pudo añadir la reseña.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                            }

                        } else {
                            sesion.setAttribute("mensajeError", "Debes iniciar sesión para escribir una reseña.");
                            response.sendRedirect(request.getContextPath() + "/users/login");
                        }

                        break;

                    case "/editar":

                        if (usuario != null) {

                            String idResenaParam = request.getParameter("idResena");
                            String idPeliculaParam = request.getParameter("idPelicula");
                            String textoEdit = request.getParameter("texto");
                            String puntuacionEditParam = request.getParameter("puntuacion");

                            if (idResenaParam == null || textoEdit == null || textoEdit.isEmpty() || puntuacionEditParam == null) {
                                sesion.setAttribute("mensajeError", "Datos no válidos.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                                return;
                            }

                            try {
                                Long idResena = Long.parseLong(idResenaParam);
                                float puntuacionEdit = Float.parseFloat(puntuacionEditParam);

                                utx.begin();

                                TypedQuery<Reseña> queryResena = em.createNamedQuery("Resena.findById", Reseña.class);
                                queryResena.setParameter("id", idResena);
                                Reseña r = queryResena.getSingleResult();

                                if (r != null && r.getUsuario().getId().equals(usuario.getId())) {
                                    r.setTexto(textoEdit);
                                    r.setPuntuacion(puntuacionEdit);
                                    em.merge(r);
                                } else {
                                    sesion.setAttribute("mensajeError", "No tienes permiso para editar esta reseña.");
                                }

                                utx.commit();

                                response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);

                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());
                                sesion.setAttribute("mensajeError", "No se pudo editar la reseña.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                            }

                        } else {
                            sesion.setAttribute("mensajeError", "Debes iniciar sesión para editar una reseña.");
                            response.sendRedirect(request.getContextPath() + "/users/login");
                        }

                        break;

                    case "/eliminar":

                        if (usuario != null) {

                            String idResenaParam = request.getParameter("idResena");
                            String idPeliculaParam = request.getParameter("idPelicula");

                            if (idResenaParam == null) {
                                sesion.setAttribute("mensajeError", "Datos no válidos.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                                return;
                            }

                            try {
                                Long idResena = Long.parseLong(idResenaParam);

                                utx.begin();

                                TypedQuery<Reseña> queryResena = em.createNamedQuery("Resena.findById", Reseña.class);
                                queryResena.setParameter("id", idResena);
                                Reseña r = queryResena.getSingleResult();

                                if (r != null && r.getUsuario().getId().equals(usuario.getId())) {
                                    em.remove(r);
                                } else {
                                    sesion.setAttribute("mensajeError", "No tienes permiso para eliminar esta reseña.");
                                }

                                utx.commit();

                                response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);

                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());
                                sesion.setAttribute("mensajeError", "No se pudo eliminar la reseña.");
                                if (idPeliculaParam != null) {
                                    response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaParam);
                                } else {
                                    response.sendRedirect(request.getContextPath());
                                }
                            }

                        } else {
                            sesion.setAttribute("mensajeError", "Debes iniciar sesión para eliminar una reseña.");
                            response.sendRedirect(request.getContextPath() + "/users/login");
                        }

                        break;

                }
            } else {
                sesion.setAttribute("mensajeError", "Acción no válida.");
                response.sendRedirect(request.getContextPath());
            }

        } else {
            sesion.setAttribute("mensajeError", "No se pudo procesar la solicitud.");
            response.sendRedirect(request.getContextPath());
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
