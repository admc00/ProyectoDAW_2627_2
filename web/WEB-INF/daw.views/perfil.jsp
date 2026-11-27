<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>Mi Perfil</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />

        <div class="container mt-5 mb-5">
            <c:if test="${not empty sessionScope.usuarioLogueado}">
                <div class="card shadow-sm">
                    <div class="row g-0">
                        <div class="col-md-4 p-3 d-flex align-items-center justify-content-center">
                            <div class="border rounded bg-light d-flex align-items-center justify-content-center w-100" style="height: 280px;">
                                <c:if test="${not empty sessionScope.usuarioLogueado.perfilUrl}">
                                    <img src="${pageContext.request.contextPath}/img/perfiles/${sessionScope.usuarioLogueado.perfilUrl}.jpg"
                                         class="img-fluid rounded w-100 h-100"
                                         alt="${sessionScope.usuarioLogueado.nombre}"
                                         style="object-fit: cover;">
                                </c:if>
                                <c:if test="${empty sessionScope.usuarioLogueado.perfilUrl}">
                                    <span class="text-muted">Sin foto de perfil</span>
                                </c:if>
                            </div>
                        </div>

                        <div class="col-md-8 d-flex flex-column">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start mb-2">
                                    <h1 class="card-title mb-0">${sessionScope.usuarioLogueado.nombre}</h1>
                                </div>

                                <p class="text-muted mb-4">
                                    <strong>Correo:</strong> ${sessionScope.usuarioLogueado.email}
                                </p>

                                <h5>Bibliografía</h5>
                                <c:if test="${not empty sessionScope.usuarioLogueado.bibliografía}">
                                    <p class="card-text">${sessionScope.usuarioLogueado.bibliografía}</p>
                                </c:if>
                                <c:if test="${empty sessionScope.usuarioLogueado.bibliografía}">
                                    <p class="card-text text-muted">Sin bibliografía todavía.</p>
                                </c:if>
                            </div>

                            <div class="card-footer bg-transparent border-0 mt-auto pb-3 px-3 d-flex justify-content-between">
                                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                                    Volver al inicio
                                </a>
                                <a href="${pageContext.request.contextPath}/users/logout" class="btn btn-danger">
                                    Cerrar sesión
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="container mt-4 mb-5">
            <h3 class="mb-4">Reseñas</h3>

            <c:if test="${empty sessionScope.resenasUsuario}">
                <p class="text-muted">Todavía no has escrito ninguna reseña.</p>
            </c:if>

            <c:if test="${not empty sessionScope.resenasUsuario}">
                <c:forEach var="r" items="${sessionScope.resenasUsuario}">
                    <div class="card shadow-sm mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <div>
                                    <strong>${r.pelicula.titulo}</strong>
                                    <span class="text-muted ms-2">${r.fecha_reseña}</span>
                                </div>
                                <span class="badge bg-warning text-dark">★ ${r.puntuacion} / 10</span>
                            </div>

                            <p class="card-text mb-0">${r.texto}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>
    </body>
</html>