/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let f = document.getElementById("formulario");


if (f) {
    f.addEventListener("submit", checkData);
}

const myModal = document.getElementById('myModal');
const myInput = document.getElementById('botonAnadir');

const ModalEditarPeli = document.getElementById('ModalEditarPeli');
const InputEditar = document.getElementById('botonEditar');

if (myModal) {
    myModal.addEventListener('shown.bs.modal', () => {
        myInput.focus();
    });
}

if (ModalEditarPeli) {

    ModalEditarPeli.addEventListener('shown.bs.modal', (event) => {

        const botonPulsado = event.relatedTarget;

        const id = botonPulsado.getAttribute('data-bs-id');
        const titulo = botonPulsado.getAttribute('data-bs-titulo');
        const descripcion = botonPulsado.getAttribute('data-bs-descripcion');
        const fecha = botonPulsado.getAttribute('data-bs-fecha');
        const puntuacionRaw = botonPulsado.getAttribute('data-bs-puntuacion');

        const puntuacion = puntuacionRaw ? Math.round(parseFloat(puntuacionRaw)) : "";


        document.getElementById('edit-id').value = id;
        document.getElementById('edit-titulo').value = titulo;
        document.getElementById('edit-descripcion').value = descripcion;
        document.getElementById('edit-fecha').value = fecha;
        document.getElementById('edit-puntuacion').value = puntuacion;

        InputEditar.focus();


    });


}

function mostrarError(idSpan, mensaje) {
    const span = document.getElementById(idSpan);
    if (span) {
        span.textContent = mensaje;
    }
}

function esValido(idSpan) {

    const span = document.getElementById(idSpan);
    if (span) {
        span.textContent = "";
    }
}



function checkData(evt) {

    let ok = true;

    let con1 = document.getElementById("contrasena");
    let con2 = document.getElementById("contrasena2");
    let nombre = document.getElementById("nombre");
   

    if (nombre.value == null || nombre.value == "" || nombre.value.length == 0 || nombre.value.length <= 3) {
        mostrarError("error-usuario", "El usuario debe tener al menos 3 caracteres.");
        ok = false;
    } else {
        esValido("error-usuario");

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



let email = document.getElementById("email");

if (email) {
    email.addEventListener("change", emailDuplicadoFetch);
}

async function emailDuplicadoFetch() {

    let email = document.getElementById("email");
    let datos = new URLSearchParams(`email=${email.value}`);
    let response = await fetch("/home/users/comprobarEmail", {
        method: 'POST',
        body: datos
    });

    if (response.ok) {

        const data = await response.text();
        let duplicado = document.getElementById("emailInUse");
        
        let respuesta = data;
        let estilo;
        if (respuesta.includes("Ok"))
            estilo = "text-success";
        else
            estilo = "text-danger";

        let span = document.getElementById("mensaje");
        if (!span) {
            span = document.createElement("span");
            span.setAttribute("id", "mensaje");
            duplicado.appendChild(span);
        }

        span.setAttribute("class", estilo);
        span.innerHTML = respuesta;
    }

}

