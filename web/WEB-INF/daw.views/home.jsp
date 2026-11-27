<%-- 
    Document   : home
    Created on : 9 oct 2026, 11:03:37
    Author     : FX506
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />

        <div class="container my-4">
            <h1 class="mb-4 text-center">Catálogo de películas</h1>

            <c:if test="${not empty peliculas}">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-lg-5 g-4">
                    <c:forEach var="p" items="${peliculas}">
                        <div class="col">
                            <a href="${pageContext.request.contextPath}/pelicula/detalles?id=${p.id}"
                               class="text-decoration-none text-dark">
                                <div class="card h-100 shadow-sm">
                                    <img src="${pageContext.request.contextPath}/img/posters/${p.posterUrl}.jpg" class="card-img-top" alt="${p.titulo}" style="height: 320px; object-fit: cover;">
                                    <div class="card-body text-center">
                                        <h5 class="card-title mb-0">${p.titulo}</h5>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty peliculas}">
                <p class="text-center text-muted">No hay películas disponibles por el momento.</p>
            </c:if>
        </div>



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>

    </body>
</html>
