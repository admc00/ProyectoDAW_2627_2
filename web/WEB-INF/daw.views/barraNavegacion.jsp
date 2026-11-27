<%-- 
    Document   : barraNavegacion
    Created on : 22 oct 2026, 19:42:27
    Author     : admc0
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar py-3" style="background-color: #000066">
    <div  class ="container-fluid d-flex justify-content-between align-items-center">

        <a class="text-decoration-none" style="color: #00ff00" href="/home">
            Inicio
        </a>

        <div class="d-flex gap-3 align-items-center ">
            <c:if test="${not empty sessionScope.usuarioLogueado}">



                <c:if test="${usuarioLogueado.rol eq 'Admin'}">

                    <a class="text-decoration-none" style="color: #00ff00" href="/home/pelicula/gestion">Gestionar Peliculas</a>

                </c:if>

                <c:if test="${usuarioLogueado.rol eq 'Usuario'}">

                    <a class="text-decoration-none" style="color: #00ff00" href="/home/users/perfil">
                        Mi Perfil
                    </a>

                </c:if>


                <a class="text-decoration-none" style="color: #00ff00" href="/home/users/logout">Cerrar Sesión</a>

                <button type="button"
                        class="btn ms-2"
                        data-bs-toggle="modal"
                        data-bs-target="#modalCesta">
                    <img src="${pageContext.request.contextPath}/img/bookmark.png" alt="Cesta" style="width: 18px; height: 18px;">
                    <c:if test="${not empty sessionScope.cesta}">

                        <span>${sessionScope.cestaPeliculas.size()}</span>

                    </c:if>
                </button>

            </c:if>

            <c:if test="${empty sessionScope.usuarioLogueado}">
                <a class="text-decoration-none" style="color: #00ff00" href="/home/users/login">Iniciar Sesión</a>
                <a class="text-decoration-none" style="color: #00ff00" href="/home/users/register">Registrarse</a>
            </c:if>
        </div>

    </div>




    <div class="toast-container position-fixed bottom-0 end-0 p-3" style="z-index: 1080;">
        <div id="toastPermisos" class="toast align-items-center text-bg-danger border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div id="toastPermisosTexto" class="toast-body">${sessionScope.mensajeError}</div>
                <%
                    String mensajeError = (String) session.getAttribute("mensajeError");
                    if (mensajeError != null) {
                        session.removeAttribute("mensajeError");
                    }
                %>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Cerrar"></button>
            </div>
        </div>
    </div>

    <div class="toast-container position-fixed bottom-0 end-0 p-3" style="z-index: 1080;">
        <div id="toastCesta" class="toast align-items-center text-bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div id="toastCestaTexto" class="toast-body">${sessionScope.mensajeCesta}</div>
                <%
                    String mensajeCesta = (String) session.getAttribute("mensajeCesta");
                    if (mensajeCesta != null) {
                        session.removeAttribute("mensajeCesta");
                    }
                %>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Cerrar"></button>
            </div>
        </div>
    </div>



    <div class="modal fade" id="modalCesta" tabindex="-1" data-bs-backdrop="static" data-bs-keyboard="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Mi cesta</h5>
                </div>
                <div class="modal-body">
                    <c:if test="${empty sessionScope.cesta}">
                        <p class="text-muted mb-0">La cesta está vacía.</p>
                    </c:if>

                    <c:if test="${not empty sessionScope.cesta}">
                        <c:forEach var="p" items="${sessionScope.cesta}">
                            <div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-2">
                                <div>
                                    <span>${p.titulo}</span>
                                </div>
                                <form action="${pageContext.request.contextPath}/pelicula/quitarCesta" method="POST">
                                    <input type="hidden" name="idPelicula" value="${p.id}">
                                    <input type="hidden" name="volverA" value="${pageContext.request.contextPath}/pelicula/detalles?id=${pelicula.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Quitar</button>
                                </form>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

</nav>


