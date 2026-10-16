<%-- 
    Document   : home
    Created on : 9 oct 2026, 11:03:37
    Author     : FX506
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Home</h1>
        <c:if test="${not empty sesion.u}" >
            <a href="/home/users/perfil"> Mi perfil</a>
            <a href="/home/users/logout"> Cerrar Sesion</a>
        </c:if>
            
        <c:if test="${ empty sesion.u}" >
            <a href="/home/users/login">Iniciar Sesión<a/>
            <a href="/home/users/register">Registrarse<a/>
        </c:if>
        
    </body>
</html>
