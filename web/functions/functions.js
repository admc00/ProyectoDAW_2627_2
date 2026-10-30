/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let f = document.getElementById("formulario");
f.addEventListener("submit", checkData);

const myModal = document.getElementById('myModal');
const myInput = document.getElementById('botonAnadir');

myModal.addEventListener('shown.bs.modal', () => {
    myInput.focus();
});

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
