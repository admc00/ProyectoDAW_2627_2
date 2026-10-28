<%-- 
    Document   : barraNavegacion
    Created on : 22 oct 2026, 19:42:27
    Author     : admc0
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <nav class="navbar py-3" style="background-color: #000066">
        <div  class ="container-fluid d-flex justify-content-between align-items-center">

            <a style="color: #00ff00" href="/home">
                Inicio
            </a>

            <div class="d-flex gap-3 align-items-center ">
                <c:if test="${not empty sessionScope.usuarioLogueado}">



                    <c:if test="${usuarioLogueado.email eq 'admc0000@gmail.com'}">

                        <a style="color: #00ff00" href="/home/pelicula/gestion">Gestionar Peliculas</a>

                        </c:if>

                        <c:if test="${usuarioLogueado.email ne 'admc0000@gmail.com'}">

                            <a style="color: #00ff00" href="/home/users/perfil">
                                Mi Perfil
                            </a>

                        </c:if>


                        <a style="color: #00ff00" href="/home/users/logout">Cerrar Sesión</a>

                    </c:if>

                    <c:if test="${empty sessionScope.usuarioLogueado}">
                        <a style="color: #00ff00" href="/home/users/login">Iniciar Sesión</a>
                        <a style="color: #00ff00" href="/home/users/register">Registrarse</a>
                    </c:if>
            </div>

        </div>
    </nav>
</html>
