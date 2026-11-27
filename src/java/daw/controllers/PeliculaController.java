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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Float.parseFloat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author FX506
 */
@MultipartConfig
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

                        if (usuario.getRol().equals("Admin")) {

                            TypedQuery<Pelicula> query = em.createNamedQuery("Peliculas.findAll", Pelicula.class);
                            List<Pelicula> listaPeliculas = query.getResultList();

                            request.setAttribute("peliculas", listaPeliculas);
                            vista = "gestionPeliculas";
                        } else {

                            sesion.setAttribute("mensajeError", "No tienes permiso de administrador.");
                            response.sendRedirect(request.getContextPath());
                            return;
                        }

                    } else {

                        sesion.setAttribute("mensajeError", "No tienes permiso de administrador.");
                        response.sendRedirect(request.getContextPath());
                        return;

                    }

                } else {

                    vista = "home";
                }
                break;

            case "/detalles":
                String idParam = request.getParameter("id");

                if (idParam == null || idParam.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath());
                    return;
                }

                try {
                    Long idPelicula = Long.parseLong(idParam.trim());

                    TypedQuery<Pelicula> queryDetalles = em.createNamedQuery("Peliculas.findByID", Pelicula.class);
                    queryDetalles.setParameter("id", idPelicula);
                    List<Pelicula> resultados = queryDetalles.getResultList();

                    if (resultados.isEmpty()) {
                        request.getSession().setAttribute("mensajeError", "La película solicitada no existe.");
                        response.sendRedirect(request.getContextPath());
                        return;
                    }

                    TypedQuery<Reseña> queryResenas = em.createNamedQuery("Resena.findByPeliculaId", Reseña.class);
                    queryResenas.setParameter("idPelicula", idPelicula);
                    List<Reseña> listaReseñas = queryResenas.getResultList();
                    request.setAttribute("resenas", listaReseñas);

                    request.setAttribute("pelicula", resultados.get(0));
                    vista = "detalles";

                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath());
                    return;
                }
                break;

            default:
                if ("/anadir".equals(accion) || "/editar".equals(accion) || "/eliminar".equals(accion)) {
                    HttpSession sesionDefault = request.getSession();
                    sesionDefault.setAttribute("mensajeError", "No tienes permiso de administrador.");
                    response.sendRedirect(request.getContextPath());
                    return;
                }
                response.sendRedirect(request.getContextPath());
                return;

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession sesion = request.getSession();

        if (sesion != null) {

            Usuarios admin = (Usuarios) sesion.getAttribute("usuarioLogueado");

            String accion = "/pelicula";

            if (request.getServletPath().equals(accion)) {
                if (request.getPathInfo() != null) {
                    accion = request.getPathInfo();
                }
            }

            if (accion != null) {

                switch (accion) {

                    case "/anadir":

                        if (admin != null && admin.getRol().equals("Admin")) {

                            String titulo = request.getParameter("titulo");
                            String descripcion = request.getParameter("descripcion");
                            String fecha = request.getParameter("fecha");
                            String puntuacion = request.getParameter("puntuacionMedia");
                            final Part posterPart = request.getPart("poster");

                            if (titulo == null || titulo.trim().isEmpty() || descripcion == null || fecha == null || puntuacion == null || posterPart == null) {
                                sesion.setAttribute("mensajeError", "Datos no válidos.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                                return;
                            }

                            String tituloNormalizado = Normalizer.normalize(titulo, Normalizer.Form.NFD);
                            tituloNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                            tituloNormalizado = tituloNormalizado.trim().toLowerCase().replaceAll("[^a-z0-9]", "");

                            TypedQuery<Pelicula> queryAnadir = em.createNamedQuery("Peliculas.findByTituloNormalizado", Pelicula.class);
                            queryAnadir.setParameter("tituloNormalizado", tituloNormalizado);
                            List<Pelicula> listaPeliculas = queryAnadir.getResultList();

                            if (!listaPeliculas.isEmpty()) {
                                sesion.setAttribute("mensajeError", "Ya existe una película con ese título.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                                return;
                            }

                            try {

                                String relativePathFolder = "" + File.separator + "img" + File.separator + "posters";
                                Log.log(Level.INFO, "webPath: {0} ", relativePathFolder);
                                String absolutePathFolder = getServletContext().getRealPath(relativePathFolder);
                                Log.log(Level.INFO, "Path: {0} ", absolutePathFolder);

                                String filename = "poster-" + tituloNormalizado;
                                String posterUrl = filename;

                                Pelicula p = new Pelicula();
                                p.setTitulo(titulo);
                                p.setTituloNormalizado(tituloNormalizado);
                                p.setFecha_estreno(fecha);
                                p.setDescripcion(descripcion);
                                p.setPuntuacionMedia(parseFloat(puntuacion));
                                p.setPosterUrl(posterUrl);

                                utx.begin();
                                em.persist(p);
                                utx.commit();

                                File f = new File(absolutePathFolder + File.separator + filename + ".jpg");
                                OutputStream fos = new FileOutputStream(f);
                                InputStream filecontent = posterPart.getInputStream();
                                int read = 0;
                                final byte[] bytes = new byte[1024];
                                while ((read = filecontent.read(bytes)) != -1) {
                                    fos.write(bytes, 0, read);
                                }
                                fos.close();
                                filecontent.close();

                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");

                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());

                                sesion.setAttribute("mensajeError", "No se pudo añadir la película.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");

                            }

                        } else {
                            sesion.setAttribute("mensajeError", "No tienes permisos de administrador para realizar esta acción.");
                            response.sendRedirect(request.getContextPath());
                        }

                        break;

                    case "/editar":

                        if (admin != null && admin.getRol().equals("Admin")) {

                            Long id = Long.parseLong(request.getParameter("id"));
                            String tituloEdit = request.getParameter("titulo");
                            String descripcionEdit = request.getParameter("descripcion");
                            String fechaEdit = request.getParameter("fecha");
                            String puntuacionEdit = request.getParameter("puntuacionMedia");
                            final Part posterPartEdit = request.getPart("poster");

                            if (tituloEdit == null || descripcionEdit == null || fechaEdit == null || puntuacionEdit == null) {
                                sesion.setAttribute("mensajeError", "Datos no válidos.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                                return;
                            }

                            String tituloNormalizado = Normalizer.normalize(tituloEdit, Normalizer.Form.NFD);
                            tituloNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                            tituloNormalizado = tituloNormalizado.trim().toLowerCase().replaceAll("[^a-z0-9]", "");

                            TypedQuery<Pelicula> queryEditar = em.createNamedQuery("Peliculas.findByTituloNormalizado", Pelicula.class);
                            queryEditar.setParameter("tituloNormalizado", tituloNormalizado);
                            List<Pelicula> listaPeliculas = queryEditar.getResultList();

                            boolean existe = false;
                            for (Pelicula peliDuplicada : listaPeliculas) {

                                if (peliDuplicada.getId() != id) {
                                    existe = true;
                                    break;
                                }

                            }

                            if (existe) {

                                sesion.setAttribute("mensajeError", "Ya existe una película con ese título.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                                return;

                            }

                            try {

                                utx.begin();

                                TypedQuery<Pelicula> queryPelicula = em.createNamedQuery("Peliculas.findByID", Pelicula.class);
                                queryPelicula.setParameter("id", id);

                                Pelicula resultado = queryPelicula.getSingleResult();

                                if (id == resultado.getId()) {
                                    Pelicula p = resultado;

                                    if (p != null) {

                                        p.setTitulo(tituloEdit);
                                        p.setTituloNormalizado(tituloNormalizado);
                                        p.setDescripcion(descripcionEdit);
                                        p.setFecha_estreno(fechaEdit);
                                        p.setPuntuacionMedia(Float.parseFloat(puntuacionEdit));

                                        if (posterPartEdit != null && posterPartEdit.getSize() > 0) {
                                            String relativePathFolder = "" + File.separator + "img" + File.separator + "posters";
                                            String absolutePathFolder = getServletContext().getRealPath(relativePathFolder);

                                            String filename = "poster-" + tituloNormalizado;
                                            String posterUrl = filename;

                                            p.setPosterUrl(posterUrl);

                                            File f = new File(absolutePathFolder + File.separator + filename + ".jpg");
                                            OutputStream fos = new FileOutputStream(f);
                                            InputStream filecontent = posterPartEdit.getInputStream();
                                            int read = 0;
                                            final byte[] bytes = new byte[1024];
                                            while ((read = filecontent.read(bytes)) != -1) {
                                                fos.write(bytes, 0, read);
                                            }
                                            fos.close();
                                            filecontent.close();
                                        }

                                        em.merge(p);

                                    }

                                    utx.commit();
                                    response.sendRedirect(request.getContextPath() + "/pelicula/gestion");

                                }

                            } catch (Exception e) {

                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());

                                sesion.setAttribute("mensajeError", "No se pudo editar la película.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                            }

                        } else {
                            sesion.setAttribute("mensajeError", "No tienes permisos de administrador para realizar esta acción.");
                            response.sendRedirect(request.getContextPath());
                        }

                        break;

                    case "/eliminar":

                        if (admin != null && admin.getRol().equals("Admin")) {
                            try {

                                Long id = Long.parseLong(request.getParameter("idPelicula"));

                                utx.begin();
                                TypedQuery<Pelicula> queryPelicula = em.createNamedQuery("Peliculas.findByID", Pelicula.class);
                                queryPelicula.setParameter("id", id);

                                Pelicula resultado = queryPelicula.getSingleResult();

                                if (id == resultado.getId()) {
                                    Pelicula p = resultado;

                                    if (p != null) {
                                        em.remove(p);
                                    }

                                    utx.commit();

                                    response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                                }

                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());

                                sesion.setAttribute("mensajeError", "No se pudo eliminar la película.");
                                response.sendRedirect(request.getContextPath() + "/pelicula/gestion");
                            }

                        } else {
                            sesion.setAttribute("mensajeError", "No tienes permisos de administrador para realizar esta acción.");
                            response.sendRedirect(request.getContextPath());
                        }

                        break;

                    case "/anadirCesta":

                        String idPeliculaAnadir = request.getParameter("idPelicula");

                        if (idPeliculaAnadir != null && !idPeliculaAnadir.isEmpty()) {
                            try {
                                Long idPelicula = Long.parseLong(idPeliculaAnadir);

                                TypedQuery<Pelicula> query = em.createNamedQuery("Peliculas.findByID", Pelicula.class);
                                query.setParameter("id", idPelicula);
                                Pelicula peliculaAnadir = query.getSingleResult();

                                if (peliculaAnadir != null) {
                                    List<Pelicula> cesta = (List<Pelicula>) sesion.getAttribute("cesta");
                                    if (cesta == null) {
                                        cesta = new ArrayList<>();
                                    }

                                    boolean yaEsta = false;
                                    for (int i = 0; i < cesta.size(); i++) {
                                        if (cesta.get(i).getId().equals(peliculaAnadir.getId())) {
                                            yaEsta = true;
                                            break;
                                        }
                                    }

                                    if (!yaEsta) {
                                        cesta.add(peliculaAnadir);
                                        sesion.setAttribute("mensajeCesta", "Película añadida a ver más tarde.");
                                    } else {
                                        sesion.setAttribute("mensajeCesta", "La película ya está en tu cesta.");
                                    }

                                    sesion.setAttribute("cesta", cesta);
                                }
                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());
                            }
                        }

                        response.sendRedirect(request.getContextPath() + "/pelicula/detalles?id=" + idPeliculaAnadir);
                        break;

                    case "/quitarCesta":

                        String idPeliculaQuitar = request.getParameter("idPelicula");

                        if (idPeliculaQuitar != null && !idPeliculaQuitar.isEmpty()) {
                            try {
                                Long idPelicula = Long.parseLong(idPeliculaQuitar);
                                List<Pelicula> cesta = (List<Pelicula>) sesion.getAttribute("cesta");

                                if (cesta != null) {
                                    for (int i = 0; i < cesta.size(); i++) {
                                        if (cesta.get(i).getId().equals(idPelicula)) {
                                            cesta.remove(i);
                                            break;
                                        }
                                    }
                                    sesion.setAttribute("cesta", cesta);
                                }
                            } catch (Exception e) {
                                System.err.println("ERROR CAPTURADO EN EL CATCH:" + e.getMessage());
                            }
                        }

                        String volverAQuitar = request.getParameter("volverA");
                        if (volverAQuitar != null && !volverAQuitar.isEmpty()) {
                            response.sendRedirect(volverAQuitar);
                        } else {
                            response.sendRedirect(request.getContextPath());
                        }
                        break;

                }
            } else {

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

}
