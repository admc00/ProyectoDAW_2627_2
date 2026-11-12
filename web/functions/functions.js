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


function checkData(evt) {

    let ok = true;

    let con1 = document.getElementById("contrasena").value;
    let con2 = document.getElementById("contrasena2").value;

    if (con1.length < 4) {
        alert("La contraseña debe tener al menos 4 caracteres.");
        evt.preventDefault();
        ok = false;
    }

    if (con1 !== con2) {
        alert("Las contraseñas introducidas no coinciden.");
        evt.preventDefault();
        ok = false;
    }

    return ok;
}
