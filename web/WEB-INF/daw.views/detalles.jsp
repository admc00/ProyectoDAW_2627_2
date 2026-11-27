<%-- 
    Document   : detalles
    Created on : 27 nov 2026, 5:31:23
    Author     : admc0
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>${pelicula.titulo} - Detalles</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />

        <div class="container mt-5">
            <div class="card shadow-sm">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="${pageContext.request.contextPath}/img/posters/${pelicula.posterUrl}.jpg"class="img-fluid rounded-start w-100" alt="${pelicula.titulo}" style="max-height: 650px;" class="img-fluid rounded">
                    </div>
                    <div class="col-md-8 d-flex flex-column">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <h1 class="card-title mb-0">${pelicula.titulo}</h1>
                                <span class="badge bg-warning text-dark fs-5">
                                    ★ ${pelicula.puntuacionMedia} / 10
                                </span>
                            </div>

                            <p class="text-muted mb-4">
                                <strong>Fecha de estreno:</strong> ${pelicula.fecha_estreno}
                            </p>

                            <h5>Sinopsis</h5>
                            <p class="card-text">${pelicula.descripcion}</p>
                        </div>

                        <div class="card-footer bg-transparent border-0 mt-auto pb-3 px-3 d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                                Volver al inicio
                            </a>

                            <form action="${pageContext.request.contextPath}/pelicula/anadirCesta" method="POST">
                                <input type="hidden" name="idPelicula" value="${pelicula.id}">
                                <button type="submit" class="btn btn-primary">Ver más tarde</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container mt-4 mb-5">
            <h3 class="mb-4">Reseñas</h3>


            <c:if test="${not empty sessionScope.usuarioLogueado}">
                <div class="card shadow-sm mb-4">
                    <div class="card-body">
                        <h5 class="card-title mb-3">Escribe una reseña</h5>
                        <form action="${pageContext.request.contextPath}/resena/anadir" method="POST">
                            <input type="hidden" name="idPelicula" value="${pelicula.id}">

                            <div class="mb-3">
                                <label for="puntuacion" class="form-label">Puntuación</label>
                                <select id="puntuacion" name="puntuacion" class="form-select" style="max-width: 150px;" required>
                                    <option value="10">10</option>
                                    <option value="9">9</option>
                                    <option value="8">8</option>
                                    <option value="7">7</option>
                                    <option value="6">6</option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                    <option value="0">0</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="texto" class="form-label">Opinión</label>
                                <textarea id="texto" name="texto" class="form-control" rows="3" required></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary">Publicar reseña</button>
                        </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.usuarioLogueado}">
                <div class="mb-4">
                    <a href="${pageContext.request.contextPath}/users/login">Inicia sesión para poder escribir una reseña.</a>
                </div>
            </c:if>


            <c:if test="${empty resenas}">
                <p class="text-muted">Todavía no hay reseñas para esta película.</p>
            </c:if>

            <c:if test="${not empty resenas}">
                <c:forEach var="r" items="${resenas}">
                    <div class="card shadow-sm mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <div>
                                    <strong>${r.usuario.nombre}</strong>
                                    <span class="text-muted ms-2">${r.fecha_reseña}</span>
                                </div>
                                <span class="badge bg-warning text-dark">★ ${r.puntuacion} / 10</span>
                            </div>

                            <p class="card-text mb-3">${r.texto}</p>

                            <c:if test="${not empty sessionScope.usuarioLogueado and r.usuario.id == sessionScope.usuarioLogueado.id}">
                                <div class="d-flex gap-2">
                                    <button type="button"
                                            class="btn btn-warning btn-sm"
                                            data-bs-toggle="modal"
                                            data-bs-target="#modalEditarResena"
                                            data-id="${r.id}"
                                            data-puntuacion="${r.puntuacion}"
                                            data-texto="${r.texto}">
                                        Editar
                                    </button>

                                    <form action="${pageContext.request.contextPath}/resena/eliminar" method="POST">
                                        <input type="hidden" name="idResena" value="${r.id}">
                                        <input type="hidden" name="idPelicula" value="${pelicula.id}">
                                        <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <div class="modal fade" id="modalEditarResena" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar reseña</h5>
                    </div>
                    <div class="modal-body">
                        <form id="formEditarResena" action="${pageContext.request.contextPath}/resena/editar" method="POST">
                            <input type="hidden" id="edit-idResena" name="idResena">
                            <input type="hidden" name="idPelicula" value="${pelicula.id}">

                            <div class="mb-3">
                                <label for="edit-puntuacion" class="form-label">Puntuación</label>
                                <select id="edit-puntuacion" name="puntuacion" class="form-select" required>
                                    <option value="10">10</option>
                                    <option value="9">9</option>
                                    <option value="8">8</option>
                                    <option value="7">7</option>
                                    <option value="6">6</option>
                                    <option value="5">5</option>
                                    <option value="4">4</option>
                                    <option value="3">3</option>
                                    <option value="2">2</option>
                                    <option value="1">1</option>
                                    <option value="0">0</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="edit-texto" class="form-label">Opinión</label>
                                <textarea id="edit-texto" name="texto" class="form-control" rows="3" required></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" form="formEditarResena" class="btn btn-primary">Guardar cambios</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>
    </body>
</html>