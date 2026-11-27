<%--
    Document   : gestionPeliculas
    Created on : 28 oct 2026, 13:02:12
    Author     : admc0
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <div class="container mt-4">

            <div class="d-flex flex-column align-items-center text-center">

                <h1>Listado peliculas</h1>

                <c:if test="${empty requestScope.peliculas}">

                    <p class="mb-3"> No hay películas en la lista... </p>

                    <div >

                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" > Añadir Películas </button>

                    </div>




                </c:if>



                <c:if test="${not empty requestScope.peliculas}">



                    <div class="row w-100">
                        <c:forEach var="pelicula" items="${peliculas}">
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <img src="${pageContext.request.contextPath}/img/posters/${pelicula.posterUrl}.jpg" class="card-img-top" alt="${pelicula.titulo}" style="height: 400px; object-fit: cover;">
                                    <div class="card-body">
                                        <h5 class="card-title">${pelicula.titulo}</h5>
                                        <p class="card-text">${pelicula.descripcion}</p>
                                        <p class="text-muted">Estreno: ${pelicula.fecha_estreno} | Nota: ${pelicula.puntuacionMedia}</p>
                                    </div>
                                    <div class="container-fluid d-flex justify-content-between">

                                        <div class="mb-3">

                                            <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#ModalEditarPeli"
                                                    data-bs-id="${pelicula.id}"
                                                    data-bs-titulo="${pelicula.titulo}"
                                                    data-bs-descripcion="${pelicula.descripcion}"
                                                    data-bs-fecha="${pelicula.fecha_estreno}"
                                                    data-bs-puntuacion="${pelicula.puntuacionMedia}" 
                                                    > Editar </button>


                                        </div>
                                        <div class="mb-3">
                                            <form action="/home/pelicula/eliminar" method="POST">
                                                  
                                                <input type="hidden" name="idPelicula" value="${pelicula.id}">

                                                <button type="submit" class="btn btn-danger">Eliminar</button>
                                            </form>

                                        </div>

                                    </div>

                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="mt-3">

                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" > Añadir Películas </button>

                    </div>

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
                        <form id="formPeliculas" action="/home/pelicula/anadir" method="POST" enctype="multipart/form-data">

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="titulo">Título:</label>
                                <input id="titulo" type="text" name="titulo" class="form-control flex-grow-1" required>

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="descripcion">Descripción:</label>
                                <textarea id="descripcion" name="descripcion" class="form-control flex-grow-1" rows="3"></textarea>

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="fecha" >Fecha de Estreno:</label>
                                <input id="fecha" name="fecha" type="date" class="form-control flex-grow-1">

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="poster"> Póster:</label>
                                <input id="poster" name ="poster" type="file" class="form-control flex-grow-1">

                            </div>

                            <div class="d-flex align-items-center mb-0">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="puntuacionMedia">Puntuación Media:</label>
                                <select id="puntuacionMedia" name="puntuacionMedia" type="number" class="form-control flex-grow-1" >
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
                                    <option value="" selected disabled hidden >Elige una opcion</option>
                                </select>

                            </div>



                        </form>



                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" form="formPeliculas" class="btn btn-primary">Añadir Película</button>
                    </div>

                </div>
            </div>
        </div>


        <div class="modal fade" id="ModalEditarPeli" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Datos de la película</h5>
                    </div>
                    <div class="modal-body">
                        <form id="formEditarPelicula" action="/home/pelicula/editar" method="POST" enctype="multipart/form-data">


                            <input type="hidden" id="edit-id" name="id">

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="edit-titulo">Título:</label>
                                <input id="edit-titulo" type="text" name="titulo" class="form-control flex-grow-1" required>

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="edit-descripcion">Descripción:</label>
                                <textarea id="edit-descripcion" name="descripcion" class="form-control flex-grow-1"></textarea>

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="edit-fecha" >Fecha de Estreno:</label>
                                <input id="edit-fecha" name="fecha" type="date" class="form-control flex-grow-1">

                            </div>

                            <div class="d-flex align-items-center mb-3">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="edit-poster"> Póster:</label>
                                <input id="edit-poster" name ="poster" type="file" class="form-control flex-grow-1">

                            </div>

                            <div class="d-flex align-items-center mb-0">

                                <label style="width: 110px" class="flex-shrink-0 mb-0" for="edit-puntuacionMedia">Puntuación Media:</label>
                                <select id="edit-puntuacionMedia" name="puntuacionMedia" type="number" class="form-control flex-grow-1" >
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
                                    <option value="" selected disabled hidden >Elige una opcion</option>
                                </select>

                            </div>



                        </form>



                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <button type="submit" form="formEditarPelicula" class="btn btn-primary">Editar Película</button>
                    </div>

                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>

    </body>

</html>
