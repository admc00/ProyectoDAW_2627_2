<%--
    Document   : gestionPeliculas
    Created on : 28 oct 2026, 13:02:12
    Author     : admc0
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />

        <div class="container mt-4">

            <div class="d-flex flex-column align-items-center text-center">

                <h1>Listado peliculas</h1>

                <c:if test="${ empty sessionScope.peliculas}">

                    <p class="mb-3"> No hay películas en la lista... </p>

                    <div >

                        <button id="botonAnadir" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" > Añadir Películas </button>

                    </div>




                </c:if>



                <c:if test="${not empty sessionScope.peliculas}">

                    <c:forEach var="pelicula" items="${peliculas}">

                    </c:forEach>

                </c:if>
            </div>

        </div>

        <div class="modal fade" id="myModal" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Datos de la película</h5>
                    </div>
                    <div class="modal-body">
                        <form action="/home/pelicula/añadir" method="POST" enctype="multipart/form-data">

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 100px" for="titulo">Título:</label>
                                <input id="titulo" type="text" name="titulo" required>

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 100px" for="descripcion">Descripción:</label>
                                <input id="descripcion" name="descripcion">

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 100px" for="fecha" >Fecha de Estreno:</label>
                                <input id="fecha" name="fecha" type="date">

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 100px" for="poster"> Póster:</label>
                                <input id="poster" name ="poster" type="file">

                            </div>

                            <div class="d-flex align-items-center mb-0">

                                <label style="width: 100px" for="puntuacionMedia">Puntuación Media:</label>
                                <select id="puntuacion" name="puntuacion" type="number" class="form-select-sm" >
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
                                    <option value="" selected disable hidden >Elige una opcion</option>
                                </select>

                            </div>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary">Añadir Película</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>

    </body>

</html>
