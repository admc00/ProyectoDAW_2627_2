/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let f = document.getElementById("formulario");


if (f) {
    f.addEventListener("submit", checkData);
}


let ModalEditarPeli = document.getElementById('ModalEditarPeli');

let modalEditarResena = document.getElementById('modalEditarResena');



if (ModalEditarPeli) {

    ModalEditarPeli.addEventListener('shown.bs.modal', (event) => {

        const botonPulsado = event.relatedTarget;

        const id = botonPulsado.getAttribute('data-bs-id');
        const titulo = botonPulsado.getAttribute('data-bs-titulo');
        const descripcion = botonPulsado.getAttribute('data-bs-descripcion');
        const fecha = botonPulsado.getAttribute('data-bs-fecha');
        const puntuacionRaw = botonPulsado.getAttribute('data-bs-puntuacion');

        const puntuacion = parseFloat(puntuacionRaw);


        document.getElementById('edit-id').value = id;
        document.getElementById('edit-titulo').value = titulo;
        document.getElementById('edit-descripcion').value = descripcion;
        document.getElementById('edit-fecha').value = fecha;
        document.getElementById('edit-puntuacionMedia').value = puntuacion;


    });


}

if (modalEditarResena) {

    modalEditarResena.addEventListener('shown.bs.modal', (event) => {

        const boton = event.relatedTarget;
        
        const id = boton.getAttribute('data-id');
        
        const puntuacionRaw = boton.getAttribute('data-puntuacion');
        
        const texto = boton.getAttribute('data-texto');
                
               
        const puntuacion = parseFloat(puntuacionRaw);
        
        document.getElementById('edit-idResena').value = id;
        document.getElementById('edit-puntuacion').value = puntuacion;
        document.getElementById('edit-texto').value = texto;

    });

}

function mostrarError(idSpan, mensaje) {
    const span = document.getElementById(idSpan);
    if (span) {
        span.textContent = mensaje;
        span.className = "text-danger";
    }
}

function esValido(idSpan) {

    const span = document.getElementById(idSpan);
    if (span) {
        span.textContent = "";
        span.className = "";
    }
}



function checkData(evt) {

    let ok = true;

    let con1 = document.getElementById("contrasena");
    let con2 = document.getElementById("contrasena2");
    let nombre = document.getElementById("nombre");
    let email = document.getElementById("email");

    let regex = /^[-\w.%+]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,125}[a-zA-Z]{2,63}$/i;


    if (nombre.value == null || nombre.value == "" || nombre.value.length == 0 || nombre.value.length <= 3) {
        mostrarError("error-usuario", "El usuario debe tener al menos 3 caracteres.");
        ok = false;
    } else {
        esValido("error-usuario");

    }

    if (!regex.test(email.value)) {
        mostrarError("error-email", "El email no es correcto.");
        ok = false;
    } else {
        esValido("error-email");
    }

    if (con1.value.length < 4) {
        mostrarError("error-contraseña", "La contraseña debe tener al menos 4 caracteres.");
        ok = false;
    } else {
        esValido("error-contraseña");
    }

    if (con1.value !== con2.value) {
        mostrarError("error-contraseña2", "Las contraseñas no coinciden.");
        ok = false;
    } else {
        esValido("error-contraseña2");
    }

    if (!ok) {
        evt.preventDefault();
    }
}


let toastPermisos = document.getElementById('toastPermisos');

if (toastPermisos) {
    document.addEventListener("DOMContentLoaded", function () {

        let cuerpo = toastPermisos.querySelector(".toast-body");
        if (cuerpo && cuerpo.textContent !== "") {
            const toast = new bootstrap.Toast(toastPermisos);
            toast.show();

        }

        toastPermisos.addEventListener("hidden.bs.toast", limpiarToastError);

    });

}

function limpiarToastError() {
    let cuerpo = toastPermisos.querySelector(".toast-body");
    if (cuerpo) {
        cuerpo.textContent = "";
    }
}


let toastCesta = document.getElementById('toastCesta');

if (toastCesta) {
    document.addEventListener("DOMContentLoaded", function () {

        let cuerpoCesta = toastCesta.querySelector(".toast-body");
        if (cuerpoCesta && cuerpoCesta.textContent !== "") {
            const toast = new bootstrap.Toast(toastCesta);
            toast.show();
        }
        
        toastCesta.addEventListener("hidden.bs.toast", limpiarToastCesta);

    });
}

function limpiarToastCesta() {
    let cuerpoCesta = toastCesta.querySelector(".toast-body");
    if (cuerpoCesta) {
        cuerpoCesta.textContent = "";
    }
}



let email = document.getElementById("email");

if (email) {
    email.addEventListener("change", emailDuplicadoFetch);
}

async function emailDuplicadoFetch() {

    let span = document.getElementById("error-email");
    let email = document.getElementById("email");
    let datos = new URLSearchParams(`email=${email.value}`);
    let response = await fetch("/home/users/comprobarEmail", {
        method: 'POST',
        body: datos
    });

    if (response.ok) {

        const data = await response.text();

        let respuesta = data;
        let estilo;
        if (respuesta.includes("Ok"))
            estilo = "text-success";
        else
            estilo = "text-danger";


        span.setAttribute("class", estilo);
        span.textContent = respuesta;
    }

}

