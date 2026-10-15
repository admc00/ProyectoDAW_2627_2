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
        <h1>Registro Usuario</h1>

    <form id="formulario" action="/home/users/register" method="POST">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required><br/><br/>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br/><br/>

        <label for="contrasena">Contraseña:</label>
        <input type="password" id="contrasena" name="contrasena" required><br/><br/>

        <label for="contrasena2">Repetir Contraseña:</label>
        <input type="password" id="contrasena2" name="contrasena2" required><br/><br/>

        <input type="submit" value="Crear usuario" />

        <a href="/home/">Inicio</a>
        
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>
    </form>
</body>
</html>