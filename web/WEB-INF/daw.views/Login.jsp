<%-- 
    Document   : Login
    Created on : 9 oct 2026, 11:03:08
    Author     : FX506
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />
        
        <h1>Iniciar Sesión</h1>
        
        <form action="/home/users/login" method="POST">
            <label for="email">Email:</label>
            <input id="email" type="text" name="email" required><br/> 
            <label for="contrasena">Contraseña:</label>
            <input id="contrasena" type="password" name="contrasena" required><br/> 
            
            <input type="submit" value="Iniciar Sesión" />
            
            <a href="/home/">Inicio</a>
        </form>
    </body>
</html>
