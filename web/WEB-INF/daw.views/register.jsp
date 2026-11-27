<%-- 
    Document   : Register
    Created on : 9 oct 2026, 11:03:08
    Author     : FX506
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="bg-light">

        <jsp:include page="/WEB-INF/daw.views/barraNavegacion.jsp" />



        <div class="container mt-5">
            <div class="row justify-content-center align-items-center">

                <div class="col-md-5 mb-4 text-md-start">

                    <p class="lead text-secondary">Tu espacio personal para opinar, debatir y descubrir el mejor cine con otros usuarios.</p>
                    <ul class=" text-muted d-md-block">
                        <li class="mb-2">Crea reseñas personalizadas.</li>
                        <li class="mb-2">Interactúa mediante comentarios.</li>
                        <li class="mb-2">Administra tu perfil cinéfilo.</li>
                    </ul>
                </div>

                <div class="col-md-6 col-lg-5">

                    <h1 class="text-center mb-4">Registro Usuario</h1>

                    <div class="card shadow-sm border-2">
                        <div class="card-body">

                            <form novalidate id="formulario"  action="/home/users/register" method="POST">

                                <div class="mb-3">
                                    <label for="nombre">Nombre:</label>
                                    <input type="text" id="nombre" name="nombre" required minlength="3" class="form-control" >
                                    <span id="error-usuario"></span>

                                </div>


                                <div class="mb-3">
                                    <label for="email">Email:</label>
                                    <input type="email" id="email" name="email" class="form-control" >
                                    <span id="error-email"></span>

                                </div>


                                <div class="mb-3">
                                    <label for="contrasena">Contraseña:</label>
                                    <input type="password" id="contrasena" name="contrasena" class="form-control" >
                                    <span id="error-contraseña"></span>
                                </div>


                                <div class="mb-3">
                                    <label for="contrasena2">Repetir Contraseña:</label>
                                    <input type="password" id="contrasena2" name="contrasena2" class="form-control" >
                                    <span id="error-contraseña2"></span>
                                </div>

                                <div class=" d-flex justify-content-center">

                                    <button type="submit" class="btn btn-primary btn-center me-3">Crear Usuario</button>

                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container mt-5">

            <div class=" d-flex justify-content-center">

                <a href="/home/"  class="btn btn-primary btn-center me-3">Volver a inicio</a>
                <a href="/home/users/login"  class="btn btn-primary btn-center me-3">¿Tienes ya cuenta?</a>

            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/functions/functions.js"></script>
    </body>

</html>