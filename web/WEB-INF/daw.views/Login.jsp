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
        
        <form action="/home/usuarios/save" method="POST">
            <label for="nombre">Nombre:</label>
            <input id="nombre type=text"><br/> 
            <label for="email">Email:</label>
            <input id="email type=text"><br/> 
            <label for="contraseña">Contraseña:</label>
            <input id="contraseña type=text"><br/> 
            <label for="contraseña">Repetir Contraseña:</label>
            <input id="contraseña type=text"><br/> 
            
            <input type="submit" value="Guardar" />
            
            <a href="/home/home">Inicio</a>
        </form>
    </body>
</html>
